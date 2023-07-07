package com.contoh.petris02.viewModels

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.lifecycle.ViewModel
import com.contoh.petris02.models.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamePageViewModel @Inject constructor(
    private val _gameState : GameState,
) : ViewModel() {
    lateinit var modalSheetScope: CoroutineScope
    lateinit var drawerState: DrawerState

    val gameState = _gameState

    init {
        _gameState.closeDrawer = ::closeModalSheet
        _gameState.openDrawer = ::openModalSheet
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