package actors

import eu.grand.hotel.core.hotel.NumberOfRooms
import eu.grand.hotel.core.hotel.RoomType

fun HotelManager.Companion.DomainHotelManager(): HotelManager = object : HotelManager {
    private val hotel = mutableMapOf<RoomType, NumberOfRooms>()

    override fun setRoom(numberOfRooms: NumberOfRooms, roomType: RoomType) {
        hotel[roomType] = numberOfRooms
    }
}
