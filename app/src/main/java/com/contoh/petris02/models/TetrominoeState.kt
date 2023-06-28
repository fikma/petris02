package com.contoh.petris02.models

import android.graphics.Point
import com.contoh.petris02.ui.theme.tetrominoeColors

enum class TetrominoeType {
    O,
    L,
    T,
    Z,
    N,
    I
}

val tetrominoeShapes = listOf(
    // shape o
    listOf(
        BlockState(Position(0, 0)),
        BlockState(Position(1, 0)),
        BlockState(Position(0, 1)),
        BlockState(Position(1, 1)),
    ).toCollection(TetrominoeBlocks(TetrominoeType.O)),
    // shape I
    listOf(
        BlockState(Position(0, 0)),
        BlockState(Position(0, 1)),
        BlockState(Position(0, 2)),
        BlockState(Position(0, 3)),
    ).toCollection(TetrominoeBlocks(TetrominoeType.I)),
    // shape T
    listOf(
        BlockState(Position(0, 0)),
        BlockState(Position(1, 0)),
        BlockState(Position(2, 0)),
        BlockState(Position(1, 1)),
    ).toCollection(TetrominoeBlocks(TetrominoeType.T)),
    // shape z
    listOf(
        BlockState(Position(0, 0)),
        BlockState(Position(1, 0)),
        BlockState(Position(1, 1)),
        BlockState(Position(2, 1)),
    ).toCollection(TetrominoeBlocks(TetrominoeType.Z)),
    // shape N
    listOf(
        BlockState(Position(0, 0)),
        BlockState(Position(0, 1)),
        BlockState(Position(1, 1)),
        BlockState(Position(1, 2)),
    ).toCollection(TetrominoeBlocks(TetrominoeType.N)),
    // shape L
    listOf(
        BlockState(Position(0, 0)),
        BlockState(Position(0, 1)),
        BlockState(Position(0, 2)),
        BlockState(Position(1, 2)),
    ).toCollection(TetrominoeBlocks(TetrominoeType.L)),
)

data class TetrominoeBlocks(
    val shape: TetrominoeType,
    val element: MutableList<BlockState> = mutableListOf()
) : ArrayList<BlockState>(element)

data class TetrominoeState(
    var direction: Point = Point(0, 0),
    var blocks: TetrominoeBlocks = tetrominoeShapes[(tetrominoeShapes.indices).random()]
)