package com.kristianskokars.features.sudoku.domain

import kotlin.random.Random

fun startSudokuGame(randomNumberCount: Int): Array<IntArray> {
    val size = 9
    val board = createBoard(size)
    board.generateRandomNumbersOnBoard(randomNumberCount)
    return board
}

fun Array<IntArray>.generateRandomNumbersOnBoard(randomNumberCount: Int) {
    val blockSize = 3
    val numberCountToRemove = (this.size * this.size) - randomNumberCount

    fillDiagonalBlocks(this, blockSize)
    fillOtherBlocks(this, blockSize, 0, blockSize)
    removeRandomNumbers(this, numberCountToRemove)
}

fun removeRandomNumbers(sudokuBoard: Array<IntArray>, numberCountToRemove: Int) {
    var localNumberCountToRemove = numberCountToRemove

    while (localNumberCountToRemove != 0) {
        val randomRowIndex = Random.nextInt(0, sudokuBoard.size)
        val randomColumnIndex = Random.nextInt(0, sudokuBoard.size)

        if (sudokuBoard[randomRowIndex][randomColumnIndex] != 0) {
            localNumberCountToRemove--
            sudokuBoard[randomRowIndex][randomColumnIndex] = 0
        }
    }
}

fun hasReachedEndOfBoard(sudokuBoard: Array<IntArray>, rowIndex: Int, columnIndex: Int) =
    rowIndex == sudokuBoard.size - 1 && columnIndex == sudokuBoard.size

fun fillOtherBlocks(
    sudokuBoard: Array<IntArray>,
    blockSize: Int,
    startingRowIndex: Int,
    startingColumnIndex: Int
): Boolean {
    var rowIndex = startingRowIndex
    var columnIndex = startingColumnIndex

    if (hasReachedEndOfBoard(sudokuBoard, rowIndex, columnIndex)) return true

    if (columnIndex == sudokuBoard.size) {
        rowIndex += 1
        columnIndex = 0
    }

    if (sudokuBoard[rowIndex][columnIndex] != 0) {
        return fillOtherBlocks(sudokuBoard, blockSize, rowIndex, columnIndex + 1)
    }

    var number = 1
    while (number <= sudokuBoard.size) {
        if (checkIfNumberIsInAValidSpot(sudokuBoard, blockSize, rowIndex, columnIndex, number)) {
            sudokuBoard[rowIndex][columnIndex] = number

            if (fillOtherBlocks(sudokuBoard, blockSize, rowIndex, columnIndex + 1)) return true
            sudokuBoard[rowIndex][columnIndex] = 0
        }
        number++
    }

    return false
}

fun fillDiagonalBlocks(sudokuBoard: Array<IntArray>, blockSize: Int) {
    var i = 0
    while (i < sudokuBoard.size) {
        fillBlock(sudokuBoard, blockSize, i, i)
        i += blockSize
    }
}

fun fillBlock(sudokuBoard: Array<IntArray>, blockSize: Int, rowIndex: Int, columnIndex: Int) {
    for (i in 0 until blockSize) {
        for (j in 0 until blockSize) {
            var randomNumber: Int
            var isNumberInAValidSpot: Boolean
            do {
                randomNumber = Random.nextInt(1, 10)
                isNumberInAValidSpot = checkIfUnusedInBlock(sudokuBoard, blockSize, rowIndex, columnIndex, randomNumber)
            } while (!isNumberInAValidSpot)

            sudokuBoard[rowIndex + i][columnIndex + j] = randomNumber
        }
    }
}

fun checkIfNumberIsInAValidSpot(
    sudokuBoard: Array<IntArray>,
    blockSize: Int,
    rowIndex: Int,
    columnIndex: Int,
    number: Int
): Boolean {
    if (!checkIfUnusedInColumn(sudokuBoard, rowIndex, columnIndex, number)) return false
    if (!checkIfUnusedInRow(sudokuBoard, rowIndex, columnIndex, number)) return false
    return checkIfUnusedInBlock(sudokuBoard, blockSize, rowIndex, columnIndex, number)
}

fun checkIfUnusedInColumn(sudokuBoard: Array<IntArray>, rowIndex: Int, columnIndex: Int, number: Int): Boolean {
    for (currentRowIndex in sudokuBoard.indices) {
        if (currentRowIndex != rowIndex && sudokuBoard[currentRowIndex][columnIndex] == number) return false
    }
    return true
}

fun checkIfUnusedInRow(sudokuBoard: Array<IntArray>, rowIndex: Int, columnIndex: Int, number: Int): Boolean {
    for (currentColumnIndex in sudokuBoard.indices) {
        if (currentColumnIndex != columnIndex && sudokuBoard[rowIndex][currentColumnIndex] == number) return false
    }
    return true
}

fun checkIfUnusedInBlock(
    sudokuBoard: Array<IntArray>,
    blockSize: Int,
    rowIndex: Int,
    columnIndex: Int,
    number: Int
): Boolean {
    val rowIndexInBlock = rowIndex - rowIndex % blockSize
    val columnIndexInBlock = columnIndex - columnIndex % blockSize

    for (i in 0 until blockSize) {
        for (j in 0 until blockSize) {
            if (
                (rowIndexInBlock + i != rowIndex && columnIndexInBlock + j != columnIndex) &&
                sudokuBoard[rowIndexInBlock + i][columnIndexInBlock + j] == number
            ) return false
        }
    }
    return true
}

fun createBoard(size: Int): Array<IntArray> = Array(size) {
    IntArray(size)
}

// TODO: rework
fun checkIfBoardIsValid(
    sudokuBoard: Array<IntArray>,
    blockSize: Int,
): Boolean {
    for (i in sudokuBoard.indices) {
        for (j in sudokuBoard[i].indices) {
            if (!checkIfUnusedInColumn(sudokuBoard, i, j, sudokuBoard[i][j])) {
                return false
            }
            if (!checkIfUnusedInRow(sudokuBoard, i, j, sudokuBoard[i][j])) {
                return false
            }
            if (!checkIfUnusedInBlock(sudokuBoard, blockSize, i, j, sudokuBoard[i][j])) {
                return false
            }
        }
    }
    return true
}
