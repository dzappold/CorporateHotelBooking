package booking.service

import FakeHttpBookingPolicyService
import FakeHttpHotelService
import RecordTraces
import actors.CompanyAdmin
import actors.Employee
import actors.HotelManager
import eu.grand.hotel.bookingservice.ports.BookingPolicyService
import eu.grand.hotel.bookingservice.ports.HotelService
import eu.grand.hotel.core.Hotel
import eu.grand.hotel.core.HotelId
import eu.grand.hotel.core.HotelName
import eu.grand.hotel.core.NumberOfRooms
import eu.grand.hotel.core.RoomType
import eu.grand.hotel.core.roomTypesOf
import io.kotest.assertions.asClue
import io.kotest.matchers.and
import io.kotest.matchers.should
import org.http4k.cloudnative.env.Environment
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.core.Uri
import org.http4k.kotest.haveBody
import org.http4k.kotest.haveStatus
import org.http4k.routing.reverseProxy
import org.junit.jupiter.api.Test
import scenarios.BookAvailableRoomScenario
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private const val BOOKING_POLICY = "booking-policy"
private const val HOTEL = "hotel"

class BookingServiceApiShould : RecordTraces(), BookAvailableRoomScenario {
    private val hotelId = HotelId("b9b1a762-79a5-486e-9490-363d8fa6e2cd")
    override val expectedHotel: Hotel = Hotel(HotelName("Youth Hostel"))
    private val clock = Clock.fixed(Instant.EPOCH, ZoneId.systemDefault())
    private val checkIn: LocalDate = LocalDate.of(2023, Month.APRIL, 17)
    private val checkOut: LocalDate = LocalDate.of(2023, Month.APRIL, 23)

    private val env = Environment.defaults(
        BookingServiceApiSettings.BOOKING_POLICY_SERVICE_URL of Uri.of("http://$BOOKING_POLICY:7777"),
        BookingServiceApiSettings.HOTEL_SERVICE_URL of Uri.of("http://$HOTEL:8888")
    )

    private val http: HttpHandler = reverseProxy(
        BOOKING_POLICY to BookingPolicyService.FakeHttpBookingPolicyService(
            roomTypesOf(
                RoomType.SINGLE,
                RoomType.DOUBLE
            )
        ),
        HOTEL to HotelService.FakeHttpHotelService(mapOf(hotelId to expectedHotel)),
    )

    @Test
    fun `book an allowed and available hotel successfully`() {
        val client = BookingServiceApi(env, clock, events, http)

        val response = client(
            Request(
                Method.GET,
                "/v1/bookings/employeeId/${hotelId.value}/double/${DateTimeFormatter.ISO_LOCAL_DATE.format(checkIn)}/${
                    DateTimeFormatter.ISO_LOCAL_DATE.format(checkOut)
                }"
            )
        )

        // TODO: more detailed test
        "booking service response:".asClue {
            response should (haveStatus(Status.OK) and haveBody("me ..."))
        }
    }

    override val helga: HotelManager
        get() = object : HotelManager {
            override fun setRoom(numberOfRooms: NumberOfRooms, roomType: RoomType) {
            }
        }
    override val cora: CompanyAdmin
        get() = TODO("Not yet implemented")
    override val emilia: Employee
        get() = TODO("Not yet implemented")

    // TODO: error mapping
}
