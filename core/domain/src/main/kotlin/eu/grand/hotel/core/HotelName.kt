package eu.grand.hotel.core

//@JvmInline
data class HotelName(val name: String)

@JvmInline
value class NumberOfRooms(val amount: Int)

data class Hotel(val hotelName: HotelName)
