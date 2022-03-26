package com.mahmoudalim.currency.shared

import com.mahmoudalim.core.utils.Const
import com.mahmoudalim.core.utils.CurrencyEvent
import com.mahmoudalim.data.models.Ratings
import com.mahmoudalim.data.utils.RateFromCurrencyParser
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.round

/**
 * Created by Mahmoud Alim on 26/03/2022.
 */
class CurrencyCalculator(private val allRates: StateFlow<Ratings?>) {

   operator fun invoke(
        amount: String,
        fromCurrency: String,
        toCurrency: String
    ): CurrencyEvent {
        val fromAmount = amount.toDoubleOrNull()
            ?: return CurrencyEvent.Failure

        val value = calculateRatesRatio(fromCurrency, toCurrency, fromAmount)
            ?: return CurrencyEvent.Failure

        val convertedCurrency = round(value * Const.ROUND_VALUE) / Const.ROUND_VALUE
        return CurrencyEvent.Success("$convertedCurrency")
    }


    private fun calculateRatesRatio(
        fromCurrency: String,
        toCurrency: String,
        fromAmount: Double?
    ): Double? {
        if (allRates.value == null) return null
        val fromValue = RateFromCurrencyParser(fromCurrency, allRates.value) ?: return null
        val toValue = RateFromCurrencyParser(toCurrency, allRates.value) ?: return null

        val fromCurrencyRatioToBase = 1.0 / fromValue
        val toCurrencyRatioToBase = 1.0 / toValue

        val conversionValue = fromCurrencyRatioToBase / toCurrencyRatioToBase

        return conversionValue * fromAmount!!
    }


}