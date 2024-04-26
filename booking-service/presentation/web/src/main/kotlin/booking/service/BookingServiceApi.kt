package booking.service

import Http
import booking.service.routes.v1.Book
import eu.grand.hotel.bookingservice.BookingService
import eu.grand.hotel.bookingservice.ports.BookingPolicyService
import eu.grand.hotel.bookingservice.ports.HotelService
import eu.grand.hotel.core.app.AppEvents
import eu.grand.hotel.core.app.AppIncomingHttp
import eu.grand.hotel.core.app.AppOutgoingHttp
import org.http4k.cloudnative.env.Environment
import org.http4k.core.HttpHandler
import org.http4k.events.Events
import org.http4k.routing.routes
import java.time.Clock

fun BookingServiceApi(
    env: Environment,
    clock: Clock,
    events: Events,
    http: HttpHandler
): HttpHandler {
    val appEvents = AppEvents("BookingService", clock, events)
    val outgoingHttp = AppOutgoingHttp(appEvents, http)

    val bookingService = BookingService(
        BookingPolicyService.Http(env[BookingServiceApiSettings.BOOKING_POLICY_SERVICE_URL], outgoingHttp),
        HotelService.Http(env[BookingServiceApiSettings.HOTEL_SERVICE_URL], outgoingHttp)
    )

    return AppIncomingHttp(events, routes(Book(bookingService)))
}

