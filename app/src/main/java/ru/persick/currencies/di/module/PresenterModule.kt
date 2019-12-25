package ru.persick.currencies.di.module

import dagger.Module
import dagger.Provides
import ru.persick.currencies.ui.main.MainPresenter
import ru.persick.currencies.usecase.RatesUsecase
import javax.inject.Singleton

@Module
class PresenterModule {

    @Singleton
    @Provides
    fun provideMainPresenter(ratesUsecase: RatesUsecase) = MainPresenter(ratesUsecase)
}