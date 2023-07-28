package com.contoh.petris02.views

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.contoh.petris02.models.BoardState
import com.contoh.petris02.models.GameState
import com.contoh.petris02.models.TetrominoeState
import com.contoh.petris02.ui.theme.Petris02Theme
import com.contoh.petris02.viewModels.GamePageViewModel
import com.contoh.petris02.viewModels.TetrisBoardViewModel
import com.contoh.petris02.viewModels.TetrominoeViewModel
import com.contoh.petris02.views.components.ControlsButton
import com.contoh.petris02.views.components.BoardGrid
import com.contoh.petris02.views.components.TetrisBoard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamePage(
    tetrisBoardViewModel: TetrisBoardViewModel = viewModel(),
    gamePageViewModel: GamePageViewModel = viewModel(),
    tetrominoeViewModel: TetrominoeViewModel = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    gamePageViewModel.modalSheetScope = rememberCoroutineScope()

    //Todo: move this logic to somewhere not compose
    val uriHandler = LocalUriHandler.current
    val view = LocalView.current
    val window = (view.context as Activity).window
    window.navigationBarColor = MaterialTheme.colorScheme.surface.toArgb()
    window.statusBarColor = MaterialTheme.colorScheme.surface.toArgb()
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isSystemInDarkTheme()
    WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !isSystemInDarkTheme()
    ModalNavigationDrawer(
        drawerState = gamePageViewModel.drawerState,
        drawerContent = {
            ModalDrawerSheet() {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    NavigationDrawerItem(
                        icon = { Icon(imageVector = Icons.Default.Info, contentDescription = "")},
                        label = { Text(text = "https://github.com/fikma/petris02")},
                        selected = false,
                        onClick = { uriHandler.openUri("https://github.com/fikma/petris02") },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    NavigationDrawerItem(
                        icon = { Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "")},
                        label = { Text(text = gamePageViewModel.gameState.stateText.value)},
                        selected = false,
                        onClick = gamePageViewModel.onResumeGameButton,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    NavigationDrawerItem(
                        icon = { Icon(imageVector = Icons.Default.Refresh, contentDescription = "")},
                        label = { Text(text = "New Game")},
                        selected = false,
                        onClick = gamePageViewModel.onNewGameButton,
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        },
        content = {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "Petris 02")
                        },
                        colors = TopAppBarDefaults.topAppBarColors(),
                        actions = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Next")
                                Spacer(modifier = Modifier.width(10.dp))
                                Box(modifier = Modifier.size(40.dp, 40.dp)) {
                                    BoardGrid(
                                        boardList = tetrominoeViewModel._tetrominoeState.nextTetrominoeBoard,
                                        smallBlockSize = true,
                                        xBlockCount = 4,
                                        modifier = Modifier
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }
                    )
                },
                bottomBar = {
                    ControlsButton(
                        tetrominoeViewModel = tetrominoeViewModel,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        Modifier.height(70.dp)
                    )
                },
                floatingActionButton = {
                    IconButton(onClick = gamePageViewModel.gameState.openDrawer) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play"
                        )
                    }
                }
            ) { contentPadding ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(contentPadding)
                        .fillMaxHeight()
                ) {
                    TetrisBoard(
                        tetrisBoardViewModel = tetrisBoardViewModel,
                        gamePageViewModel = gamePageViewModel
                    )
                }
            }
        }
    )
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
    val gamePageViewModel = GamePageViewModel(gameState, boardState, tetrominoeState)
    val tetrominoeViewModel = TetrominoeViewModel(_boardState = boardState, tetrominoeState)

    Petris02Theme {
        Surface {
            GamePage(
                tetrisBoardViewModel = boardVm,
                gamePageViewModel = gamePageViewModel,
                tetrominoeViewModel = tetrominoeViewModel
            )
        }
    }
}