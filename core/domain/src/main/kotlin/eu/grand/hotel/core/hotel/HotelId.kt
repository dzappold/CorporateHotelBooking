package eu.grand.hotel.core.hotel

import dev.forkhandles.values.NonBlankStringValueFactory
import dev.forkhandles.values.StringValue

class HotelId private constructor(value: String) : StringValue(value) {
    companion object : NonBlankStringValueFactory<HotelId>(::HotelId)
}
