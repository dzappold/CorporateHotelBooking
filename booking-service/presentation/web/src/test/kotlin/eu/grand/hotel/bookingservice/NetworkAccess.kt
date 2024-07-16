package eu.grand.hotel.bookingservice

import eu.grand.hotel.bookingservice.BookingServiceApiSettings.BOOKING_POLICY_SERVICE_URL
import eu.grand.hotel.bookingservice.BookingServiceApiSettings.HOTEL_SERVICE_URL
import eu.grand.hotel.bookingservice.hotel.FakeHttpHotelService
import eu.grand.hotel.bookingservice.policy.FakeHttpBookingPolicyService
import eu.grand.hotel.bookingservice.ports.BookingPolicyService
import eu.grand.hotel.bookingservice.ports.HotelService
import eu.grand.hotel.core.Hotel
import eu.grand.hotel.core.HotelId
import eu.grand.hotel.core.RoomTypes
import org.http4k.config.Environment
import org.http4k.routing.reverseProxy

fun networkAccess(env: Environment, roomTypes: RoomTypes, hotelInfos: Map<HotelId, Hotel>) = reverseProxy(
    env[BOOKING_POLICY_SERVICE_URL].authority to BookingPolicyService.FakeHttpBookingPolicyService(roomTypes),
    env[HOTEL_SERVICE_URL].authority to HotelService.FakeHttpHotelService(hotelInfos),
)
