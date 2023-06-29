package eu.grand.hotel.core

import dev.forkhandles.values.StringValueFactory
import dev.forkhandles.values.Value
import dev.forkhandles.values.ofResult4k

/**
 * val employeeIdResult4k = EmployeeId.ofResult4k("5MKj5j")
 * val employeeId = EmployeeId.of("5MKj5j")
 */
@JvmInline
value class EmployeeId private constructor(override val value: String) : Value<String> {
    companion object : StringValueFactory<EmployeeId>(::EmployeeId, String::isNotBlank)
}

fun employeeIdOf(value: String) = EmployeeId.ofResult4k(value)

