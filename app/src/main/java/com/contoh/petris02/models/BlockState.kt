package com.contoh.petris02.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.contoh.petris02.viewModels.BlockType

data class BlockState(
    val size : Dp = 30.dp,
    val color: Color?,
    val type: BlockType = BlockType.EMPTY
)