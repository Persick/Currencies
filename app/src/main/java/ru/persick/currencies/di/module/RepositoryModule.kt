package ru.persick.currencies.di.module

import dagger.Module
import dagger.Provides
import ru.persick.currencies.api.Service
import ru.persick.currencies.data.RatesRepository
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    internal fun providesRatesRepository(service: Service): RatesRepository =
        RatesRepository(service)

}