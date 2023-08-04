package eu.grand.hotel.core

@JvmInline
value class CompanyId(val value: String) {
    init {
        require(value.isNotBlank())
    }
}
