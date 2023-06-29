package eu.grand.hotel.bookingservice

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.flatMap
import dev.forkhandles.result4k.map
import eu.grand.hotel.bookingservice.BookingServiceError.InvalidBookingRange
import eu.grand.hotel.bookingservice.ports.BookingPolicyService
import eu.grand.hotel.bookingservice.ports.HotelService
import eu.grand.hotel.core.Booking
import eu.grand.hotel.core.EmployeeId
import eu.grand.hotel.core.HotelId
import eu.grand.hotel.core.ResidencePeriod
import eu.grand.hotel.core.RoomType
import java.time.LocalDate

class BookingService(private val bookingPolicyService: BookingPolicyService, private val hotelService: HotelService) {
    fun book(employeeId: EmployeeId, hotelId: HotelId, roomType: RoomType, checkIn: LocalDate, checkOut: LocalDate): Result4k<Booking, BookingServiceError> =
        when (checkIn.isBefore(checkOut)) {
            true -> execute(employeeId, roomType, hotelId, checkIn, checkOut)
            false -> Failure(InvalidBookingRange)
        }

    private fun execute(employeeId: EmployeeId, roomType: RoomType, hotelId: HotelId, checkIn: LocalDate, checkOut: LocalDate) =
        bookingPolicyService.isBookingAllowed(employeeId, roomType)
            .flatMap {
                when (it) {
                    true -> Success(hotelId)
                    false -> Failure(BookingServiceError.BookingNotAllowed)
                }
            }
            .flatMap(hotelService::findHotelBy)
            .map { hotel -> Booking(hotel, roomType, ResidencePeriod(checkIn, checkOut)) }
}
