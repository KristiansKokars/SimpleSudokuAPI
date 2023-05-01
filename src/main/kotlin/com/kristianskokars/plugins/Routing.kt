package com.kristianskokars.plugins

import com.kristianskokars.features.sudoku.domain.checkIfBoardIsValid
import com.kristianskokars.features.sudoku.domain.startSudokuGame
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import kotlin.math.sqrt

fun Application.configureRouting() {
    routing {
        // TODO: does not work, gives a NetworkError
//        swaggerUI(path = "/", swaggerFile = "openapi/documentation.yaml")

        route("/board") {
            /**
             * Creates a valid 9x9 Sudoku board and returns it as a 2D Int array.
             */
            get {
                val board = startSudokuGame(81)
                call.respond(board)
            }
            /**
             * Verifies if the given 9x9 Sudoku board (given as a 2D Int array) is a valid Sudoku board (i.e. is solved)
             */
            post("/verify") {
                val board = call.receive<Array<IntArray>>()
                val isBoardValid = checkIfBoardIsValid(board, sqrt(board.size.toFloat()).toInt())
                call.respond(isBoardValid)
            }
        }

    }
}
