package dev.echirchir.binaria.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BinariaProgressDialog() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(
            Color.Black.copy(alpha = 0.3f)
        )
    ) {
        CircularProgressIndicator(
            color = Color.Gray,
            strokeWidth = 4.dp
        )
    }
}