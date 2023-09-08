package dev.echirchir.binaria.domain.repository

import dev.echirchir.binaria.domain.common.Response
import dev.echirchir.binaria.domain.model.ExchangeRatesResponse

interface ExchangeRatesRepository {
    suspend fun fetchLatestExchangeRates(): Response<ExchangeRatesResponse>
}