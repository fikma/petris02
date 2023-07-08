package com.contoh.petris02.views.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.contoh.petris02.BuildConfig
import com.contoh.petris02.models.BoardState
import com.contoh.petris02.models.GameState
import com.contoh.petris02.models.TetrominoeState
import com.contoh.petris02.viewModels.GamePageViewModel
import com.contoh.petris02.viewModels.TetrisBoardViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TetrisBoard(
    gamePageViewModel: GamePageViewModel = viewModel(),
    tetrisBoardViewModel: TetrisBoardViewModel = viewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(tetrisBoardViewModel.boardState.toggle.value) {
        coroutineScope.launch {
            if (!gamePageViewModel.gameState.isPaused.value) {
                delay(gamePageViewModel.gameState.speed)
                gamePageViewModel.runUpdate()
            }
            tetrisBoardViewModel.toggleLoop()
        }
    }

    Box(modifier = Modifier
        .width(
            (tetrisBoardViewModel.boardState.blockSize * tetrisBoardViewModel.boardState.xSize).dp
        )
        .height(
            (tetrisBoardViewModel.boardState.blockSize * tetrisBoardViewModel.boardState.ySize).dp
        )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(10)
        ) {
            items(
                tetrisBoardViewModel.boardState.blocks
            ) { block ->
                Box (modifier = Modifier.size(block.size)) {
                    Box(modifier = Modifier
                        .size(block.size.minus(2.dp))
                        .clip(CutCornerShape(5.dp))
                        .background(color = block.color ?: MaterialTheme.colorScheme.primaryContainer))
                }
            }
        }
        if (BuildConfig.DEBUG)
            Text(text = tetrisBoardViewModel.tetrominoeType.value)
    }
}

@Preview
@Composable
fun PreviewTetrisBoard() {
    val boardState = BoardState()
    val gameState = GameState()
    val tetrominoeState = TetrominoeState()

    Surface {
        TetrisBoard(
            GamePageViewModel(
                gameState
            ),
            TetrisBoardViewModel(
                boardState,
                gameState,
                tetrominoeState
            )
        )
    }
}