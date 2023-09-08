package dev.echirchir.binaria

import android.app.Application
import dev.echirchir.binaria.di.DataModule
import dev.echirchir.binaria.di.DomainModule
import dev.echirchir.binaria.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BinariaApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger()
            androidContext(this@BinariaApp)
            modules(
                DomainModule,
                DataModule,
                ViewModelModule
            )
        }
    }
}