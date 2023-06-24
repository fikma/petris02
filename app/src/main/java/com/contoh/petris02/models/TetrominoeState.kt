package com.contoh.petris02.models

import android.graphics.Point
import com.contoh.petris02.ui.theme.tetrominoeColors

enum class TetrominoeType {
    O,
    L,
    T,
    Z,
    N
}

val tetrominoeShapes = listOf(
    // shape o
    listOf(
        BlockState(Point(0, 0)),
        BlockState(Point(1, 0)),
        BlockState(Point(0, 1)),
        BlockState(Point(1, 1)),
    ).toCollection(TetrominoeBlocks(TetrominoeType.O)),
    // shape L
    listOf(
        BlockState(Point(0, 0)),
        BlockState(Point(0, 1)),
        BlockState(Point(0, 2)),
        BlockState(Point(0, 3)),
    ).toCollection(TetrominoeBlocks(TetrominoeType.L)),
    // shape T
    listOf(
        BlockState(Point(0, 0)),
        BlockState(Point(1, 0)),
        BlockState(Point(2, 0)),
        BlockState(Point(1, 1)),
    ).toCollection(TetrominoeBlocks(TetrominoeType.T)),
    // shape z
    listOf(
        BlockState(Point(0, 0)),
        BlockState(Point(1, 0)),
        BlockState(Point(1, 1)),
        BlockState(Point(2, 1)),
    ).toCollection(TetrominoeBlocks(TetrominoeType.Z)),
    // shape N
    listOf(
        BlockState(Point(0, 0)),
        BlockState(Point(0, 1)),
        BlockState(Point(1, 1)),
        BlockState(Point(1, 2)),
    ).toCollection(TetrominoeBlocks(TetrominoeType.N)),
)

data class TetrominoeBlocks(
    val shape: TetrominoeType,
    val element: MutableList<BlockState> = mutableListOf()
) : ArrayList<BlockState>(element)

data class TetrominoeState(
    var direction: Point = Point(0, 0),
    var blocks: TetrominoeBlocks = tetrominoeShapes[(tetrominoeShapes.indices).random()]
)