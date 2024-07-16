package eu.grand.hotel.bookingservice.policy

import eu.grand.hotel.bookingservice.ports.BookingPolicyService
import eu.grand.hotel.core.EmployeeId
import eu.grand.hotel.core.RoomType
import eu.grand.hotel.core.RoomTypes
import eu.grand.hotel.core.roomTypesOf
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path
import org.http4k.lens.enum
import org.http4k.lens.nonEmptyString
import org.http4k.routing.bind
import org.http4k.routing.routes

private const val EMPLOYEE_ID = "employeeId"
private const val ROOM_TYPE = "roomType"

private val isBookingAllowed = Body.auto<Boolean>().toLens()
private val employeeId = Path.nonEmptyString().map(::EmployeeId, EmployeeId::value).of(EMPLOYEE_ID)
private val roomType = Path.enum<RoomType>(caseSensitive = false).of(ROOM_TYPE)

fun BookingPolicyService.Companion.FakeHttpBookingPolicyService(companyPolicy: RoomTypes = roomTypesOf()): HttpHandler =
    routes(
        "/v1/booking-allowed/{$EMPLOYEE_ID}/{$ROOM_TYPE}" bind GET to { request ->
            Response(OK).with(isBookingAllowed of (roomType(request) in companyPolicy))
        }
    )
