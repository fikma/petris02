package com.contoh.petris02.services

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.contoh.petris02.commands.MoveCommand
import com.contoh.petris02.models.*
import com.contoh.petris02.ui.theme.tetrominoeColors

fun isGameOver(tetrominoeBlocks: TetrominoeBlocks) : Boolean {
    tetrominoeBlocks.forEach {
        if (it.position.y < 0) {
            return true
        }
    }

    return false
}

fun resetTetrominoe() : TetrominoeBlocks {
    val tetrominoeShapeBuilder = TetrominoeBlocksBuilder()
    val randomIndex = (tetrominoeShapeBlocks.indices).random()
    val result = tetrominoeShapeBuilder.build(randomIndex)
    val randomColor = tetrominoeColors[(tetrominoeColors.indices).random()]

    for (index in 0 until result.size) {
        result[index].color = randomColor
        result[index].type = BlockType.NORMAL
    }

    moveTetrominoe(Position(0, -3), result)

    return result
}

// credit: https://youtube.com/watch?v=zH_omFPqMO4
fun rotateTetrominoe(tetrominoeBlocks: TetrominoeBlocks, clockWise: Boolean = true) {
    if (tetrominoeBlocks.shape == TetrominoeType.O) return

    val pivot = tetrominoeBlocks[1]

    for (index in 0 until tetrominoeBlocks.size) {
        val x = tetrominoeBlocks[index].position.y - pivot.position.y
        val y = tetrominoeBlocks[index].position.x - pivot.position.x

        if (clockWise) {
            tetrominoeBlocks[index].position.x = pivot.position.x - x
            tetrominoeBlocks[index].position.y = pivot.position.y + y
        } else {
            tetrominoeBlocks[index].position.x = pivot.position.x + x
            tetrominoeBlocks[index].position.y = pivot.position.y - y
        }
    }

}

fun moveTetrominoeDown(
    tetrominoeBlocks: TetrominoeBlocks,
    boardBlocks: SnapshotStateList<BlockState>
) {
    val moveCommand = MoveCommand(Position.DOWN(), tetrominoeBlocks)
    var undoFlag = false
    while (true) {
        moveCommand.execute()
        if (isTetrominoeOutsideBoard(tetrominoeBlocks, checkYonly = true))
            undoFlag = true
        if (isCollideWithTetrominoeBlock(tetrominoeBlocks, boardBlocks))
            undoFlag = true

        if (undoFlag) {
            moveCommand.undo()
            setTetrominoeToBoard(
                tetrominoeBlocks,
                boardBlocks,
                true
            )
            return
        }
    }
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
        if (tetrominoeBlocks[index].position.y < 0) continue
        if (checkXonly) {
            if (tetrominoeBlocks[index].position.x < 0) return true
            if (tetrominoeBlocks[index].position.x >= size.x) return true
        }
        if (checkYonly)
            if (tetrominoeBlocks[index].position.y >= 20) return true
    }

    return false
}