package com.contoh.petris02.views.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.contoh.petris02.models.BoardState
import com.contoh.petris02.models.TetrominoeState
import com.contoh.petris02.ui.theme.Petris02Theme
import com.contoh.petris02.viewModels.TetrominoeViewModel

data class Wrapper(
    val icon: ImageVector,
    val callback: () -> Unit
)

@Composable
fun ControlsButton(
    tetrominoeViewModel: TetrominoeViewModel = viewModel(),
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
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

    Column {
        BottomAppBar(

            containerColor = containerColor,
            contentColor = contentColor,
            actions = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    buttonNames.forEach {
                        IconButton(
                            onClick = it.callback,
                            modifier = modifier
                        ) {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = "Hello",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    }
                }
            },
            tonalElevation = 1.dp,
            modifier = modifier
        )
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
    Petris02Theme {
        ControlsButton(
            tetrominoeViewModel,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            Modifier
        )
    }
}

@Preview(
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewDarkControlsButton() {
    val boardState = BoardState()
    val tetrominoeState = TetrominoeState()

    val tetrominoeViewModel = TetrominoeViewModel(
        boardState, tetrominoeState
    )
    Petris02Theme {
        ControlsButton(
            tetrominoeViewModel,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            Modifier
        )
    }
}