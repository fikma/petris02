package com.contoh.petris02.views.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.contoh.petris02.models.BoardState
import com.contoh.petris02.models.TetrominoeState
import com.contoh.petris02.viewModels.TetrominoeViewModel

data class Wrapper(
    val icon: ImageVector,
    val callback: () -> Unit
)

@Composable
fun ControlsButton(
    tetrominoeViewModel: TetrominoeViewModel = viewModel()
) {
    val navItemDefault = NavigationBarItemDefaults
    val buttonNames = listOf(
        Wrapper(
            Icons.Filled.KeyboardArrowLeft,
            tetrominoeViewModel.moveLeft
        ),
        Wrapper(
            Icons.Filled.KeyboardArrowRight,
            tetrominoeViewModel.moveRight
        ),
        Wrapper(
            Icons.Filled.KeyboardArrowUp,
            tetrominoeViewModel.rotate
        ),
        Wrapper(
            Icons.Filled.KeyboardArrowDown,
            tetrominoeViewModel.pullDown
        )
    )
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        buttonNames.forEach { item ->
            NavigationBarItem(
                selected = false,
                onClick = item.callback,
                icon = {
                       Icon(
                           imageVector = item.icon,
                           contentDescription = "t",
                           modifier = Modifier.size(48.dp)
                       )
                },
                colors = navItemDefault.colors(),
            )
        }
    }
}

@Preview(
    widthDp = 320
)
@Composable
fun PreviewControlsButton() {
    val boardState = BoardState()
    val tetrominoeState = TetrominoeState()

    val tetrominoeViewModel = TetrominoeViewModel(
        boardState, tetrominoeState
    )
    ControlsButton(
        tetrominoeViewModel
    )
}