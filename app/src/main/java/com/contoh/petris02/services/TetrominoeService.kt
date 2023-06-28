package com.contoh.petris02.services

import com.contoh.petris02.models.Position
import com.contoh.petris02.models.TetrominoeBlocks
import com.contoh.petris02.models.tetrominoeShapes
import com.contoh.petris02.ui.theme.tetrominoeColors
import com.contoh.petris02.viewModels.BlockType

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