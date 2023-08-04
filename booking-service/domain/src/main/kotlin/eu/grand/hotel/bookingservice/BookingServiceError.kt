package eu.grand.hotel.bookingservice

sealed interface BookingServiceError {
    data object BookingNotAllowed : BookingServiceError
    data object UnknownHotel : BookingServiceError
    data object InvalidBookingRange : BookingServiceError
    data object InternalError : BookingServiceError
}
