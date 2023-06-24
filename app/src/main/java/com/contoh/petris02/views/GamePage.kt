package com.contoh.petris02.views

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.contoh.petris02.models.BoardState
import com.contoh.petris02.models.GameState
import com.contoh.petris02.models.TetrominoeState
import com.contoh.petris02.viewModels.GamePageViewModel
import com.contoh.petris02.viewModels.TetrisBoardViewModel
import com.contoh.petris02.views.components.TetrisBoard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamePage(
    tetrisBoardViewModel: TetrisBoardViewModel = viewModel(),
    gamePageViewModel: GamePageViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Petris 02")
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
        ) {
            TetrisBoard(
                tetrisBoardViewModel = tetrisBoardViewModel,
                gamePageViewModel = gamePageViewModel
            )
        }
    }
}

@Preview(
    device = "id:pixel_5",
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewGamePage() {
    val boardState = BoardState()
    val gameState = GameState()
    val tetrominoeState = TetrominoeState()

    val boardVm = TetrisBoardViewModel(boardState, gameState, tetrominoeState)
    val gamePageViewModel = GamePageViewModel(gameState)

    Surface {
        GamePage(
            tetrisBoardViewModel = boardVm,
            gamePageViewModel = gamePageViewModel
        )
    }
}