package com.contoh.petris02.viewModels

import androidx.lifecycle.ViewModel
import com.contoh.petris02.models.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GamePageViewModel @Inject constructor(
    private val _gameState : GameState
) : ViewModel() {
    val gameState = _gameState
    val togglePausedStateRef = ::togglePausedState

    fun runUpdate() {
        _gameState.task.sortBy { it.priority }
        _gameState.task.forEach { wrapper ->
            wrapper.task()
        }
    }

    private fun togglePausedState() {
        _gameState.isPaused.value = !_gameState.isPaused.value
    }
}