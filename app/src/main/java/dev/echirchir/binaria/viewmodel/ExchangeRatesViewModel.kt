package dev.echirchir.binaria.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.echirchir.binaria.domain.usecase.FetchExchangeRatesUseCase
import dev.echirchir.binaria.viewmodel.state.ExchangeRatesState
import dev.echirchir.binaria.viewmodel.utils.binaryToInt
import dev.echirchir.binaria.viewmodel.utils.getCurrencyByCountry
import dev.echirchir.binaria.viewmodel.utils.getExchangeRateByCountry
import dev.echirchir.binaria.viewmodel.utils.getPhoneLengthByCountry
import dev.echirchir.binaria.viewmodel.utils.getPhonePrefixByCountry
import dev.echirchir.binaria.viewmodel.utils.isBinary
import dev.echirchir.binaria.viewmodel.utils.toBinaryString
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
                val totalAmount = exchangeRate!!.toInt() * action.amount.binaryToInt()
                exchangeRatesState = exchangeRatesState.copy(amount = action.amount, amountInBinary = totalAmount.toBinaryString())
            }
            is Action.OnCountrySelected -> {
                val prefix = getPhonePrefixByCountry(action.country) ?: "+254"
                val currency = getCurrencyByCountry(action.country) ?: "KES"
                exchangeRatesState = exchangeRatesState.copy(country = action.country, currency = currency, countryPrompt = action.prompt, prefix = prefix)
            }
            is Action.OnFirstNameChanged -> {
                exchangeRatesState = if(action.firstName.isNotEmpty()) {
                    exchangeRatesState.copy(firstName = action.firstName)
                } else {
                    exchangeRatesState.copy(firstNameError = "")
                }
            }
            is Action.OnLastNameChanged -> {
                exchangeRatesState = if(action.lastName.isNotEmpty()) {
                    exchangeRatesState.copy(lastName = action.lastName)
                } else {
                    exchangeRatesState.copy(lastNameError = "")
                }
            }
            is Action.OnPhoneNumberChanged -> {
                val allowedPhoneLength = getPhoneLengthByCountry(exchangeRatesState.country)
                val isPhoneValid = exchangeRatesState.maxPhoneLength == action.phoneNumber.length
                exchangeRatesState = exchangeRatesState.copy(phone = action.phoneNumber, maxPhoneLength = allowedPhoneLength!!, phoneNumberIsValid = isPhoneValid)
            }
            is Action.OnPhonePrefixChanged -> {
                exchangeRatesState = exchangeRatesState.copy(prefix = action.phonePrefix)
            }
            is Action.OnSend -> {
                exchangeRatesState = exchangeRatesState.copy(navigateToSuccessScreen = true)
            }
        }

        exchangeRatesState = exchangeRatesState.copy(
            isSendButtonActive =
                    exchangeRatesState.firstNameError == null
                    && exchangeRatesState.lastNameError == null
                    && (exchangeRatesState.country.isNotEmpty() && exchangeRatesState.country != exchangeRatesState.countryPrompt)
                    && exchangeRatesState.phoneNumberIsValid
                    && exchangeRatesState.amount.isNotEmpty()
                    && exchangeRatesState.amount.isBinary()
        )
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
                            base = it.base,
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
        data class OnCountrySelected(val country: String, val prompt: String): Action
        object OnSend: Action
        object OnResetState: Action
    }
}
