package ru.persick.currencies.di

import dagger.Component
import ru.persick.currencies.di.module.*
import ru.persick.currencies.ui.main.MainFragment
import javax.inject.Singleton

@Component(
    modules = [
        ApiModule::class,
        PresenterModule::class,
        RepositoryModule::class,
        RetrofitModule::class,
        RatesUsecaseModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(mainFragment: MainFragment)
}