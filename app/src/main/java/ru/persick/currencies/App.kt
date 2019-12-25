package ru.persick.currencies

import android.app.Application
import ru.persick.currencies.di.AppComponent
import ru.persick.currencies.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initAppComponent()
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent
            .builder()
            .build()
    }
}