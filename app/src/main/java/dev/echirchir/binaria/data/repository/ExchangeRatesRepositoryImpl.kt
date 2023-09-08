package dev.echirchir.binaria.data.repository

import dev.echirchir.binaria.data.datasource.ExchangeRatesDataSource
import dev.echirchir.binaria.data.datasource.remote.model.toDomain
import dev.echirchir.binaria.domain.common.Response
import dev.echirchir.binaria.domain.model.ExchangeRatesResponse
import dev.echirchir.binaria.domain.repository.ExchangeRatesRepository

class ExchangeRatesRepositoryImpl(
    private val dataSource: ExchangeRatesDataSource
): ExchangeRatesRepository {
    override suspend fun fetchLatestExchangeRates(): Response<ExchangeRatesResponse> {
        return try {
            Response.success(dataSource.fetchLatestExchangeRates().toDomain())
        }catch (e: Exception){
            Response.failure(e)
        }
    }
}