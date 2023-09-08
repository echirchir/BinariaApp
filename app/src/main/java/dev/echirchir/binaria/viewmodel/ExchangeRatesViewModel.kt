package dev.echirchir.binaria.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.echirchir.binaria.domain.usecase.FetchExchangeRatesUseCase
import dev.echirchir.binaria.viewmodel.state.ExchangeRatesState
import dev.echirchir.binaria.viewmodel.utils.getExchangeRateByCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExchangeRatesViewModel(
    private val fetchExchangeRatesUseCase: FetchExchangeRatesUseCase
): ViewModel() {

    var exchangeRatesState by mutableStateOf(ExchangeRatesState())

    fun onAction(action: Action) {
        when(action) {
            is Action.OnLoad -> {
                fetchExchangeRates()
            }
            is Action.OnResetState -> {
                exchangeRatesState = ExchangeRatesState()
            }
            is Action.OnAmountChanged -> {
                val exchangeRate = getExchangeRateByCountry(exchangeRatesState.country, exchangeRatesState.toMap())
            }
            is Action.OnCountrySelected -> {
                exchangeRatesState = exchangeRatesState.copy(country = action.country)
            }
            is Action.OnFirstNameChanged -> {
                exchangeRatesState = exchangeRatesState.copy(firstName = action.firstName)
            }
            is Action.OnLastNameChanged -> {
                exchangeRatesState = exchangeRatesState.copy(lastName = action.lastName)
            }
            is Action.OnPhoneNumberChanged -> {
                exchangeRatesState = exchangeRatesState.copy(phone = action.phoneNumber)
            }
            is Action.OnPhonePrefixChanged -> {
                exchangeRatesState = exchangeRatesState.copy(prefix = action.phonePrefix)
            }
            is Action.OnSend -> {
                exchangeRatesState = exchangeRatesState.copy(navigateToSuccessScreen = true)
            }
        }
    }

    private fun fetchExchangeRates() {
        exchangeRatesState = exchangeRatesState.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            fetchExchangeRatesUseCase.execute()
                .fold(
                    onSuccess = {
                        val rates = it.rates
                        exchangeRatesState = exchangeRatesState.copy(
                            kes = rates.kes,
                            tzs = rates.tzs,
                            ngn = rates.ngn,
                            ugx = rates.ugx,
                            isLoading = false
                        )
                    },
                    onFailure = {
                        exchangeRatesState =
                            exchangeRatesState.copy(
                                isLoading = false,
                                error = it.message
                            )
                    }
                )
        }
    }

    sealed interface Action {
        object OnLoad: Action
        data class OnFirstNameChanged(val firstName: String): Action
        data class OnLastNameChanged(val lastName: String): Action
        data class OnPhonePrefixChanged(val phonePrefix: String): Action
        data class OnPhoneNumberChanged(val phoneNumber: String): Action
        data class OnAmountChanged(val amount: String): Action
        data class OnCountrySelected(val country: String): Action
        object OnSend: Action
        object OnResetState: Action
    }
}
