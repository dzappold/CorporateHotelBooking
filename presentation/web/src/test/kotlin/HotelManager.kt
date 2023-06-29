import dev.forkhandles.result4k.Result4k

// Hotel Manager:
// Set all the different types of rooms and respective quantities for her hotel.
interface HotelManager {
    fun registerHotel(id: HotelId, name: HotelName): Result4k<Hotel, HotelError>
    fun configureRoom(hotelID: HotelId, roomNumber: Int, roomType: RoomType)
}

enum class RoomType {
    SINGLE, DOUBLE, PRESIDENT
}
