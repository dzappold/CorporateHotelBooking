import booking.service.BookingServiceApi
import org.http4k.client.OkHttp
import org.http4k.cloudnative.asK8sServer
import org.http4k.cloudnative.env.Environment
import org.http4k.events.AutoMarshallingEvents
import org.http4k.format.Jackson
import org.http4k.server.Helidon
import org.http4k.server.Http4kServer
import utils.registerShutdownHook
import java.time.Clock

fun main() {
    BookingServiceApi(Environment.ENV, Clock.systemUTC(), AutoMarshallingEvents(Jackson), OkHttp())
        .asK8sServer(::Helidon)
        .start()
        .also { server -> println("started server at port ${server.port()}") }
        .apply(Http4kServer::registerShutdownHook)
}
