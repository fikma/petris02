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

    moveTetrominoe(Position(0, -3), result)

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
    for (index in 0 until tetrominoeBlocks.size) {
        if (tetrominoeBlocks[index].position.y < 0 || tetrominoeBlocks[index].position.y >= 20)
            continue
        if (tetrominoeBlocks[index].position.x < 0 || tetrominoeBlocks[index].position.x >= 10)
            continue
        val blockPosition = getBoardPosition(tetrominoeBlocks[index].position)
        if (boardBlocks[blockPosition].type != BlockType.EMPTY)
            return true
    }

    return false
}

fun isTetrominoeOutsideBoard(
    tetrominoeBlocks: TetrominoeBlocks,
    checkXonly: Boolean = false,
    checkYonly: Boolean = false,
    size: Position = Position(10, 20)
) : Boolean {
    for (index in 0 until tetrominoeBlocks.size) {
        if (tetrominoeBlocks[index].position.y < 0 || tetrominoeBlocks[index].position.y < 0) continue
        if (checkXonly) {
            if (tetrominoeBlocks[index].position.x < 0) return true
            if (tetrominoeBlocks[index].position.x >= size.x) return true
        }
        if (checkYonly)
            if (tetrominoeBlocks[index].position.y >= 20) return true
    }

    return false
}