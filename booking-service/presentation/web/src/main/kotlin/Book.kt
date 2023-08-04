import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.recover
import eu.grand.hotel.bookingservice.BookingService
import eu.grand.hotel.core.EmployeeId
import eu.grand.hotel.core.HotelId
import eu.grand.hotel.core.RoomType
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.Path
import org.http4k.lens.enum
import org.http4k.lens.localDate
import org.http4k.lens.nonEmptyString
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind

private val employeeId = Path.nonEmptyString().map(::EmployeeId, EmployeeId::value).of("employeeId")
private val hotelId = Path.nonEmptyString().map(::HotelId, HotelId::value).of("hotelId")
private val roomType = Path.enum<RoomType>(false).of("roomType")
private val checkIn = Path.localDate().of("checkIn")
private val checkOut = Path.localDate().of("checkOut")

fun Book(bookingService: BookingService): RoutingHttpHandler =
    "/v1/bookings/{employeeId}/{hotelId}/{roomType}/{checkIn}/{checkOut}" bind Method.GET to { request ->
        bookingService
            .book(employeeId(request), hotelId(request), roomType(request), checkIn(request), checkOut(request))
            .map { Response(OK) }
            .recover { Response(BAD_REQUEST) }
    }
