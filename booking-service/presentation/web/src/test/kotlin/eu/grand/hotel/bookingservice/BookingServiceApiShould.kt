package eu.grand.hotel.bookingservice

import RecordTraces
import actors.CompanyAdmin
import actors.Employee
import actors.HotelManager
import eu.grand.hotel.core.Booking
import eu.grand.hotel.core.company.CompanyId
import eu.grand.hotel.core.company.EmployeeId
import eu.grand.hotel.core.hotel.Hotel
import eu.grand.hotel.core.hotel.HotelId
import eu.grand.hotel.core.hotel.HotelName
import eu.grand.hotel.core.hotel.NumberOfRooms
import eu.grand.hotel.core.ResidencePeriod
import eu.grand.hotel.core.hotel.RoomType
import eu.grand.hotel.core.hotel.RoomType.DOUBLE
import eu.grand.hotel.core.hotel.RoomType.SINGLE
import eu.grand.hotel.core.hotel.RoomTypes
import eu.grand.hotel.core.hotel.roomTypesOf
import io.kotest.assertions.json.containJsonKeyValue
import io.kotest.assertions.json.schema.jsonSchema
import io.kotest.assertions.json.schema.obj
import io.kotest.assertions.json.schema.shouldMatchSchema
import io.kotest.assertions.print.print
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.and
import io.kotest.matchers.string.beBlank
import io.kotest.matchers.string.match
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Status.Companion.OK
import org.http4k.kotest.shouldHaveBody
import org.http4k.kotest.shouldHaveStatus
import org.junit.jupiter.api.Test
import scenarios.bookings.BookAvailableRoomScenario
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.Month.APRIL
import java.time.ZoneId
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE

class BookingServiceApiShould : RecordTraces(), BookAvailableRoomScenario {
    private val hotelId = HotelId("b9b1a762-79a5-486e-9490-363d8fa6e2cd")
    override val expectedHotel: Hotel = Hotel(HotelName("Youth Hostel"))
    private val clock = Clock.fixed(Instant.EPOCH, ZoneId.systemDefault())
    private val checkIn: LocalDate = LocalDate.of(2023, APRIL, 17)
    private val checkOut: LocalDate = LocalDate.of(2023, APRIL, 23)

    private val env = BookingServiceApiTestEnvironment
    private val http: HttpHandler = networkAccess(env, roomTypesOf(SINGLE, DOUBLE), mapOf(hotelId to expectedHotel))

    @Test
    fun `book an allowed and available hotel successfully`() {
        val client = BookingServiceApi(env, clock, events, http)

        val response = client(Request(GET, "/v1/bookings/employeeId/${hotelId.value}/double/${ISO_LOCAL_DATE.format(checkIn)}/${ISO_LOCAL_DATE.format(checkOut)}"))

        response shouldHaveStatus OK
        response.bodyString() shouldMatchSchema bookingServiceSchema

        response.shouldHaveBody(
            containJsonKeyValue("$.hotel.hotelName.name", expectedHotel.hotelName.name)
                .and(containJsonKeyValue("$.roomType", DOUBLE.name))
                .and(containJsonKeyValue("$.residencePeriod.checkIn", ISO_LOCAL_DATE.format(checkIn)))
                .and(containJsonKeyValue("$.residencePeriod.checkOut", ISO_LOCAL_DATE.format(checkOut)))
        )
    }


    // TODO: error mapping

    override val helga: HotelManager
        get() = object : HotelManager {
            override fun setRoom(numberOfRooms: NumberOfRooms, roomType: RoomType) {
            }
        }

    override val cora: CompanyAdmin
        get() = object : CompanyAdmin {
            override val companyId: CompanyId
                get() = TODO("Not yet implemented")

            override fun addEmployee(employeeId: EmployeeId) {

            }

            override fun deleteEmployee(employeeId: EmployeeId) {
                TODO("Not yet implemented")
            }

            override fun setCompanyBookingPolicy(roomTypes: RoomTypes) {
            }

            override fun setEmployeeBookingPolicy(employeeId: EmployeeId, roomTypes: RoomTypes) {
                TODO("Not yet implemented")
            }
        }

    override val emilia: Employee
        get() = object : Employee {
            override val employeeId: EmployeeId
                get() = EmployeeId("employee-id-emilia")

            override val hotelId: HotelId
                get() = TODO("Not yet implemented")

            override fun book(roomType: RoomType, checkIn: LocalDate, checkOut: LocalDate): Booking {
                return Booking(expectedHotel, roomType, ResidencePeriod(checkIn, checkOut))
            }
        }
}


private fun beOneOfIgnoringCase(collection: List<String>): Matcher<String> =
    object : Matcher<String> {
        override fun test(value: String): MatcherResult {
            if (collection.isEmpty()) throw AssertionError("Asserting content on empty collection. Use Collection.shouldBeEmpty() instead.")
            val match = collection.any { it.uppercase() == value.uppercase() }
            return MatcherResult(
                match,
                { "Collection should contain the instance ${value.print().value} with hashcode ${value.hashCode()}." },
                { "Collection should not contain the instance ${value.print().value} with hashcode ${value.hashCode()}." })
        }
    }

private val bookingServiceSchema
    get() = jsonSchema {
        obj {
            obj("hotel", false) {
                obj("hotelName", false) {
                    string("name", false) { beBlank().invert() }
                }
            }
            string("roomType", false) { beOneOfIgnoringCase(RoomType.entries.map(RoomType::name)) }
            obj("residencePeriod", false) {
                string("checkIn", false) { match("\\d{4}-\\d{2}-\\d{2}".toRegex()) }
                string("checkOut", false) { match("\\d{4}-\\d{2}-\\d{2}".toRegex()) }
            }
            additionalProperties = false
        }
    }
