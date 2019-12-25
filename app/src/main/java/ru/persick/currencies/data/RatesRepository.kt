package ru.persick.currencies.data

import io.reactivex.Observable
import ru.persick.currencies.api.Service
import ru.persick.currencies.api.model.CurrencyResponse
import javax.inject.Inject

class RatesRepository @Inject constructor(private val service: Service) {

    fun getRates(baseCurrency: String): Observable<CurrencyResponse> =
        service.getCurrencyRates(baseCurrency)
}