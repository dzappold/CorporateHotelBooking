package actors

import eu.grand.hotel.core.Booking
import eu.grand.hotel.core.company.EmployeeId
import eu.grand.hotel.core.hotel.HotelId
import eu.grand.hotel.core.hotel.RoomType
import java.time.LocalDate

// knows her/his id and company
interface Employee {
    val employeeId: EmployeeId
    val hotelId: HotelId
    fun book(roomType: RoomType, checkIn: LocalDate, checkOut: LocalDate): Booking

    companion object
}

