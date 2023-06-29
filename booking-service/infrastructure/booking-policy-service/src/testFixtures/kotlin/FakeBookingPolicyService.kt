import eu.grand.hotel.bookingservice.ports.BookingPolicyService
import eu.grand.hotel.core.EmployeeId
import eu.grand.hotel.core.RoomType
import eu.grand.hotel.core.RoomTypes
import eu.grand.hotel.core.roomTypesOf
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path
import org.http4k.lens.enum
import org.http4k.lens.value
import org.http4k.routing.bind
import org.http4k.routing.routes

fun BookingPolicyService.Companion.FakeHttpBookingPolicyService(companyPolicy: RoomTypes = roomTypesOf()): HttpHandler {
    val isBookingAllowed = Body.auto<Boolean>().toLens()
    val employeeId = Path.value(EmployeeId).of("employeeId")
    val roomType = Path.enum<RoomType>(caseSensitive = false).of("roomType")

    return routes(
        "/v1/booking-allowed/{employeeId}/{roomType}" bind Method.GET to { request ->
            Response(Status.OK).with(isBookingAllowed of (roomType(request) in companyPolicy))
        }
    )
}
