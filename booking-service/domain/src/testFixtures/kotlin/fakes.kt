import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.asResultOr
import eu.grand.hotel.bookingservice.ports.BookingPolicyService
import eu.grand.hotel.bookingservice.BookingServiceError
import eu.grand.hotel.bookingservice.ports.HotelService
import eu.grand.hotel.core.hotel.Hotel
import eu.grand.hotel.core.hotel.HotelId
import eu.grand.hotel.core.hotel.RoomTypes

fun BookingPolicyService.Companion.FakeBookingPolicyService(companyPolicy: RoomTypes) =
    BookingPolicyService { employeeId, roomType -> Success(companyPolicy.contains(roomType)) }

fun HotelService.Companion.FakeHotelService(hotel: Map<HotelId, Hotel>) =
    HotelService { hotelId -> hotel[hotelId].asResultOr { BookingServiceError.UnknownHotel } }
