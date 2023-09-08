package dev.echirchir.binaria.domain.usecase

import dev.echirchir.binaria.domain.InternetService
import dev.echirchir.binaria.domain.common.Response
import dev.echirchir.binaria.domain.common.exception.InternetException
import dev.echirchir.binaria.domain.common.usecase.UseCaseNoInput
import dev.echirchir.binaria.domain.model.ExchangeRatesResponse
import dev.echirchir.binaria.domain.repository.ExchangeRatesRepository

class FetchExchangeRatesUseCase(
    private val internetService: InternetService,
    private val exchangeRatesRepository: ExchangeRatesRepository
): UseCaseNoInput<ExchangeRatesResponse> {
    override suspend fun execute(): Response<ExchangeRatesResponse> {
        if (!internetService.isConnected()) {
            return Response.failure(InternetException)
        }
        return exchangeRatesRepository.fetchLatestExchangeRates()
    }
}