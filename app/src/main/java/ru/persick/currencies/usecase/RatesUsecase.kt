package ru.persick.currencies.usecase

import io.reactivex.Observable
import ru.persick.currencies.api.model.CurrencyResponse
import ru.persick.currencies.data.RatesRepository
import javax.inject.Inject

class RatesUsecase @Inject constructor(private val repository: RatesRepository) {

    fun getRates(baseCurrency: String): Observable<CurrencyResponse> =
        repository.getRates(baseCurrency)
}