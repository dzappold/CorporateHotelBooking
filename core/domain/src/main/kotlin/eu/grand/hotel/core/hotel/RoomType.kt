package eu.grand.hotel.core.hotel

enum class RoomType {
    SINGLE, DOUBLE, JUNIOR,
}

data class RoomTypes(val roomTypes: List<RoomType>) : Collection<RoomType> by roomTypes

fun roomTypesOf(vararg roomType: RoomType): RoomTypes = RoomTypes(roomType.toList())
