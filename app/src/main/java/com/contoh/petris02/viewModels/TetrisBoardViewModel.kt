package com.contoh.petris02.viewModels

import android.graphics.Point
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.contoh.petris02.models.*
import com.contoh.petris02.ui.theme.tetrominoeColors
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class BlockType {
    EMPTY,
    NORMAL
}

@HiltViewModel
class TetrisBoardViewModel @Inject constructor(
    private val _boardState: BoardState,
    private val _gameState: GameState,
    private val _tetrominoeState: TetrominoeState,
) : ViewModel() {
    val boardState = _boardState
    var tetrominoeType: MutableState<String> = mutableStateOf(_tetrominoeState.blocks.shape.toString())

    init {
        _gameState.task.add(::changeBoardColor)
    }

    fun toggleLoop() {
        _boardState.toggle.value = !_boardState.toggle.value
    }

    fun changeBoardColor() {
        for (index in 0 until _boardState.blocks.size) {
            _boardState.blocks.set(index, BlockState(color = null))
        }

//        val randomIndex = _boardState.blocks.indices.random()
//        _boardState.blocks.set(
//            randomIndex,
//            BlockState(
//                color = tetrominoeColors[(tetrominoeColors.indices).random()]
//            )
//        )
        resetTetrominoeBoard()
        setTetrominoeToBoard()
        tetrominoeType.value = _tetrominoeState.blocks.shape.toString()
        Log.i("", "changeBoardColor: ${_tetrominoeState.blocks.shape.toString()}")
    }

    fun resetTetrominoeBoard() {
        _tetrominoeState.blocks = tetrominoeShapes[(tetrominoeShapes.indices).random()]

        val randomColor = tetrominoeColors[(tetrominoeColors.indices).random()]

        for (index in 0 until _tetrominoeState.blocks.size) {
            _tetrominoeState.blocks[index].color = randomColor
        }
    }

    fun setTetrominoeToBoard() {
        _tetrominoeState.blocks.forEach {block ->
            _boardState.blocks.set(getBoardPosition(block.position), block)
        }
    }

    fun getBoardPosition(position: Point, boardXsize: Int = 10) : Int {
        var result = 0
        result = position.x + (position.y * boardXsize)
        return result
    }
}