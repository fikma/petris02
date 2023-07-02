package com.contoh.petris02.services

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.contoh.petris02.models.BlockState
import com.contoh.petris02.models.BlockType
import com.contoh.petris02.models.Position
import com.contoh.petris02.models.TetrominoeBlocks

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

fun setTetrominoeToBoard(
    tetrominoeBlocks: TetrominoeBlocks,
    boardBlocks: SnapshotStateList<BlockState>,
    setTypeToBoard: Boolean = false
) {
    for (index in 0 until tetrominoeBlocks.size) {
        if (tetrominoeBlocks[index].position.y < 0 || tetrominoeBlocks[index].position.y >= 20)
            continue
        if (tetrominoeBlocks[index].position.x < 0 || tetrominoeBlocks[index].position.x >= 10)
            continue
        boardBlocks.set(
            getBoardPosition(tetrominoeBlocks[index].position),
            if (setTypeToBoard)
                tetrominoeBlocks[index].copy(type = tetrominoeBlocks[index].type)
            else
                tetrominoeBlocks[index].copy(type = BlockType.EMPTY)
        )
    }
}