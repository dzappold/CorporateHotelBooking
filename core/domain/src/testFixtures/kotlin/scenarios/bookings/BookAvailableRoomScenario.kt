package scenarios.bookings

import actors.CompanyAdmin
import actors.Employee
import actors.HotelManager
import eu.grand.hotel.core.Booking
import eu.grand.hotel.core.hotel.Hotel
import eu.grand.hotel.core.hotel.NumberOfRooms
import eu.grand.hotel.core.ResidencePeriod
import eu.grand.hotel.core.hotel.RoomType.SINGLE
import eu.grand.hotel.core.hotel.roomTypesOf
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate

interface BookAvailableRoomScenario {
    val helga: HotelManager
    val cora: CompanyAdmin
    val emilia: Employee

    val expectedHotel: Hotel

    @Test
    fun `book an available single bed room according company policy`() {
        val today = LocalDate.now()

        helga.setRoom(NumberOfRooms(20), SINGLE)

        cora.addEmployee(emilia.employeeId)
        cora.setCompanyBookingPolicy(roomTypesOf(SINGLE))

        val checkIn = today.minusMonths(3)
        val checkOut = today.minusMonths(2)

        val booking = emilia.book(SINGLE, checkIn, checkOut)

        booking shouldBe Booking(expectedHotel, SINGLE, ResidencePeriod(checkIn, checkOut))
    }
}
