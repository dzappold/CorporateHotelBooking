package actors

import eu.grand.hotel.core.NumberOfRooms
import eu.grand.hotel.core.RoomType

// own a specific hotel
interface HotelManager {
//    fun addHotel(hotelId: HotelId, hotelName: HotelName)
//    fun setRoom(hotelId: HotelId, numberOfRooms: NumberOfRooms, roomType: RoomType)
//    fun findHotelBy(hotelId: HotelId): Hotel
    fun setRoom(numberOfRooms: NumberOfRooms, roomType: RoomType)

    companion object
}
