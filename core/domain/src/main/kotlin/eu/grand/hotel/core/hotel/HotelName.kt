package eu.grand.hotel.core.hotel

import dev.forkhandles.values.IntValue
import dev.forkhandles.values.IntValueFactory
import dev.forkhandles.values.NonBlankStringValueFactory
import dev.forkhandles.values.StringValue

class HotelName private constructor(value: String) : StringValue(value) {
    companion object : NonBlankStringValueFactory<HotelName>(::HotelName)
}


class NumberOfRooms(amount: Int) : IntValue(value = amount) {
    companion object : IntValueFactory<NumberOfRooms>(::NumberOfRooms, { amount: Int -> amount >= 0 })
}

data class Hotel(val hotelName: HotelName)
