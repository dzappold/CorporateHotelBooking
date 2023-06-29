package eu.grand.hotel.bookingservice.ports

import dev.forkhandles.result4k.Result4k
import eu.grand.hotel.bookingservice.BookingServiceError
import eu.grand.hotel.core.Hotel
import eu.grand.hotel.core.HotelId

fun interface HotelService {
    fun findHotelBy(hotelId: HotelId): Result4k<Hotel, BookingServiceError>

    companion object
}
