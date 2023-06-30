package com.contoh.petris02.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.contoh.petris02.commands.MoveCommand
import com.contoh.petris02.models.BoardState
import com.contoh.petris02.models.Position
import com.contoh.petris02.models.TetrominoeState
import com.contoh.petris02.services.clearBoardColor
import com.contoh.petris02.services.isTetrominoeOutsideBoard
import com.contoh.petris02.services.setTetrominoeToBoard
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TetrominoeViewModel @Inject constructor(
    private val _boardState: BoardState,
    private val _tetrominoeState: TetrominoeState,
) : ViewModel() {
    val moveLeft = ::_moveLeft
    val moveRight = ::_moveRight
    val rotate = ::_rotate
    val pullDown = ::_pullDown

    private fun xMovement(direction: Position) {
        clearBoardColor(_boardState.blocks)

        val moveCommand = MoveCommand(direction, _tetrominoeState.blocks)
        moveCommand.execute()
        if (isTetrominoeOutsideBoard(_tetrominoeState.blocks))
            moveCommand.undo()

        setTetrominoeToBoard(_tetrominoeState.blocks, _boardState.blocks)
    }

    private fun _moveLeft() {
        xMovement(Position.LEFT())
    }

    private fun _moveRight() {
        xMovement(Position.RIGHT())
    }

    private fun _rotate() {
        Log.i(TetrominoeViewModel::class.simpleName, "rotate")
    }

    private fun _pullDown() {
        Log.i(TetrominoeViewModel::class.simpleName, "pulldown")
    }
}