package utils

import com.fasterxml.jackson.module.kotlin.KotlinModule
import eu.grand.hotel.core.CompanyId
import eu.grand.hotel.core.EmployeeId
import eu.grand.hotel.core.HotelId
import org.http4k.format.ConfigurableJackson
import org.http4k.format.asConfigurable
import org.http4k.format.value
import org.http4k.format.withStandardMappings

object Json : ConfigurableJackson(
    KotlinModule.Builder().build()
        .asConfigurable()
        .withStandardMappings()
        .value(HotelId)
        .value(EmployeeId)
        .value(CompanyId)
        .done()

)
