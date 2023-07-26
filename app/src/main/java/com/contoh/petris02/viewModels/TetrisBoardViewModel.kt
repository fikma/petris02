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

        val nextTetrominoe = _tetrominoeState.blocksQueue.peek()
        if (nextTetrominoe != null) {
            clearBoardColor(_tetrominoeState.nextTetrominoeBoard)
            setTetrominoeToBoard(
                nextTetrominoe,
                _tetrominoeState.nextTetrominoeBoard,
                true,
                4
            )
        }
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
            // start when game over logic
            if (isGameOver(_tetrominoeState.blocks)) {
                _tetrominoeState.blocksQueue.add(resetTetrominoe())
                setNextTetrominoeFromQueue(_tetrominoeState.blocksQueue, _tetrominoeState.blocks)
                resetBoard(_boardState.blocks)
                _gameState.stateText.value = "Game over"
                _gameState.openDrawer()
                return
            }
            // end game over logic

            moveCommand.undo()
            setTetrominoeToBoard(
                _tetrominoeState.blocks,
                _boardState.blocks,
                true
            )
            setNextTetrominoeFromQueue(_tetrominoeState.blocksQueue, _tetrominoeState.blocks)
            moveTetrominoe(Position(0, -3), _tetrominoeState.blocks)

            val nextTetrominoe = _tetrominoeState.blocksQueue.peek()
            if (nextTetrominoe != null) {
                clearBoardColor(_tetrominoeState.nextTetrominoeBoard)
                setTetrominoeToBoard(
                    nextTetrominoe,
                    _tetrominoeState.nextTetrominoeBoard,
                    boardXsize = 4
                )
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