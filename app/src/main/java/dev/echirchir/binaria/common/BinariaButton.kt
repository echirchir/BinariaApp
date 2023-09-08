package dev.echirchir.binaria.common

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.echirchir.binaria.ui.theme.gray00
import dev.echirchir.binaria.ui.theme.green15

@Composable
fun BinariaButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = MaterialTheme.shapes.large.copy(all = CornerSize(16.dp)),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Green,
            contentColor = Color.White,
            disabledBackgroundColor = green15,
            disabledContentColor = gray00
        )
    ) {
        Text(text = label)
    }
}