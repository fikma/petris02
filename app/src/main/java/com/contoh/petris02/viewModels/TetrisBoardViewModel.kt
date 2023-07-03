package com.contoh.petris02.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.contoh.petris02.BuildConfig
import com.contoh.petris02.commands.MoveCommand
import com.contoh.petris02.models.*
import com.contoh.petris02.services.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TetrisBoardViewModel @Inject constructor(
    private val _boardState: BoardState,
    private val _gameState: GameState,
    private val _tetrominoeState: TetrominoeState,
) : ViewModel() {
    val boardState = _boardState
    var tetrominoeType: MutableState<String> = mutableStateOf(_tetrominoeState.blocks.shape.toString())

    init {
        _gameState.task.add(
            TaskWrapper(0, this::clear)
        )
        _gameState.task.add(
            TaskWrapper(1, this::resetTetrominoeTypeName)
        )
        _gameState.task.add(
            TaskWrapper(9999, this::setTetrominoeColor)
        )
        _gameState.task.add(
            TaskWrapper(1, this::moveTetrominoeDownOnePosition)
        )

        _tetrominoeState.blocks = resetTetrominoe()
        setTetrominoeToBoard(
            _tetrominoeState.blocks,
            _boardState.blocks
        )
    }

    fun toggleLoop() {
        _boardState.toggle.value = !_boardState.toggle.value
    }
    
    private fun moveTetrominoeDownOnePosition() {
        var undoFlag = false
        val moveCommand = MoveCommand(Position.DOWN(), _tetrominoeState.blocks)
        moveCommand.execute()

        if (isTetrominoeOutsideBoard(_tetrominoeState.blocks, checkYonly = true))
            undoFlag = true

        if (isCollideWithTetrominoeBlock(_tetrominoeState.blocks, _boardState.blocks))
            undoFlag = true

        if (undoFlag) {
            moveCommand.undo()
            setTetrominoeToBoard(
                _tetrominoeState.blocks,
                _boardState.blocks,
                true
            )

            val newTetrominoeBlocks = resetTetrominoe()
            for (index in 0 until _tetrominoeState.blocks.size) {
                _tetrominoeState.blocks[index] = newTetrominoeBlocks[index].copy()
            }
        }

        clearLine(boardState)
    }

    private fun resetTetrominoeTypeName() {
        if (BuildConfig.DEBUG)
            tetrominoeType.value = _tetrominoeState.blocks.shape.toString()
    }

    // Must run first
    private fun clear() {
        clearBoardColor(_boardState.blocks)
    }

    // Must run last
    private fun setTetrominoeColor() {
        setTetrominoeToBoard(
            _tetrominoeState.blocks,
            _boardState.blocks
        )
    }
}