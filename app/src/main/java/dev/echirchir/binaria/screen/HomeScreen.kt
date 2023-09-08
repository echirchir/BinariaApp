package dev.echirchir.binaria.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.echirchir.binaria.common.BinariaButton
import dev.echirchir.binaria.common.Route

@Composable
fun HomeScreen(
    navController: NavController
) {
    BinariaButton(
        modifier = Modifier.fillMaxWidth().height(56.dp),
        label = "Send",
        enabled = true //state.amount.toString().isNotEmpty() && state.isSendButtonActive
    ) {
        navController.navigate(Route.Home.SendMoneyScreen)
    }
}