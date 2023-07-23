package com.contoh.petris02.viewModels

import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.lifecycle.ViewModel
import com.contoh.petris02.models.BoardState
import com.contoh.petris02.models.GameState
import com.contoh.petris02.models.TetrominoeState
import com.contoh.petris02.services.resetBoard
import com.contoh.petris02.services.resetTetrominoe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamePageViewModel @Inject constructor(
    private val _gameState : GameState,
    private val _boardState: BoardState,
    private val _tetrominoeState: TetrominoeState
) : ViewModel() {
    lateinit var modalSheetScope: CoroutineScope
    var drawerState = DrawerState(initialValue = DrawerValue.Closed, confirmStateChange = {
        if (it == DrawerValue.Open) {
            _gameState.isPaused.value = true
            Log.i(GamePageViewModel::class.simpleName, "isPaused: ${_gameState.isPaused.value}")
            true
        }
        else if (it == DrawerValue.Closed) {
            _gameState.isPaused.value = false
            Log.i(GamePageViewModel::class.simpleName, "isPaused: ${_gameState.isPaused.value}")
            true
        } else {
            false
        }
    })

    val gameState = _gameState

    init {
        _gameState.closeDrawer = ::closeModalSheet
        _gameState.openDrawer = ::openModalSheet
        _gameState.onNewGameButton = ::onNewGameButton
    }

    private fun closeModalSheet() {
        modalSheetScope.launch {
            drawerState.close()
            togglePausedState(false)
        }
    }

    private fun openModalSheet() {
        modalSheetScope.launch {
            togglePausedState(true)
            drawerState.open()
        }
    }

    private fun onNewGameButton() {
        resetBoard(_boardState.blocks)
        _tetrominoeState.blocks = resetTetrominoe()
        closeModalSheet()
        togglePausedState(false)
    }

    fun runUpdate() {
        _gameState.task.sortBy { it.priority }
        _gameState.task.forEach { wrapper ->
            wrapper.task()
        }
    }

    private fun togglePausedState(isPaused: Boolean) {
        _gameState.isPaused.value = isPaused
    }
}