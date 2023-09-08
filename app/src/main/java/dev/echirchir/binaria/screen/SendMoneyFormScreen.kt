package dev.echirchir.binaria.screen

import androidx.compose.runtime.Composable
import dev.echirchir.binaria.viewmodel.ExchangeRatesViewModel

@Composable
fun SendMoneyFormScreen(
    viewModel: ExchangeRatesViewModel
) {
    val state = viewModel.exchangeRatesState


}