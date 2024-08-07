package eu.grand.hotel.core

import eu.grand.hotel.core.hotel.Hotel
import eu.grand.hotel.core.hotel.RoomType
import java.time.LocalDate

data class Booking(val hotel: Hotel, val roomType: RoomType, val residencePeriod: ResidencePeriod)

data class ResidencePeriod(val checkIn: LocalDate, val checkOut: LocalDate)
