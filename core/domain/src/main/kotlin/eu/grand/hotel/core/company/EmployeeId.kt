package eu.grand.hotel.core.company

import dev.forkhandles.values.NonBlankStringValueFactory
import dev.forkhandles.values.StringValue

class EmployeeId private constructor(value: String) : StringValue(value) {
    companion object : NonBlankStringValueFactory<EmployeeId>(::EmployeeId)
}
