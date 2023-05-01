package com.kristianskokars

import com.kristianskokars.lib.dotenv
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.kristianskokars.plugins.*

fun main() {
    embeddedServer(Netty, port = dotenv["PORT"].toInt(), host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureSerialization()
    configureRouting()
}
