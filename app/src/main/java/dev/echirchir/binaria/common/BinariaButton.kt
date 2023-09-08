package dev.echirchir.binaria.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.echirchir.binaria.ui.theme.disabled
import dev.echirchir.binaria.ui.theme.green100
import dev.echirchir.binaria.ui.theme.green70
import dev.echirchir.binaria.ui.theme.onDisabled

@Composable
fun BinariaButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        border = BorderStroke(1.dp, green100),
        onClick = onClick,
        modifier = modifier.testTag(label),
        enabled = enabled,
        shape = MaterialTheme.shapes.large.copy(all = CornerSize(16.dp)),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            backgroundColor = green70,
            disabledBackgroundColor = androidx.compose.material.MaterialTheme.colors.disabled,
            disabledContentColor = androidx.compose.material.MaterialTheme.colors.onDisabled
        )
    ){
        Text(
            text = label,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}