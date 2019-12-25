package ru.persick.currencies.ui.vo

import androidx.annotation.DrawableRes

data class CurrencyVO(
    val code: String,
    val rate: Double,
    val name: String,
    @DrawableRes val image: Int,
    val isBase: Boolean
)