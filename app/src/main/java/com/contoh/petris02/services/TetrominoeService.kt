package com.contoh.petris02.services

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.contoh.petris02.commands.MoveCommand
import com.contoh.petris02.models.*
import com.contoh.petris02.ui.theme.tetrominoeColors
import java.util.*

fun setNextTetrominoeFromQueue(blocksQueue: Queue<TetrominoeBlocks>, activeTetrominoe: TetrominoeBlocks) {
    val nextBlocks = blocksQueue.remove()
    if (nextBlocks.isNotEmpty()) {
        for (index in 0 until activeTetrominoe.size) {
            activeTetrominoe[index] = nextBlocks[index].copy()
        }
    }
    blocksQueue.add(resetTetrominoe())
}

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
    val dimension = Position(0, 0)

    for (index in 0 until result.size) {
        result[index].color = randomColor
        result[index].type = BlockType.NORMAL
        dimension.x = if (dimension.x >= result[index].position.x) dimension.x else result[index].position.x
        dimension.y = if (dimension.y >= result[index].position.y) dimension.y else result[index].position.y
    }

    result.dimension.x = dimension.x
    result.dimension.y = dimension.y

    return result
}

fun moveTetrominoeToCenter(tetrominoeBlocks: TetrominoeBlocks, boardWidth: Int) {
    val xPointOffset = boardWidth / 2 - tetrominoeBlocks.dimension.x

    moveTetrominoe(Position(xPointOffset, 0), tetrominoeBlocks)
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
    tetrominoeState: TetrominoeState,
    boardBlocks: SnapshotStateList<BlockState>
) {
    val moveCommand = MoveCommand(Position.DOWN(), tetrominoeState.blocks)
    var undoFlag = false
    while (true) {
        moveCommand.execute()
        if (isTetrominoeOutsideBoard(tetrominoeState.blocks, checkYonly = true))
            undoFlag = true
        if (isCollideWithTetrominoeBlock(tetrominoeState.blocks, boardBlocks))
            undoFlag = true

        if (undoFlag) {
            moveCommand.undo()
            setTetrominoeToBoard(
                tetrominoeState.blocks,
                boardBlocks,
                true
            )

            val nextTetrominoe = tetrominoeState.blocksQueue.peek()
            if (nextTetrominoe != null) {
                clearBoardColor(tetrominoeState.nextTetrominoeBoard)
                setTetrominoeToBoard(
                    nextTetrominoe,
                    tetrominoeState.nextTetrominoeBoard,
                    boardXsize = 4
                )
            }
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