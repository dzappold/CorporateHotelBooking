import dev.forkhandles.result4k.Result4k
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.asResultOr
import dev.forkhandles.result4k.map
import dev.forkhandles.result4k.recover
import org.http4k.cloudnative.asK8sServer
import org.http4k.contract.openapi.OpenAPIJackson.auto
import org.http4k.core.Body
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status.Companion.I_M_A_TEAPOT
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.UNAUTHORIZED
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ServerFilters
import org.http4k.lens.Path
import org.http4k.lens.nonEmptyString
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Undertow

@JvmInline
value class HotelId(val id: String)

@JvmInline
value class HotelName(val name: String)

@JvmInline
value class CompanyId(val id: String)

@JvmInline
value class EmployeeId(val id: String)

class HotelBookingApi(private val hotelService: HotelRepository) {
    val hotelIds = Path.nonEmptyString().map(::HotelId, HotelId::id).of("hotelId")
    val hotelsBody = Body.auto<List<Hotel>>().toLens()
    val hotelBody = Body.auto<Hotel>().toLens()

    private val getHotelsHandler: HttpHandler = { request ->
        val result = hotelService.getHotels()
        result.map { hotels ->
            Response(OK).with(hotelsBody of hotels)
        }.recover { Response(I_M_A_TEAPOT) }
    }

    private val getHotelByIdHandler: HttpHandler = { request ->
        val hotelId = hotelIds(request)
        hotelService.findHotelBy(hotelId)
            .map { hotel -> Response(OK).with(hotelBody of hotel) }
            .recover { Response(NOT_FOUND) }
    }

    internal val routes: RoutingHttpHandler = routes(
        "/hotels" bind Method.GET to getHotelsHandler,
        "/hotels/{hotelId}" bind Method.GET to getHotelByIdHandler
    )

}

data class Hotel(val id: HotelId, val name: HotelName)

interface IHotelService {
    fun addHotel(id: HotelId, name: HotelName): Result4k<Hotel, HotelError>
    fun getHotels(): Result4k<List<Hotel>, HotelError>

    // maybe make roomnumber a roomname - can be that some hotels are using names
    fun setRoom(hotelID: HotelId, roomNumber: Int, roomType: String)
    fun findHotelBy(id: HotelId): Result4k<Hotel, HotelError>
}

sealed interface HotelError {
    data class UnknownHotel(val message: String) : HotelError
}

class HotelRepository : IHotelService {
    private val hotels: MutableMap<HotelId, Hotel> = mutableMapOf()
    private val roomTypes: MutableMap<Pair<HotelId, Int>, String> = mutableMapOf()

    override fun addHotel(id: HotelId, name: HotelName): Result4k<Hotel, HotelError> {
        hotels[id] = Hotel(id, name)
        return hotels[id].asResultOr { HotelError.UnknownHotel("just created and still doesn't exist") }
    }

    override fun getHotels(): Result4k<List<Hotel>, HotelError> =
        Success(hotels.values.toList())


    override fun setRoom(hotelID: HotelId, roomNumber: Int, roomType: String) {
        roomTypes[hotelID to roomNumber] = roomType
    }

    override fun findHotelBy(id: HotelId): Result4k<Hotel, HotelError> =
        hotels[id].asResultOr { HotelError.UnknownHotel("Hotel with id '${id.id}' doesn't exist!") }
}

fun main() {
    val hotelRepository = HotelRepository()
    val hotelBookingApi = HotelBookingApi(hotelRepository)

    hotelRepository.addHotel(HotelId("1"), HotelName("Hotel A"))
    hotelRepository.addHotel(HotelId("2"), HotelName("Hotel B"))


    val authFilter = Filter { next ->
        { request ->
            val authHeader = request.header("Authorization")
            if (authHeader == "Basic <your-auth-token>") {
                next(request)
            } else {
                Response(UNAUTHORIZED)
            }
        }
    }
    val server = authFilter.then(ServerFilters.CatchAll().then(hotelBookingApi.routes))
        .asK8sServer(::Undertow)
        .start()

    println("Server started on port ${server.port()}")

    Runtime.getRuntime().addShutdownHook(Thread {
        server.stop()
        println("Server stopped")
    })
}
