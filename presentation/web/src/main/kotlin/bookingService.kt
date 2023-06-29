import org.http4k.core.Body
import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Http4kServer
import org.http4k.server.Undertow
import org.http4k.server.asServer
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.ViewModel
import org.http4k.template.viewModel

data class BookingFormModel(
    val employeeId: String = "",
    val hotelId: String = "",
    val roomType: String = "",
    val checkIn: String = "",
    val checkOut: String = ""
) : ViewModel

val bookingFormLens = Body.auto<BookingFormModel>().toLens()

fun main() {
    val renderer = HandlebarsTemplates().CachingClasspath()
    val view = Body.viewModel(renderer, TEXT_HTML).toLens()
    fun bookingFormHandler(): HttpHandler = {
        val model = BookingFormModel()
        Response(OK).with(view of model)
    }

    val bookingFormRoute: HttpHandler = {
        Response(OK).body(renderer(BookingFormModel()))
    }

    val bookingSubmissionRoute: HttpHandler = {
        println("received: '${it.bodyString()}'")
        val bookingForm = bookingFormLens(it)
        // Process the booking form submission and perform any necessary actions
        // such as saving the booking to a database or triggering other business logic
        Response(OK).body("Booking submitted successfully")
    }

    val routes: RoutingHttpHandler = routes(
        "/" bind GET to bookingFormHandler(),
        "/booking" bind GET to bookingFormRoute,
        "/booking" bind POST to bookingSubmissionRoute
    )

    val server: Http4kServer = routes.asServer(Undertow(port = 8080))

    server.start()

    println("Server started on port ${server.port()}")

    Runtime.getRuntime().addShutdownHook(Thread {
        server.stop()
        println("Server stopped")
    })
}
