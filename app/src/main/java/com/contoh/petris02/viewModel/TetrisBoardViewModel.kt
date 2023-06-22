package com.contoh.petris02.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.contoh.petris02.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class BlockState(
    val size : Dp = 30.dp,
    val color: Color = Color.Red
)

data class GameState(
    var speed: Long = 1000, // milliseconds
    val task: MutableList<() -> Unit> = mutableListOf()
)

data class BoardState(
    val xSize: Int = 10,
    val ySize: Int = 20,
    val blockSize: Int = 30,
    var blocks: SnapshotStateList<BlockState> = mutableStateListOf()
) {
    init {
        for (number in 0 until (10*20)) {
            blocks.add(BlockState())
        }
    }
}

@HiltViewModel
class TetrisBoardViewModel @Inject constructor(
    private val _boardState: BoardState,
    private val _gameState: GameState,
) : ViewModel() {
    val counter = mutableStateOf(0)
    val boardState = _boardState
    private val _item = mutableStateOf(Item("sdfHello guss!"))
    val item : State<Item> = _item
    private var toggled = true
    private val _blockColor = mutableStateOf(Color.Blue)
    val color : State<Color> = _blockColor

    private var _currentTime = mutableStateOf(60)
    val currentTime: State<Int> = _currentTime

    private var _items = mutableStateListOf("Item 1", "Item 2", "Item 3", "Item 4")
    val items : SnapshotStateList<String> = _items

    private val _blocksState = mutableStateListOf(
        BlockState(),
        BlockState(),
        BlockState(),
        BlockState(),
        BlockState(),
    )

    val blocksState : SnapshotStateList<BlockState> = _blocksState

    init {
        _gameState.task.add(::changeBoardColor)
        Log.d(TetrisBoardViewModel::class.simpleName, "inited")
    }

    fun toggleLoop() {
        counter.value = counter.value + 1
    }

    fun changeBoardColor() {
        for (index in 0 until _boardState.blocks.size) {
            _boardState.blocks.set(index, BlockState())
        }

        val randomIndex = (0 until _boardState.blocks.size).random()
        _boardState.blocks.set(randomIndex, BlockState(color = Color.Blue))
    }

    fun changeBlocksColor() {
        for (index in 0 until _blocksState.size) {
            _blocksState.set(index, BlockState())
        }

        val randomIndex = (0 until _blocksState.size).random()
        _blocksState.set(randomIndex, BlockState(color = Color.Blue))
    }

    fun changeItemText(index: Int) {
        _items[index] = "Updated item ${index + 1}"
    }

    fun deleteItem(index: Int) {
        _items.removeAt(index)
    }

    fun decreaseTime() {
        _currentTime.value = _currentTime.value - 1
    }

    fun changeName() {
        toggled = !toggled
        if (toggled) {
            _item.value = Item("Guss hello!")
        } else {
            _item.value = Item("Hello guss!")
        }
    }

    fun changeColor() {
        changeName()
        if (toggled)
            _blockColor.value = Color.Red
        else {
            _blockColor.value = Color.Blue
        }

        changeBlocksColor()
    }

    fun formatTime(timeInSeconds: Int): String {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }
}