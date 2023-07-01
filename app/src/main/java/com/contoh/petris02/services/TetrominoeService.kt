package com.contoh.petris02.services

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.contoh.petris02.models.*
import com.contoh.petris02.ui.theme.tetrominoeColors

fun resetTetrominoe() : TetrominoeBlocks {
    val result = tetrominoeShapes[(tetrominoeShapes.indices).random()]
    val randomColor = tetrominoeColors[(tetrominoeColors.indices).random()]

    for (index in 0 until result.size) {
        result[index].color = randomColor
        result[index].type = BlockType.NORMAL
    }

    return result
}

fun moveTetrominoe(direction: Position, tetrominoeBlocks: TetrominoeBlocks) {
    for (index in 0 until tetrominoeBlocks.size) {
        val newPosition = tetrominoeBlocks[index].position + direction
        tetrominoeBlocks[index].position = newPosition
    }
}

fun isCollideWithTetrominoeBlock(
    tetrominoeBlocks: TetrominoeBlocks,
    boardBlocks: SnapshotStateList<BlockState>
) : Boolean {
    tetrominoeBlocks.forEach {
        val blockPosition = getBoardPosition(it.position)
        if (boardBlocks[blockPosition].type != BlockType.EMPTY)
            return true
    }

    return false
}

fun isTetrominoeOutsideBoard(
    tetrominoeBlocks: TetrominoeBlocks,
    size: Position = Position(10, 20)
) : Boolean {
    tetrominoeBlocks.forEach {
        if (it.position.x < 0) return true
        if (it.position.x >= size.x) return true
        if (it.position.y >= 20) return true
    }

    return false
}