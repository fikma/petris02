package com.contoh.petris02.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GamePageViewModel @Inject constructor(
    private val _gameState : GameState
) : ViewModel() {
    val gameState = _gameState

    fun runUpdate() {
        _gameState.task.forEach { task ->
            task()
        }
    }
}