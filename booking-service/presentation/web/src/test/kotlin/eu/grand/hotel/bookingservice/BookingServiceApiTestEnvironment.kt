package eu.grand.hotel.bookingservice

import eu.grand.hotel.bookingservice.BookingServiceApiSettings.BOOKING_POLICY_SERVICE_URL
import eu.grand.hotel.bookingservice.BookingServiceApiSettings.HOTEL_SERVICE_URL
import org.http4k.config.Environment
import org.http4k.core.Uri

val BookingServiceApiTestEnvironment =
    Environment.defaults(
        BOOKING_POLICY_SERVICE_URL of Uri.of("http://booking-policy"),
        HOTEL_SERVICE_URL of Uri.of("http://hotel")
    )
