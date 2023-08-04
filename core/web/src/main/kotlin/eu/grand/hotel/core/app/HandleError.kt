package eu.grand.hotel.core.app

import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.SERVICE_UNAVAILABLE
import org.http4k.core.then
import org.http4k.events.Event.Companion.Error
import org.http4k.events.Events
import org.http4k.filter.ServerFilters.CatchAll
import org.http4k.filter.ServerFilters.CatchLensFailure

fun HandleError(events: Events) =
    CatchAll {
        events(Error("uncaught!", it))
        Response(SERVICE_UNAVAILABLE)
    }.then(CatchLensFailure { _, lensFailure ->
        events(Error(lensFailure.message.orEmpty(), lensFailure))
        Response(BAD_REQUEST)
    })
