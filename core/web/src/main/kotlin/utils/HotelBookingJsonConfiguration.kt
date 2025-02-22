package utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.afterburner.AfterburnerModule
import com.fasterxml.jackson.module.blackbird.BlackbirdModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import eu.grand.hotel.core.company.CompanyId
import eu.grand.hotel.core.company.EmployeeId
import eu.grand.hotel.core.hotel.HotelId
import org.http4k.format.ConfigurableJackson
import org.http4k.format.asConfigurable
import org.http4k.format.text
import org.http4k.format.withStandardMappings

@Suppress("Unused")
object HotelBookingJsonConfiguration : ConfigurableJackson(standardConfig())

private fun standardConfig(): ObjectMapper = KotlinModule.Builder()
    .build()
    .asConfigurable(mapper)
    .withStandardMappings()
    .text(CompanyId::of, CompanyId::value)
    .text(EmployeeId::of, EmployeeId::value)
    .text(HotelId::of, HotelId::value)
    .done()
    .deactivateDefaultTyping()
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

private val mapper: ObjectMapper = JsonMapper.builder()
    .addModule(AfterburnerModule())
    .addModule(BlackbirdModule())
    .build()
