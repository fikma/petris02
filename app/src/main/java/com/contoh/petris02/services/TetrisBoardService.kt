package com.contoh.petris02.services

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.contoh.petris02.models.*

fun resetBoard(boardBlocks: SnapshotStateList<BlockState>) {
    for (index in 0 until boardBlocks.size) {
        boardBlocks.set(index, BlockState())
    }
}

fun clearBoardColor(boardBlocks: SnapshotStateList<BlockState>) {
    for (index in 0 until boardBlocks.size) {
        if (boardBlocks[index].type == BlockType.EMPTY)
            boardBlocks.set(index, BlockState(color = null))
    }
}

fun getBoardPosition(
    position: Position,
    boardSize: Position
) : Int {
    var result = 0
    result = position.x + (position.y * boardSize.x)
    return result
}

fun clearLine(boardState: BoardState) {
    for (y in boardState.boardSize.y - 1 downTo 0) {
        var counter = 0
        for (x in 0 until boardState.boardSize.x) {
            val pos = getBoardPosition(Position(x, y), boardState.boardSize)
            if (boardState.blocks[pos].type != BlockType.EMPTY)
                counter += 1
        }

        if (counter >= boardState.boardSize.x) {
            moveLinesDown(Position(0, y), boardState.blocks, boardState.boardSize)
        }
    }
}

fun moveLinesDown(
    startAt: Position,
    boardBlocks: SnapshotStateList<BlockState>,
    boardSize: Position
) {
    for (y in startAt.y downTo 1) {
        if (startAt.y < 0) continue
        for (x in boardSize.x - 1 downTo 0) {
            val pos = getBoardPosition(Position(x, y), boardSize)
            val posMinusOne = getBoardPosition(Position(x, y - 1), boardSize)
            boardBlocks[pos] = boardBlocks[posMinusOne].copy()
        }
    }
}

fun setTetrominoeToBoard(
    tetrominoeBlocks: TetrominoeBlocks,
    boardBlocks: SnapshotStateList<BlockState>,
    setTypeToBoard: Boolean = false,
    boardSize: Position
) {
    for (index in 0 until tetrominoeBlocks.size) {
        if (tetrominoeBlocks[index].position.y < 0 || tetrominoeBlocks[index].position.y >= boardSize.y)
            continue
        if (tetrominoeBlocks[index].position.x < 0 || tetrominoeBlocks[index].position.x >= boardSize.x)
            continue
        boardBlocks.set(
            getBoardPosition(tetrominoeBlocks[index].position, boardSize = boardSize),
            if (setTypeToBoard)
                tetrominoeBlocks[index].copy(type = tetrominoeBlocks[index].type)
            else
                tetrominoeBlocks[index].copy(type = BlockType.EMPTY)
        )
    }
}