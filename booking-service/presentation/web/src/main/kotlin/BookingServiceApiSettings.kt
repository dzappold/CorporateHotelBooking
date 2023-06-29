import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.lens.of
import org.http4k.lens.uri

object BookingServiceApiSettings {
    val BOOKING_POLICY_SERVICE_URL by EnvironmentKey.uri().of().required()
    val HOTEL_SERVICE_URL by EnvironmentKey.uri().of().required()
}
