package ru.persick.currencies.di.module

import dagger.Module
import dagger.Provides
import ru.persick.currencies.data.RatesRepository
import ru.persick.currencies.usecase.RatesUsecase
import javax.inject.Singleton

@Module
class RatesUsecaseModule {

    @Singleton
    @Provides
    fun provideRatesUsecase(repository: RatesRepository) =
        RatesUsecase(repository)
}