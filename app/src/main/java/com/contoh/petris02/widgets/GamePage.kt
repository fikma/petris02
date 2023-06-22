package com.contoh.petris02.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.contoh.petris02.viewModel.BoardState
import com.contoh.petris02.viewModel.GamePageViewModel
import com.contoh.petris02.viewModel.GameState
import com.contoh.petris02.viewModel.TetrisBoardViewModel

@Composable
fun GamePage(
    tetrisBoardViewModel: TetrisBoardViewModel = viewModel(),
    gamePageViewModel: GamePageViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Petris 02")
            })
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            TetrisBoard(tetrisBoardViewModel = tetrisBoardViewModel)
        }
    }
}

@Preview(
    widthDp = 480,
    heightDp = 720
)
@Composable
fun PreviewGamePage() {
    val boardState = BoardState()
    val gameState = GameState()

    val boardVm = TetrisBoardViewModel(boardState, gameState)
    val gamePageViewModel = GamePageViewModel(gameState)

    Surface {
        GamePage(
            tetrisBoardViewModel = boardVm,
            gamePageViewModel = gamePageViewModel
        )
    }
}