package dev.echirchir.binaria.domain.model

data class ExchangeRatesResponse(
    val disclaimer: String,
    val license: String,
    val timestamp: Long,
    val base: String,
    val rates: Rates
)

data class Rates(
    val kes: Double,
    val ngn: Double,
    val tzs: Double,
    val ugx: Double
)