package eu.grand.hotel.core

@JvmInline
value class HotelId(val value: String) {
    init {
        require(value.isNotBlank())
    }
}
