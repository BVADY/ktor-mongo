import com.google.gson.Gson
import data.repositories.UserRepository
import domain.User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {

    val port = System.getenv("PORT")?.toInt() ?: 8080

    val server = embeddedServer(Netty, port = port) {
        routing {
            route("/users")  {
                get {
                    val result = UserRepository.getAllUsers()
                    call.respond(Gson().toJson(result))
                }

                get("/{email}") {
                    val email = call.parameters["email"]
                    val result = UserRepository.getUserByEmail(email.toString())
                    call.respond(Gson().toJson(result))
                }
            }
        }
    }

    println("Sever is running on port: $port")
    server.start(wait = true)
}