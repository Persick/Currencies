package ru.persick.currencies.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.persick.currencies.api.Service
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): Service =
        retrofit.create(Service::class.java)
}