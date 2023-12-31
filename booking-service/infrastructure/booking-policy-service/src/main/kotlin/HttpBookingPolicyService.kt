import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import eu.grand.hotel.bookingservice.BookingServiceError
import eu.grand.hotel.bookingservice.ports.BookingPolicyService
import eu.grand.hotel.core.EmployeeId
import eu.grand.hotel.core.RoomType
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ClientFilters
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path
import org.http4k.lens.enum
import org.http4k.lens.nonEmptyString

fun BookingPolicyService.Companion.Http(bookingPolicyServiceUri: Uri, rawHttp: HttpHandler): BookingPolicyService =
    object : BookingPolicyService {
        private val http = ClientFilters.SetHostFrom(bookingPolicyServiceUri).then(rawHttp)
        private val isBookingAllowed = Body.auto<Boolean>().toLens()
        private val employeeIdLens = Path.nonEmptyString().map(::EmployeeId, EmployeeId::value).of("employeeId")
        private val roomTypeLens = Path.enum<RoomType>(caseSensitive = false).of("roomType")

        override fun isBookingAllowed(employeeId: EmployeeId, roomType: RoomType): Result4k<Boolean, BookingServiceError> =
            http(Request(Method.GET, "/v1/booking-allowed/{employeeId}/{roomType}").with(employeeIdLens of employeeId, roomTypeLens of roomType))
                .let { response ->
                    when {
                        response.status.successful -> Success(isBookingAllowed(response))
                        else -> Failure(BookingServiceError.BookingNotAllowed)
                    }
                }
    }
