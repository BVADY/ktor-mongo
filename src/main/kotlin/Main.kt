import com.google.gson.Gson
import data.repositories.AttendeeRepository
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {

    val port = System.getenv("PORT")?.toInt() ?: 8080

    val server = embeddedServer(Netty, port = port) {
        routing {
            route("/attendees")  {
                get {
                    val result = AttendeeRepository.getAllAttendees()
                    call.respond(Gson().toJson(result))
                }

                get("/{email}") {
                    val email = call.parameters["email"]
                    val result = AttendeeRepository.getAttendeeByEmail(email.toString())
                    call.respond(Gson().toJson(result))
                }

                post("/{email}/check-in"){
                    val email = call.parameters["email"]
                    val result = AttendeeRepository.checkInAttendeeByEmail(email.toString())
                    call.respond(Gson().toJson(result))
                }
            }
        }
    }

    println("Sever is running on port: $port")
    server.start(wait = true)
}