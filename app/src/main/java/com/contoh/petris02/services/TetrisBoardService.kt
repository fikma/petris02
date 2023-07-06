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

fun getBoardPosition(position: Position, boardXcount: Int = 10) : Int {
    var result = 0
    result = position.x + (position.y * boardXcount)
    return result
}

fun clearLine(boardState: BoardState) {
    for (y in boardState.ySize - 1 downTo 0) {
        var counter = 0
        for (x in 0 until boardState.xSize) {
            val pos = getBoardPosition(Position(x, y))
            if (boardState.blocks[pos].type != BlockType.EMPTY)
                counter += 1
        }

        if (counter >= boardState.xSize) {
            moveLinesDown(Position(0, y), boardState.blocks)
        }
    }
}

fun moveLinesDown(startAt: Position, boardBlocks: SnapshotStateList<BlockState>) {
    for (y in startAt.y downTo 1) {
        if (startAt.y < 0) continue
        for (x in 9 downTo 0) {
            val pos = getBoardPosition(Position(x, y))
            val posMinusOne = getBoardPosition(Position(x, y - 1))
            boardBlocks[pos] = boardBlocks[posMinusOne].copy()
        }
    }
}

fun setTetrominoeToBoard(
    tetrominoeBlocks: TetrominoeBlocks,
    boardBlocks: SnapshotStateList<BlockState>,
    setTypeToBoard: Boolean = false,
    boardXsize: Int = 10
) {
    for (index in 0 until tetrominoeBlocks.size) {
        if (tetrominoeBlocks[index].position.y < 0 || tetrominoeBlocks[index].position.y >= 20)
            continue
        if (tetrominoeBlocks[index].position.x < 0 || tetrominoeBlocks[index].position.x >= 10)
            continue
        boardBlocks.set(
            getBoardPosition(tetrominoeBlocks[index].position, boardXsize),
            if (setTypeToBoard)
                tetrominoeBlocks[index].copy(type = tetrominoeBlocks[index].type)
            else
                tetrominoeBlocks[index].copy(type = BlockType.EMPTY)
        )
    }
}