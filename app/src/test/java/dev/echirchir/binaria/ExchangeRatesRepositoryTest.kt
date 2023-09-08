package dev.echirchir.binaria

import dev.echirchir.binaria.data.datasource.remote.model.ExchangeRatesResponseData
import dev.echirchir.binaria.data.datasource.remote.model.Rates
import dev.echirchir.binaria.data.datasource.remote.model.toDomain
import dev.echirchir.binaria.data.repository.ExchangeRatesRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExchangeRatesRepositoryTest {

    private lateinit var exchangeRatesRepositoryImpl: ExchangeRatesRepositoryImpl

    @Before
    fun setUp(){
        exchangeRatesRepositoryImpl = ExchangeRatesRepositoryImpl(FakeExchangeRatesDataSource())
    }

    @After
    fun tearDown(){
        exchangeRatesRepositoryImpl =  ExchangeRatesRepositoryImpl(FakeExchangeRatesDataSource())
    }

    @Test
    fun `test when no network connection, the response is an error`() {
        val fakeExchangeRatesDataSource = FakeExchangeRatesDataSource()
        fakeExchangeRatesDataSource.isNetworkError  = true

        exchangeRatesRepositoryImpl = ExchangeRatesRepositoryImpl(fakeExchangeRatesDataSource)

        runBlocking {
            exchangeRatesRepositoryImpl.fetchLatestExchangeRates()
                .fold(
                    onSuccess = {
                        // we have network error so this wont happen
                    },
                    onFailure = {
                        assertEquals("A network error occurred", it.message,)
                    }
                )
        }
    }

    @Test
    fun `test when network is available, the response returns ExchangeRatesResponseData`() {
        val fakeExchangeRatesDataSource = FakeExchangeRatesDataSource()
        fakeExchangeRatesDataSource.isNetworkError  = false

        exchangeRatesRepositoryImpl = ExchangeRatesRepositoryImpl(fakeExchangeRatesDataSource)

        runBlocking {
            exchangeRatesRepositoryImpl.fetchLatestExchangeRates()
                .fold(
                    onSuccess = {
                        assertEquals(
                            ExchangeRatesResponseData(
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
                            ).toDomain(),
                            it
                        )

                        assertEquals("USD", it.base)
                        assertEquals( "Usage subject to terms: https://openexchangerates.org/terms", it.disclaimer)
                        val delta = 1e-6 // Tolerance
                        assertEquals(146.15, it.rates.kes, delta)
                    },
                    onFailure = {

                    }
                )
        }
    }
}