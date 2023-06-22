package com.contoh.petris02.widgets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.contoh.petris02.viewModel.BoardState
import com.contoh.petris02.viewModel.GamePageViewModel
import com.contoh.petris02.viewModel.GameState
import com.contoh.petris02.viewModel.TetrisBoardViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TetrisBoard(
    tetrisBoardViewModel: TetrisBoardViewModel = viewModel(),
    gamePageViewModel: GamePageViewModel = viewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(tetrisBoardViewModel.counter.value) {
        coroutineScope.launch {
            delay(gamePageViewModel.gameState.speed)
            gamePageViewModel.runUpdate()
            tetrisBoardViewModel.toggleLoop()
        }
    }

    Box(modifier = Modifier
        .width((tetrisBoardViewModel.boardState.blockSize * 10).dp)
        .height((tetrisBoardViewModel.boardState.blockSize * 20).dp)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(10)
        ) {
            items(tetrisBoardViewModel.boardState.blocks) { block ->
                Box(modifier = Modifier
                    .size(block.size)
                    .background(color = block.color))
            }
        }
    }
}

@Preview
@Composable
fun PreviewTetrisBoard() {
    Surface {
        TetrisBoard(
            TetrisBoardViewModel(
                BoardState(),
                GameState()
            )
        )
    }
}