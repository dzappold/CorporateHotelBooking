import dev.forkhandles.result4k.asResultOr
import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.recover
import eu.grand.hotel.bookingservice.BookingServiceError
import eu.grand.hotel.bookingservice.ports.HotelService
import eu.grand.hotel.core.Hotel
import eu.grand.hotel.core.HotelId
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.GATEWAY_TIMEOUT
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path
import org.http4k.lens.value
import org.http4k.routing.bind
import org.http4k.routing.routes

fun HotelService.Companion.FakeHttpHotelService(hotels: Map<HotelId, Hotel> = emptyMap()): HttpHandler {
    val hotel = Body.auto<Hotel>().toLens()
    val hotelId = Path.value(HotelId).of("hotelId")

    return routes("/v1/hotels/{hotelId}" bind GET to { request ->
        hotels[hotelId(request)].asResultOr { BookingServiceError.UnknownHotel }
            .map { requestedHotel -> Response(OK).with(hotel of requestedHotel) }
            .recover { Response(GATEWAY_TIMEOUT) }
    })
}
