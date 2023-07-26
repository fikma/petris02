package com.contoh.petris02.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class TaskWrapper(
    val priority: Int,
    val task: () -> Unit
)

data class GameState(
    var isPaused: MutableState<Boolean> = mutableStateOf(false),
    var speed: Long = 700, // milliseconds
    val task: MutableList<TaskWrapper> = mutableListOf(),
    var stateText: MutableState<String> = mutableStateOf("Resume game"),
) {
    lateinit var closeDrawer : () -> Unit
    lateinit var openDrawer : () -> Unit
}