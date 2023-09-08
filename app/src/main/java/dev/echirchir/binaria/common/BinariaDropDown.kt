package dev.echirchir.binaria.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BinariaDropDown(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    boldLabel: Boolean = true,
    isValid: Boolean = true,
    readOnly: Boolean = true,
    errorMessage: String = "",
    onClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1,
            fontWeight = if(boldLabel) FontWeight.Bold else FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            OutlinedTextField(
                value = value,
                enabled = false,
                onValueChange = {},
                readOnly = readOnly,
                modifier = Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = TextFieldDefaults.MinHeight),
                trailingIcon = {
                    IconButton(onClick = { onClick() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown",
                            tint = Color.Gray
                        )
                    }
                }
            )
        }

        if (!isValid) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("${label} error"),
                text = errorMessage,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.error
            )
        }
    }
}