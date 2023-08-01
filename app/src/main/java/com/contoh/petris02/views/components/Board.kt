package com.contoh.petris02.views.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contoh.petris02.models.BlockState
import com.contoh.petris02.models.TetrominoeState
import com.contoh.petris02.services.setTetrominoeToBoard
import com.contoh.petris02.ui.theme.Petris02Theme

@Composable
fun BoardGrid(
    boardList: SnapshotStateList<BlockState>,
    smallBlockSize: Boolean = false,
    xBlockCount: Int,
    modifier: Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(xBlockCount),
        modifier = modifier
    ) {
        items(
            boardList
        ) { block ->
            Box(modifier = Modifier.size(if (smallBlockSize) 10.dp else block.size)) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(if (smallBlockSize) 10.minus(3).dp else block.size.minus(3.dp))
                        .clip(CutCornerShape(if (smallBlockSize) 2.dp else 5.dp))
                        .background(color = block.color ?: Color.Transparent)
                        .border(
                            width = if (smallBlockSize) 1.dp else 2.dp,
                            color = block.color ?: MaterialTheme.colorScheme.secondaryContainer,
                            shape = CutCornerShape(if (smallBlockSize) 2.dp else 5.dp)
                        )
                )
            }
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun NextTetrominoePreviewDark() {
    val tetrominoeState = TetrominoeState()
    val nextTetrominoe = tetrominoeState.blocksQueue.peek()
    if (nextTetrominoe != null) {
        setTetrominoeToBoard(
            nextTetrominoe,
            tetrominoeState.nextTetrominoeBoard,
            boardSize = tetrominoeState.nextTetrominoeBoardSize
        )
    }

    Petris02Theme {
        Box(modifier = Modifier.size(40.dp, 40.dp)) {
            BoardGrid(
                boardList = tetrominoeState.nextTetrominoeBoard,
                smallBlockSize = true,
                xBlockCount = tetrominoeState.nextTetrominoeBoardSize.x,
                modifier = Modifier
                    .size(
                        (10 * 4).dp,
                        (10 * 4).dp
                    )
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CutCornerShape(5.dp)
                    )
                    .padding(5.dp)
            )
        }
    }
}

@Preview
@Composable
fun NextTetrominoePreview() {
    val tetrominoeState = TetrominoeState()
    val nextTetrominoe = tetrominoeState.blocksQueue.peek()
    if (nextTetrominoe != null) {
        setTetrominoeToBoard(
            nextTetrominoe,
            tetrominoeState.nextTetrominoeBoard,
            boardSize = tetrominoeState.nextTetrominoeBoardSize
        )
    }

    Petris02Theme {
        Box(modifier = Modifier.size(40.dp, 40.dp)) {
            BoardGrid(
                boardList = tetrominoeState.nextTetrominoeBoard,
                smallBlockSize = true,
                xBlockCount = tetrominoeState.nextTetrominoeBoardSize.x,
                modifier = Modifier
                    .size(
                        (10 * 4).dp,
                        (10 * 4).dp
                    )
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CutCornerShape(5.dp)
                    )
                    .padding(5.dp)
            )
        }
    }
}