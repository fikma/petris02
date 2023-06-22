package com.contoh.petris02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.contoh.petris02.ui.theme.Petris02Theme
import com.contoh.petris02.viewModel.BoardState
import com.contoh.petris02.viewModel.GamePageViewModel
import com.contoh.petris02.viewModel.GameState
import com.contoh.petris02.viewModel.TetrisBoardViewModel
import com.contoh.petris02.widgets.GamePage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val tetrisBoardViewModel: TetrisBoardViewModel by viewModels()
    private val gamePageViewModel: GamePageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Petris02Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GamePage(
                        tetrisBoardViewModel = tetrisBoardViewModel,
                        gamePageViewModel = gamePageViewModel
                    )
                }
            }
        }
    }
}

data class Item(
    var name: String
)

data class BlockState(
    var color: Color = Color.Red,
    val size: Dp = 25.dp
)

@Composable
fun Greeting(viewModel: TetrisBoardViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())

    ) {
        Text(text = viewModel.item.value.name)
        Button(onClick = { viewModel.changeName() }) {
            Text(text = "Change name")
        }
        Text(text = viewModel.formatTime(viewModel.currentTime.value))
        Surface(color = viewModel.color.value) {
            Box(Modifier.size(50.dp))
        }
        viewModel.items.forEachIndexed { index, value ->
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = value)
                Button(onClick = {
                     viewModel.changeItemText(index)
                }) {
                    Text(text = "Update")
                }
                Button(onClick = {
                    viewModel.deleteItem(index)
                }) {
                    Text(text = "-")
                }
            }
        }

        // ========================================== //
//        Row {
//            viewModel.blocksState.forEach { item ->
//                Surface(color = item.color) {
//                    Box(modifier = Modifier.size(item.size))
//                }
//            }
//        }
    }

    LaunchedEffect(viewModel.currentTime.value) {
        if (viewModel.currentTime.value > 0) {
            coroutineScope.launch {
                delay(1000)
                viewModel.decreaseTime()
                viewModel.changeColor()
                viewModel.changeBlocksColor()
            }
        }
    }
}

@Preview(
    showBackground = true,
    heightDp = 320
)
@Composable
fun DefaultPreview() {
    Petris02Theme {
        Greeting(TetrisBoardViewModel(
            BoardState(),
            GameState()
        ))
    }
}