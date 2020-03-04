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
            get("/") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
        }
    }

    println("Sever is running on port: $port")
    server.start(wait = true)
}