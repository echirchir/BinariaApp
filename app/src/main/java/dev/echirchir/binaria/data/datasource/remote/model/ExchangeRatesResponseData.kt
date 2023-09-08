package dev.echirchir.binaria.data.datasource.remote.model

import dev.echirchir.binaria.domain.model.ExchangeRatesResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRatesResponseData(
    @SerialName("disclaimer")
    val disclaimer: String,
    @SerialName("license")
    val license: String,
    @SerialName("timestamp")
    val timestamp: Long,
    @SerialName("base")
    val base: String,
    @SerialName("rates")
    val rates: Rates
)

@Serializable
data class Rates(
    @SerialName("KES")
    val kes: Double,
    @SerialName("NGN")
    val ngn: Double,
    @SerialName("TZS")
    val tzs: Double,
    @SerialName("UGX")
    val ugx: Double
)

fun ExchangeRatesResponseData.toDomain() = ExchangeRatesResponse(
    disclaimer = disclaimer,
    license = license,
    timestamp = timestamp,
    base = base,
    rates = dev.echirchir.binaria.domain.model.Rates(
        rates.kes,
        rates.ngn,
        rates.tzs,
        rates.ugx
    )
)
