package eu.grand.hotel.bookingservice.routes.v1

import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.recover
import eu.grand.hotel.bookingservice.BookingService
import eu.grand.hotel.core.company.EmployeeId
import eu.grand.hotel.core.hotel.HotelId
import eu.grand.hotel.core.hotel.RoomType
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.Path
import org.http4k.lens.enum
import org.http4k.lens.localDate
import org.http4k.lens.value
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import utils.HotelBookingJsonConfiguration.json

private val employeeId = Path.value(EmployeeId).of("employeeId")
private val hotelId = Path.value(HotelId).of("hotelId")
private val roomType = Path.enum<RoomType>(false).of("roomType")
private val checkIn = Path.localDate().of("checkIn")
private val checkOut = Path.localDate().of("checkOut")

fun Book(bookingService: BookingService): RoutingHttpHandler =
    "/v1/bookings/{employeeId}/{hotelId}/{roomType}/{checkIn}/{checkOut}" bind GET to { request ->
        bookingService
            .book(employeeId(request), hotelId(request), roomType(request), checkIn(request), checkOut(request))
            .map { booking -> Response(OK).json(booking) }
            .recover { Response(BAD_REQUEST) }
    }
