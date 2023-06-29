package eu.grand.hotel.bookingservice

sealed interface BookingServiceError {
    object BookingNotAllowed : BookingServiceError
    object UnknownHotel : BookingServiceError
    object InvalidBookingRange : BookingServiceError
}
