package eu.grand.hotel.bookingservice.ports

import dev.forkhandles.result4k.Result4k
import eu.grand.hotel.bookingservice.BookingServiceError
import eu.grand.hotel.core.company.EmployeeId
import eu.grand.hotel.core.hotel.RoomType

fun interface BookingPolicyService {
    fun isBookingAllowed(employeeId: EmployeeId, roomType: RoomType): Result4k<Boolean, BookingServiceError>

    companion object
}
