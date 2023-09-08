package dev.echirchir.binaria.data.datasource.remote

import dev.echirchir.binaria.data.datasource.ExchangeRatesDataSource
import dev.echirchir.binaria.data.datasource.remote.error.ExchangeRatesErrorResponseData
import dev.echirchir.binaria.data.datasource.remote.model.ExchangeRatesResponseData
import dev.echirchir.binaria.network.BinariaHttpClient
import dev.echirchir.binaria.network.Constants
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess

class ExchangeRatesDataSourceImpl(
    private val httpClient: BinariaHttpClient
): ExchangeRatesDataSource {

    override suspend fun fetchLatestExchangeRates(): ExchangeRatesResponseData {
        val response = httpClient.getHttpClient(appId = Constants.APP_ID, symbols = Constants.SYMBOLS).get("/api/latest.json")
        if (response.status.isSuccess()){
            return response.body()
        }
        val error = response.body<ExchangeRatesErrorResponseData>()
        throw Exception(error.message)
    }
}