import dev.forkhandles.result4k.Result4k
import java.time.LocalDate

interface Employee {
    fun book(
        employeeId: EmployeeId,
        hotelId: HotelId,
        roomType: String,
        checkIn: LocalDate,
        checkOut: LocalDate
    ): Result4k<Booking, BookingError>

}

class Booking

sealed interface BookingError
