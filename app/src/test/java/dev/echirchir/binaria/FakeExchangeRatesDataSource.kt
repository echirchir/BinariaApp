package dev.echirchir.binaria

import dev.echirchir.binaria.data.datasource.ExchangeRatesDataSource
import dev.echirchir.binaria.data.datasource.remote.model.ExchangeRatesResponseData
import dev.echirchir.binaria.data.datasource.remote.model.Rates

class FakeExchangeRatesDataSource(): ExchangeRatesDataSource {

    var isNetworkError: Boolean = false

    override suspend fun fetchLatestExchangeRates(): ExchangeRatesResponseData {
        if (!isNetworkError){
            return ExchangeRatesResponseData(
                disclaimer = "Usage subject to terms: https://openexchangerates.org/terms",
                license = "https://openexchangerates.org/license",
                timestamp = 1694120400,
                base = "USD",
                rates = Rates(
                    kes = 146.15,
                    tzs = 2504.40,
                    ngn = 754.17,
                    ugx = 3739.57
                )
            )
        } else {
            throw Exception("A network error occurred")
        }
    }
}