import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.HttpStatusCode
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import service.attendee.AttendeeService

fun main() {

    val port = System.getenv("PORT")?.toInt() ?: 8080

    val attendeeService = AttendeeService()

    val server = embeddedServer(Netty, port = port) {
        routing {
            route("/attendees") {
                get {
                    val result = attendeeService.getAllAttendees()
                    if (result.isNullOrEmpty()) call.respond(HttpStatusCode.NoContent)

                    call.respond(Gson().toJson(result))
                }

                get("/{email}") {
                    val email = call.parameters["email"]
                    if (email.isNullOrBlank()) call.respond(HttpStatusCode.BadRequest)

                    val result = email?.let { attendeeService.getAttendeeByEmail(it) }
                    if (result == null) call.respond(HttpStatusCode.NotFound)

                    call.respond(Gson().toJson(result))
                }

                post("/{email}/check-in") {
                    val email = call.parameters["email"]
                    if (email.isNullOrBlank()) call.respond(HttpStatusCode.BadRequest)

                    val result = email?.let { attendeeService.checkInAttendeeByEmail(it) }
                    if (result == null) call.respond(HttpStatusCode.NotFound)

                    call.respond(Gson().toJson(result))
                }
            }
        }
    }

    println("Sever is running on port: $port")
    server.start(wait = true)
}