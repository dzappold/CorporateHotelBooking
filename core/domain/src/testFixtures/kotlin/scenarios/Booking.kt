package scenarios

import actors.CompanyAdmin
import actors.Employee
import actors.HotelManager
import eu.grand.hotel.core.NumberOfRooms
import eu.grand.hotel.core.RoomType
import eu.grand.hotel.core.RoomType.SINGLE
import eu.grand.hotel.core.roomTypesOf
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate

interface BookAvailableRoomScenario {
    val helga: HotelManager
    val cora: CompanyAdmin
    val emilia: Employee

    @Test
    fun `book a available single bed room according company policy`() {
        val today = LocalDate.now()

        helga.setRoom(NumberOfRooms(20), SINGLE)

        cora.addEmployee(emilia.employeeId)
        cora.setCompanyBookingPolicy(roomTypesOf(SINGLE))

        val booking = emilia.book(SINGLE, today.minusMonths(3), today.minusMonths(2))
        // TODO: was soll das booking enthalten und was wollen wir hier testen?
        booking.roomType shouldBe SINGLE
    }
}
