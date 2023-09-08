package dev.echirchir.binaria.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.echirchir.binaria.ui.theme.gray100
import dev.echirchir.binaria.ui.theme.gray15
import dev.echirchir.binaria.ui.theme.gray25
import dev.echirchir.binaria.ui.theme.green05
import dev.echirchir.binaria.ui.theme.green100
import dev.echirchir.binaria.ui.theme.green15
import dev.echirchir.binaria.ui.theme.green70
import dev.echirchir.binaria.ui.theme.red10
import dev.echirchir.binaria.ui.theme.red100
import dev.echirchir.binaria.ui.theme.red50

@Composable
fun AmountTextField(
    amountInBinary: String,
    amount: String,
    isEnabled: Boolean,
    onAmountChange: (String) -> Unit,
    baseCurrency: String,
    isAmountValid: Boolean,
    onDone: () -> Unit = {}
) {

    val isValid = amount.isNotEmpty() && isAmountValid

    Box(
        modifier = Modifier.border(
            width = 1.dp,
            color = if (amount.isEmpty()) gray15 else if (isValid) green70 else red100,
            shape = RoundedCornerShape(12.dp)
        ),
        contentAlignment = Alignment.Center
    ) {
        Column(Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .testTag("Amount Field"),
                singleLine = true,
                value = amount,
                enabled = isEnabled,
                textStyle = MaterialTheme.typography.h6,
                onValueChange = { onAmountChange(it) },
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Unspecified,
                    focusedBorderColor = Color.Unspecified,
                    disabledBorderColor = Color.Unspecified,
                    errorBorderColor = Color.Unspecified
                ),
                trailingIcon = {
                    Text(
                        text = baseCurrency.uppercase(), style = MaterialTheme.typography.h6.copy(
                            color = gray25
                        )
                    )
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        onDone()
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = if (amount.isEmpty()) gray15 else if (isValid) green15 else red50
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 0.dp,
                            bottomStart = 12.dp,
                            bottomEnd = 12.dp
                        )
                    )
                    .background(if (amount.isEmpty()) gray15 else if (isValid) green05 else red10)
                    .padding(10.dp),
                text = "Amount to be received: $amountInBinary",
                style = MaterialTheme.typography.body1.copy(
                    color = if (amount.isEmpty()) gray100 else if (isValid) green100 else red100
                )
            )
        }
    }
}