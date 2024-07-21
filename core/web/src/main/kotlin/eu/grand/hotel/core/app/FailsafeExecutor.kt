package eu.grand.hotel.core.app

import dev.failsafe.Failsafe
import dev.failsafe.FailsafeExecutor
import dev.failsafe.RateLimiter
import org.http4k.core.Response
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

val failsafeExecutor: FailsafeExecutor<Response> =
    Failsafe
        .with(
            RateLimiter
                .smoothBuilder<Response>(100L, 1.seconds.toJavaDuration())
                .withMaxWaitTime(100.milliseconds.toJavaDuration())
                .build()
        )
