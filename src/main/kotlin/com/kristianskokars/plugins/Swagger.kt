package com.kristianskokars.plugins

import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Routing.configureSwagger() {
    // TODO: does not work, gives a NetworkError
    swaggerUI(path = "/", swaggerFile = "openapi/documentation.yaml")
}
