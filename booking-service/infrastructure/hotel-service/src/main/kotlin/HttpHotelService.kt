import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import eu.grand.hotel.bookingservice.BookingServiceError
import eu.grand.hotel.bookingservice.ports.HotelService
import eu.grand.hotel.core.Hotel
import eu.grand.hotel.core.HotelId
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters
import org.http4k.format.Jackson.auto

fun HotelService.Companion.Http(hotelServiceUri: Uri, rawHttp: HttpHandler): HotelService =
    object : HotelService {
        private val http = ClientFilters.SetHostFrom(hotelServiceUri).then(rawHttp)
        private val hotel = Body.auto<Hotel>().toLens()

        override fun findHotelBy(hotelId: HotelId): Result4k<Hotel, BookingServiceError> =
            http(Request(Method.GET, "/v1/hotels/${hotelId.value}"))
                .let { response ->
                    when {
                        response.status.successful -> Success(hotel(response))
                        else -> Failure(BookingServiceError.UnknownHotel)
                    }
                }
    }
