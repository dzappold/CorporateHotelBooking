package utils

import org.http4k.server.Http4kServer

fun Http4kServer.registerShutdownHook() {
    Runtime.getRuntime().addShutdownHook(Thread { stop() })
}
