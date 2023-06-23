package com.contoh.petris02.models

data class GameState(
    var speed: Long = 1000, // milliseconds
    val task: MutableList<() -> Unit> = mutableListOf()
)