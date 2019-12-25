package ru.persick.currencies.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.persick.currencies.api.model.CurrencyResponse

interface Service {

    @GET("latest")
    fun getCurrencyRates(@Query("base") baseCurrency: String): Observable<CurrencyResponse>
}