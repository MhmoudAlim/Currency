package com.mahmoudalim.currency.shared

import com.mahmoudalim.core.utils.Const
import com.mahmoudalim.data.models.PopularRates
import com.mahmoudalim.data.models.Ratings
import com.mahmoudalim.data.utils.RateFromCurrencyParser
import kotlin.math.round

/**
 * Created by Mahmoud Alim on 26/03/2022.
 */

class CurrencyPopularList(private val allRates: Ratings) {

    fun populateList(base: String): MutableList<Pair<String, Double>> {
        val list = mutableListOf<Pair<String, Double>>()
        val currencyList = PopularRates.values()
        currencyList.forEach {
            val item = convertCurrency(fromCurrency = base, toCurrency = it.value)
            list.add(Pair(it.value, item))
        }
        return list
    }

    private fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
    ): Double {
        val final = calculateRatesRatioToBase(fromCurrency, toCurrency)
        return round(final * Const.ROUND_VALUE) / Const.ROUND_VALUE
    }


    private fun calculateRatesRatioToBase(
        fromCurrency: String,
        toCurrency: String,
    ): Double {
        val fromCurrencyRatioToBase = 1.0 / rateFromCurrencyParser(fromCurrency)
        val toCurrencyRatioToBase = 1.0 / rateFromCurrencyParser(toCurrency)
        return fromCurrencyRatioToBase / toCurrencyRatioToBase
    }

    private fun rateFromCurrencyParser(currency: String): Double {
        return RateFromCurrencyParser(currency, allRates) ?: return 0.0
    }
}