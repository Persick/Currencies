package ru.persick.currencies.api.model

data class CurrencyResponse(
    val base: String? = null,
    val date: String? = null,
    val rates: Map<String, Double>? = null
)