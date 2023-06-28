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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ControlsButton() {
    val navItemDefault = NavigationBarItemDefaults
    val buttonNames = listOf(
        Icons.Filled.KeyboardArrowLeft,
        Icons.Filled.KeyboardArrowUp,
        Icons.Filled.KeyboardArrowRight,
        Icons.Filled.KeyboardArrowDown,
    )
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        buttonNames.forEach { icon ->
            NavigationBarItem(
                selected = false,
                onClick = { /*TODO*/ },
                icon = {
                       Icon(
                           imageVector = icon,
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
        ControlsButton()
}