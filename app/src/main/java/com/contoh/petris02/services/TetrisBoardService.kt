package com.contoh.petris02.services

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.contoh.petris02.models.BlockState
import com.contoh.petris02.models.Position
import com.contoh.petris02.models.TetrominoeBlocks

fun clearBoardColor(boardBlocks: SnapshotStateList<BlockState>) {
    for (index in 0 until boardBlocks.size) {
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
    tetrominoeBlocks.forEach { block ->

        boardBlocks.set(
            getBoardPosition(block.position),
            if (setTypeToBoard) block.copy(type = block.type) else block
        )
    }
}