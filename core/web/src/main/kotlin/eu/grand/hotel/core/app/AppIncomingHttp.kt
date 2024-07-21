package eu.grand.hotel.core.app

import dev.failsafe.FailsafeExecutor
import org.http4k.core.Response
import org.http4k.core.then
import org.http4k.events.Events
import org.http4k.events.HttpEvent.Incoming
import org.http4k.filter.FailsafeFilter
import org.http4k.filter.ResponseFilters.ReportHttpTransaction
import org.http4k.filter.ServerFilters.GZip
import org.http4k.filter.ServerFilters.RequestTracing
import org.http4k.routing.RoutingHttpHandler

fun AppIncomingHttp(failsafeExecutor: FailsafeExecutor<Response>, events: Events, base: RoutingHttpHandler): RoutingHttpHandler =
    HandleError(events)
        .then(RequestTracing())
        .then(GZip())
        .then(ReportHttpTransaction { events(Incoming(it)) })
        .then(FailsafeFilter(failsafeExecutor))
        .then(base)
