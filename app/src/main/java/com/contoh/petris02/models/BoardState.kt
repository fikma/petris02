package com.contoh.petris02.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class BoardState(
    val toggle: MutableState<Boolean> = mutableStateOf(true), // hack for game loop
    val xSize: Int = 10,
    val ySize: Int = 20,
    val blockSize: Int = 30,
    var blocks: SnapshotStateList<BlockState> = mutableStateListOf()
) {
    init {
        for (number in 0 until (xSize * ySize)) {
            blocks.add(BlockState(color = null))
        }
    }
}