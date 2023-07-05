package com.contoh.petris02.viewModels

import androidx.lifecycle.ViewModel
import com.contoh.petris02.commands.MoveCommand
import com.contoh.petris02.commands.RotateCommand
import com.contoh.petris02.models.*
import com.contoh.petris02.services.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TetrominoeViewModel @Inject constructor(
    private val _boardState: BoardState,
    val _tetrominoeState: TetrominoeState,
) : ViewModel() {
    val moveLeft = ::_moveLeft
    val moveRight = ::_moveRight
    val rotate = ::_rotate
    val pullDown = ::_pullDown

    private fun xMovement(direction: Position) {
        var undoFlag = false
        val moveCommand = MoveCommand(direction, _tetrominoeState.blocks)

        clearBoardColor(_boardState.blocks)
        moveCommand.execute()

        if (isCollideWithTetrominoeBlock(_tetrominoeState.blocks, _boardState.blocks))
            undoFlag = true

        if (isTetrominoeOutsideBoard(_tetrominoeState.blocks, checkXonly = true))
            undoFlag = true

        if (undoFlag)
            moveCommand.undo()

        setTetrominoeToBoard(
            _tetrominoeState.blocks,
            _boardState.blocks
        )
    }

    private fun _moveLeft() {
        xMovement(Position.LEFT())
    }

    private fun _moveRight() {
        xMovement(Position.RIGHT())
    }

    private fun _rotate() {
        var undoFlag = false
        val rotateCommand = RotateCommand(_tetrominoeState.blocks, true)
        clearBoardColor(_boardState.blocks)

        rotateCommand.execute()

        if (isCollideWithTetrominoeBlock(_tetrominoeState.blocks, _boardState.blocks))
            undoFlag = true

        if (isTetrominoeOutsideBoard(_tetrominoeState.blocks, checkXonly = true))
            undoFlag = true

        if (undoFlag)
            rotateCommand.undo()

        setTetrominoeToBoard(
            _tetrominoeState.blocks,
            _boardState.blocks
        )
    }

    private fun _pullDown() {
        moveTetrominoeDown(
            _tetrominoeState,
            _boardState.blocks
        )
    }
}