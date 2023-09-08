package dev.echirchir.binaria.data.datasource.remote.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ExchangeRatesErrorResponseData(
    @SerialName("error")
    val error: Boolean,
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("description")
    val description: String
)