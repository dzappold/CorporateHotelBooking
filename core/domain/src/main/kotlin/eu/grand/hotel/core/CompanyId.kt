package eu.grand.hotel.core

import dev.forkhandles.values.StringValueFactory
import dev.forkhandles.values.Value

@JvmInline
value class CompanyId private constructor(override val value: String) : Value<String> {
    companion object : StringValueFactory<CompanyId>(::CompanyId, String::isNotBlank)
}
