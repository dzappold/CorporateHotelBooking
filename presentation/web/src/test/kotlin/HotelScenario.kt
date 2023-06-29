import RoomType.SINGLE
import dev.forkhandles.result4k.kotest.shouldBeSuccess
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month

interface HotelScenario {
    val jane: HotelManager
    val liam: CompanyAdmin
    val juliet: Employee

    private val exclusiveHotel: Hotel
        get() = Hotel(HotelId("exclusive"), HotelName("ladder"))

    private val liamHolding: CompanyId
        get() = CompanyId("liam holding ltd")

    private val julietsId: EmployeeId
        get() = EmployeeId("juliet")

    private val checkinDate: LocalDate
        get() = LocalDate.of(2024, Month.APRIL, 21)

    @Test
    fun `ex plore`() {
        jane.registerHotel(exclusiveHotel.id, exclusiveHotel.name)
        jane.configureRoom(exclusiveHotel.id, 42, SINGLE)

        liam.addEmployee(liamHolding, julietsId)
        liam.setCompanyPolicy(liamHolding, SINGLE)


        juliet.book(
            julietsId,
            exclusiveHotel.id,
            roomType = "single",
            checkIn = checkinDate,
            checkOut = checkinDate.plusDays(4)
        ).shouldBeSuccess()
    }
}
