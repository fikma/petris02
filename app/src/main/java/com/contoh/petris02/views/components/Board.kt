package com.contoh.petris02.views.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contoh.petris02.models.BlockState
import com.contoh.petris02.models.TetrominoeState
import com.contoh.petris02.services.setTetrominoeToBoard

@Composable
fun NextTetrominoe(nextTetrominoeBoard: SnapshotStateList<BlockState>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4)
    ) {
        items(nextTetrominoeBoard) { block ->
            Box(modifier = androidx.compose.ui.Modifier
                .size(10.dp)
                .background(color = block.color ?: MaterialTheme.colorScheme.primaryContainer))
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun NextTetrominoePreview() {
    val tetrominoeState = TetrominoeState()
    val nextTetrominoe = tetrominoeState.blocksQueue.peek()
    if (nextTetrominoe != null) {
        setTetrominoeToBoard(
            nextTetrominoe,
            tetrominoeState.nextTetrominoeBoard,
            boardXsize = 4
        )
    }

    Surface {
        Box(modifier = Modifier.size(40.dp, 40.dp)) {
            NextTetrominoe(tetrominoeState.nextTetrominoeBoard)
        }
    }
}