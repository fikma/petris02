package com.contoh.petris02.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.contoh.petris02.models.*
import com.contoh.petris02.services.clearBoardColor
import com.contoh.petris02.services.resetTetrominoe
import com.contoh.petris02.services.setTetrominoeToBoard
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
            TaskWrapper(0, ::reset)
        )
    }

    fun toggleLoop() {
        _boardState.toggle.value = !_boardState.toggle.value
    }

    private fun reset() {
        clearBoardColor(_boardState.blocks)

        _tetrominoeState.blocks = resetTetrominoe()
        setTetrominoeToBoard(
            _tetrominoeState.blocks,
            _boardState.blocks
        )
        tetrominoeType.value = _tetrominoeState.blocks.shape.toString()
    }
}