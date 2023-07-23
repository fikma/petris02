package com.contoh.petris02.views.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.contoh.petris02.BuildConfig
import com.contoh.petris02.models.BoardState
import com.contoh.petris02.models.GameState
import com.contoh.petris02.models.TetrominoeState
import com.contoh.petris02.ui.theme.Petris02Theme
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
    LazyVerticalGrid(
            columns = GridCells.Fixed(10),
            modifier = Modifier
                .size(
                    (tetrisBoardViewModel.boardState.blockSize * tetrisBoardViewModel.boardState.xSize + 10).dp,
                    (tetrisBoardViewModel.boardState.blockSize * tetrisBoardViewModel.boardState.ySize + 10).dp
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = CutCornerShape(5.dp)
                )
                .padding(5.dp)
        ) {
            items(
                tetrisBoardViewModel.boardState.blocks
            ) { block ->
                Box (modifier = Modifier.size(block.size)) {
                    Box(modifier = Modifier
                        .align(Alignment.Center)
                        .size(block.size.minus(3.dp))
                        .clip(CutCornerShape(5.dp))
                        .background(color = block.color ?: Color.Transparent)
                        .border(
                            width = 2.dp,
                            color = block.color ?: MaterialTheme.colorScheme.secondaryContainer,
                            shape = CutCornerShape(5.dp)
                        ))
                }
            }
        }
        if (BuildConfig.DEBUG)
            Text(text = tetrisBoardViewModel.tetrominoeType.value)
    }

@Preview
@Composable
fun PreviewTetrisBoard() {
    val boardState = BoardState()
    val gameState = GameState()
    val tetrominoeState = TetrominoeState()

    Petris02Theme {
        Surface {
            TetrisBoard(
                GamePageViewModel(
                    gameState, boardState, tetrominoeState
                ),
                TetrisBoardViewModel(
                    boardState,
                    gameState,
                    tetrominoeState
                )
            )
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewDarkTetrisBoard() {
    val boardState = BoardState()
    val gameState = GameState()
    val tetrominoeState = TetrominoeState()

    Petris02Theme {
        Surface {
            TetrisBoard(
                GamePageViewModel(
                    gameState, boardState, tetrominoeState
                ),
                TetrisBoardViewModel(
                    boardState,
                    gameState,
                    tetrominoeState
                )
            )
        }
    }
}