package ru.persick.currencies.ui.main

import android.content.Context
import ru.persick.currencies.ui.vo.CurrencyVO

interface MainView {
    fun getMainContext(): Context
    fun refreshRates(currencies: List<CurrencyVO>)
}