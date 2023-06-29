package eu.grand.hotel.core

enum class RoomType {
    SINGLE, DOUBLE, JUNIOR,
}

@JvmInline
value class RoomTypes(val roomTypes: List<RoomType>) : Collection<RoomType> by roomTypes

fun roomTypesOf(vararg roomType: RoomType): RoomTypes = RoomTypes(roomType.toList())
