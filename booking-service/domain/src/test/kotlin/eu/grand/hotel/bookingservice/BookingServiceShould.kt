package eu.grand.hotel.bookingservice

import FakeBookingPolicyService
import FakeHotelService
import actors.CompanyAdmin
import actors.DomainCompanyAdmin
import actors.DomainEmployee
import actors.DomainHotelManager
import actors.Employee
import actors.HotelManager
import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import eu.grand.hotel.bookingservice.ports.BookingPolicyService
import eu.grand.hotel.bookingservice.ports.HotelService
import eu.grand.hotel.core.hotel.Hotel
import eu.grand.hotel.core.hotel.HotelId
import eu.grand.hotel.core.hotel.HotelName
import eu.grand.hotel.core.hotel.RoomType.DOUBLE
import eu.grand.hotel.core.hotel.RoomType.SINGLE
import eu.grand.hotel.core.hotel.roomTypesOf
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import scenarios.bookings.BookAvailableRoomScenario
import java.time.LocalDate

private val hotelId = HotelId("cba27b00-0eec-43ef-953e-3123e8048735")

class BookingServiceShould : BookAvailableRoomScenario {
    override val expectedHotel: Hotel = Hotel(HotelName("nursery"))
    private val bookingService =
        BookingService(BookingPolicyService.FakeBookingPolicyService(roomTypesOf(SINGLE, DOUBLE)), HotelService.FakeHotelService(mapOf(hotelId to expectedHotel)))

    override val emilia: Employee = Employee.DomainEmployee(bookingService, hotelId = hotelId)
    override val helga: HotelManager = HotelManager.DomainHotelManager()
    override val cora: CompanyAdmin = CompanyAdmin.DomainCompanyAdmin()

    @Nested
    @DisplayName("do we need mock tests at all?")
    inner class CollaborateWith {
        private val bookingPolicyService = mockk<BookingPolicyService>()
        private val hotelService = mockk<HotelService>()
        private val bookingService = BookingService(bookingPolicyService, hotelService)

        @BeforeEach
        fun trainDefaultBehaviourOfServices() {
            every { bookingPolicyService.isBookingAllowed(emilia.employeeId, DOUBLE) } returns Success(true)
            every { hotelService.findHotelBy(emilia.hotelId) } returns Failure(BookingServiceError.UnknownHotel)
        }

        @Test
        fun `collaborate with booking policy service`() {
            every { bookingPolicyService.isBookingAllowed(emilia.employeeId, DOUBLE) } returns Success(true)

            bookingService.book(emilia.employeeId, emilia.hotelId, DOUBLE, LocalDate.EPOCH, LocalDate.EPOCH.plusDays(2))

            verify { bookingPolicyService.isBookingAllowed(emilia.employeeId, DOUBLE) }
        }

        @Test
        fun `collaborate with hotel service`() {
            every { hotelService.findHotelBy(emilia.hotelId) } returns Failure(BookingServiceError.UnknownHotel)

            bookingService.book(emilia.employeeId, emilia.hotelId, DOUBLE, LocalDate.EPOCH, LocalDate.EPOCH.plusDays(2))

            verify { hotelService.findHotelBy(emilia.hotelId) }
        }
    }
}
