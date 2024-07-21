package eu.grand.hotel.bookingservice

import org.http4k.config.EnvironmentKey
import org.http4k.lens.of
import org.http4k.lens.uri

object BookingServiceApiSettings {
    val BOOKING_POLICY_SERVICE_URL by EnvironmentKey.uri().of().required()
    val HOTEL_SERVICE_URL by EnvironmentKey.uri().of().required()
}
