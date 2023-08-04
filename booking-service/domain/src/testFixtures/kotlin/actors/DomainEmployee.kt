package actors

import dev.forkhandles.result4k.onFailure
import eu.grand.hotel.bookingservice.BookingService
import eu.grand.hotel.core.Booking
import eu.grand.hotel.core.EmployeeId
import eu.grand.hotel.core.HotelId
import eu.grand.hotel.core.RoomType
import java.time.LocalDate

fun Employee.Companion.DomainEmployee(
    bookingService: BookingService,
    employeeId: EmployeeId = EmployeeId("e160aeb0-8050-4f27-87ce-0e258efd7488"),
    hotelId: HotelId = HotelId("1836f83b-42f8-4868-99de-706c7fc9698e")
): Employee =
    object : Employee {
        override val employeeId: EmployeeId = employeeId
        override val hotelId: HotelId = hotelId

        override fun book(roomType: RoomType, checkIn: LocalDate, checkOut: LocalDate): Booking =
            bookingService.book(employeeId, hotelId, roomType, checkIn, checkOut).onFailure { TODO("should not happen") }
    }
