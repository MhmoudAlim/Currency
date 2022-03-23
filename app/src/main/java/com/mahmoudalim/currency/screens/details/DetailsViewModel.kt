package com.mahmoudalim.currency.screens.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudalim.core.date.AppDate
import com.mahmoudalim.core.utils.DispatcherProvider
import com.mahmoudalim.data.database.HistoryEntity
import com.mahmoudalim.data.models.PopularRates
import com.mahmoudalim.data.pref.AppPreferences
import com.mahmoudalim.data.repo.CurrencyRepository
import com.mahmoudalim.data.utils.RateFromCurrencyParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round


/**
 * Created by Mahmoud Alim on 22/03/2022.
 */

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repo: CurrencyRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    @Inject
    lateinit var appPreferences: AppPreferences

    var historyList by mutableStateOf(listOf<HistoryEntity>())

    fun fetchHistoryFromDatabase() {
        viewModelScope.launch(dispatchers.io) {
            historyList = repo.fetchConversionsHistoryList()
        }
    }

    fun lastThreeDays() = AppDate.pastDaysOf(3)

    fun generateList(base: String): MutableList<Pair<String, Double>> {
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
        return round(final * 100) / 100
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
        return RateFromCurrencyParser(currency, appPreferences.loadURates()) ?: return 0.0
    }

}