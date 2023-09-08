package dev.echirchir.binaria.viewmodel.state

data class ExchangeRatesState(
    val isLoading: Boolean = false,
    val kes: Double = 0.0,
    val ngn: Double = 0.0,
    val tzs: Double = 0.0,
    val ugx: Double = 0.0,
    val error: String? = null,
    val country: String = "",
    val countryCode: String = "+254",
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val amount: Double = 0.0,
    val prefix: String = "",
    val phone: String = "",
    val isSendButtonActive: Boolean = false,
    val navigateToSuccessScreen: Boolean = false
) {
    fun toMap(): Map<String, Double> {
        return mapOf(
            "kes" to kes,
            "ngn" to ngn,
            "tzs" to tzs,
            "ugx" to ugx
        )
    }
}
