package ru.persick.currencies.ui.main

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import ru.persick.currencies.Util.getCurrencyImage
import ru.persick.currencies.Util.getCurrencyName
import ru.persick.currencies.api.model.Currency
import ru.persick.currencies.api.model.CurrencyResponse
import ru.persick.currencies.ui.vo.CurrencyVO
import ru.persick.currencies.usecase.RatesUsecase
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainPresenter @Inject constructor(private val ratesUsecase: RatesUsecase) {

    companion object {
        private const val DEFAULT_CODE = "EUR"
        private const val DEFAULT_RATE = 100.0
    }

    private val defaultBaseCurrency = Currency(code = DEFAULT_CODE, rate = DEFAULT_RATE)

    private val baseCurrencySubject = BehaviorSubject.createDefault(defaultBaseCurrency)

    private var disposable: Disposable? = null

    private var view: MainView? = null

    fun updateCurrentBaseCurrency(currency: Currency) {
        baseCurrencySubject.onNext(currency)
    }


    fun onStart(mainView: MainView) {
        this.view = mainView

        val context = mainView.getMainContext()

        disposable = loadRates()
            .subscribe(
                { response ->
                    val currencyVoList = ArrayList<CurrencyVO>()

                    val baseCurrency = baseCurrencySubject.value ?: defaultBaseCurrency

                    currencyVoList.add(
                        CurrencyVO(
                            code = baseCurrency.code ?: DEFAULT_CODE,
                            rate = baseCurrency.rate,
                            name = getCurrencyName(baseCurrency.code ?: DEFAULT_CODE),
                            image = getCurrencyImage(
                                code = baseCurrency.code ?: DEFAULT_CODE,
                                context = context
                            ),
                            isBase = true
                        )
                    )

                    response.rates?.forEach { currency ->
                        currencyVoList.add(
                            CurrencyVO(
                                code = currency.key,
                                rate = currency.value * baseCurrency.rate,
                                name = getCurrencyName(currency.key),
                                image = getCurrencyImage(
                                    code = currency.key,
                                    context = context
                                ),
                                isBase = false
                            )
                        )
                    }

                    view?.refreshRates(currencyVoList)

                },
                {
                    Log.e(javaClass.simpleName, "Error")
                }
            )
    }

    fun onStop() {
        view = null
        disposable?.dispose()
    }

    private fun loadRates(): Observable<CurrencyResponse> =
        Observable.combineLatest<Long, Currency, String>(
            Observable.interval(0, 1, TimeUnit.SECONDS),
            baseCurrencySubject.observeOn(Schedulers.io()),
            BiFunction { _, currency ->
                currency.code ?: DEFAULT_CODE
            })
            .flatMap {
                ratesUsecase.getRates(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}