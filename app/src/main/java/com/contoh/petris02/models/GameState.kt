package com.contoh.petris02.models

data class TaskWrapper(
    val priority: Int,
    val task: () -> Unit
)

data class GameState(
    var speed: Long = 100, // milliseconds
    val task: MutableList<TaskWrapper> = mutableListOf()
)