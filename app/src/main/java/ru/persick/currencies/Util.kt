package ru.persick.currencies

import android.content.Context
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object Util {

    fun getCurrencyName(code: String): String {
        return try {
            Currency.getInstance(code).displayName
        } catch (ex: IllegalArgumentException) {
            ""
        }
    }

    fun getCurrencyImage(code: String, context: Context) =
        context.resources.getIdentifier(
            "ic_" + code.toLowerCase(Locale.getDefault()),
            "drawable",
            context.packageName
        )

    fun Double.decimalFormat(): String {
        return if (this == 0.0) "" else DecimalFormat("#.##").format(this)
    }

    fun String.decimalFormatToDouble(): Double {
        return if (isNullOrEmpty()) {
            0.0
        } else {
            try {
                val number =
                    DecimalFormat(
                        "#.##",
                        DecimalFormatSymbols.getInstance(Locale.getDefault())
                    ).parse(
                        this
                    )

                number?.toDouble() ?: 0.0
            } catch (ex: Exception) {
                0.0
            }

        }
    }
}