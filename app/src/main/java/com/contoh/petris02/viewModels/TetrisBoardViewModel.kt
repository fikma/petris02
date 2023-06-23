package com.contoh.petris02.viewModels

import androidx.lifecycle.ViewModel
import com.contoh.petris02.models.BlockState
import com.contoh.petris02.models.BoardState
import com.contoh.petris02.models.GameState
import com.contoh.petris02.ui.theme.md_theme_light_block_06
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
) : ViewModel() {
    val boardState = _boardState
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

        val randomIndex = (0 until _boardState.blocks.size).random()
        _boardState.blocks.set(randomIndex, BlockState(color = md_theme_light_block_06))
    }
}