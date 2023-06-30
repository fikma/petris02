package com.contoh.petris02.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class BlockType {
    EMPTY,
    NORMAL
}

data class Position(
    var x: Int,
    var y: Int
) {
    operator fun plus(position: Position) : Position {
        return Position(this.x + position.x, this.y + position.y)
    }

    operator fun times(number: Int) : Position {
        return Position(this.x * number, this.y * number)
    }

    companion object Factory {
        fun RIGHT() : Position = Position(1, 0)
        fun LEFT() : Position = Position(-1, 0)
        fun DOWN() : Position = Position(0, 1)
    }
}

data class BlockState(
    var position: Position = Position(0, 0),
    val size: Dp = 30.dp,
    var color: Color? = null,
    var type: BlockType = BlockType.EMPTY
) {
    fun copy(
        position: Position?,
        size: Dp?,
        color: Color?,
        type: BlockType?
    ) : BlockState {
        return BlockState(
            position = position ?: this.position,
            size = size ?: this.size,
            color = color ?: this.color,
            type = type ?: this.type
        )
    }
}