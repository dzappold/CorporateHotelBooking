package eu.grand.hotel.core

@JvmInline
value class EmployeeId(val value: String) {
    init {
        require(value.isNotBlank())
    }
}
