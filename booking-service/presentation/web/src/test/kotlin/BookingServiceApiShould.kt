import actors.CompanyAdmin
import actors.Employee
import actors.HotelManager
import eu.grand.hotel.bookingservice.ports.BookingPolicyService
import eu.grand.hotel.bookingservice.ports.HotelService
import eu.grand.hotel.core.Hotel
import eu.grand.hotel.core.HotelId
import eu.grand.hotel.core.RoomType.DOUBLE
import eu.grand.hotel.core.RoomType.SINGLE
import eu.grand.hotel.core.roomTypesOf
import org.http4k.cloudnative.env.Environment
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.kotest.shouldHaveBody
import org.http4k.kotest.shouldHaveStatus
import org.http4k.routing.reverseProxy
import org.junit.jupiter.api.Test
import scenarios.BookAvailableRoomScenario
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE

class BookingServiceApiShould : RecordTraces(), BookAvailableRoomScenario {
    private val hotelId = HotelId.of("b9b1a762-79a5-486e-9490-363d8fa6e2cd")
    private val env = Environment.from(
        "BOOKING_POLICY_SERVICE_URL" to "http://booking-policy:7777",
        "HOTEL_SERVICE_URL" to "http://hotel:8888",
    )
    private val clock = Clock.fixed(Instant.EPOCH, ZoneId.systemDefault())
    private val http: HttpHandler = reverseProxy(
        "booking-policy" to BookingPolicyService.FakeHttpBookingPolicyService(roomTypesOf(SINGLE, DOUBLE)),
        "hotel" to HotelService.FakeHttpHotelService(mapOf(hotelId to Hotel())),
    )
    private val checkIn: LocalDate = LocalDate.of(2023, Month.APRIL, 17)
    private val checkOut: LocalDate = LocalDate.of(2023, Month.APRIL, 23)

    @Test
    fun `book an allowed and available hotel successfully`() {
        val client = BookingServiceApi(env, clock, events, http)

        val response =
            client(Request(GET, "/v1/bookings/employeeId/${hotelId.value}/double/${ISO_LOCAL_DATE.format(checkIn)}/${ISO_LOCAL_DATE.format(checkOut)}"))

        // TODO: more detailed test
        response shouldHaveStatus OK
        response shouldHaveBody ""
    }

    override val helga: HotelManager
        get() = TODO("Not yet implemented")
    override val cora: CompanyAdmin
        get() = TODO("Not yet implemented")
    override val emilia: Employee
        get() = TODO("Not yet implemented")

    // TODO: error mapping
}
