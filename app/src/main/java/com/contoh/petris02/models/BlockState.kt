package com.contoh.petris02.models

import android.graphics.Point
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.contoh.petris02.viewModels.BlockType

data class BlockState(
    val position: Point = Point(0, 0),
    val size: Dp = 30.dp,
    var color: Color? = null,
    val type: BlockType = BlockType.EMPTY
)