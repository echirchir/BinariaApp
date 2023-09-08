package dev.echirchir.binaria.data.datasource

import dev.echirchir.binaria.data.datasource.remote.model.ExchangeRatesResponseData

interface ExchangeRatesDataSource {
    suspend fun fetchLatestExchangeRates(): ExchangeRatesResponseData
}