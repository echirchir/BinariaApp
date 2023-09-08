package dev.echirchir.binaria.di

import dev.echirchir.binaria.data.datasource.ExchangeRatesDataSource
import dev.echirchir.binaria.data.datasource.remote.ExchangeRatesDataSourceImpl
import dev.echirchir.binaria.data.repository.ExchangeRatesRepositoryImpl
import dev.echirchir.binaria.domain.InternetService
import dev.echirchir.binaria.domain.InternetServiceImpl
import dev.echirchir.binaria.domain.repository.ExchangeRatesRepository
import dev.echirchir.binaria.domain.usecase.FetchExchangeRatesUseCase
import dev.echirchir.binaria.network.BinariaHttpClient
import dev.echirchir.binaria.network.BinariaHttpClientImpl
import dev.echirchir.binaria.viewmodel.ExchangeRatesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val DomainModule = module {
    factory { FetchExchangeRatesUseCase(get(), get()) }
}

val DataModule = module {
    factory<BinariaHttpClient> { BinariaHttpClientImpl() }
    single<InternetService> { InternetServiceImpl(androidContext()) }
    single<ExchangeRatesRepository> { ExchangeRatesRepositoryImpl(get()) }
    single<ExchangeRatesDataSource>{ ExchangeRatesDataSourceImpl(get()) }
}

val ViewModelModule = module {
    viewModel { ExchangeRatesViewModel(get()) }
}