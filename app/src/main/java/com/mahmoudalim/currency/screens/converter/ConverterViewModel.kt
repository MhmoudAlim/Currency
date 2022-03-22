package com.mahmoudalim.currency.screens.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudalim.core.utils.AppResponse
import com.mahmoudalim.core.utils.Const.API_KEY
import com.mahmoudalim.core.utils.CurrencyEvent
import com.mahmoudalim.core.utils.DispatcherProvider
import com.mahmoudalim.data.database.HistoryEntity
import com.mahmoudalim.data.models.Ratings
import com.mahmoudalim.data.models.SpinnerItem
import com.mahmoudalim.data.pref.AppPreferences
import com.mahmoudalim.data.repo.CurrencyRepository
import com.mahmoudalim.data.utils.CurrencyItemMapper
import com.mahmoudalim.data.utils.RateFromCurrencyParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

import javax.inject.Inject
import kotlin.math.round


/**
 * Created by Mahmoud Alim on 19/03/2022.
 */

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val repo: CurrencyRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    @Inject
    lateinit var appPreferences: AppPreferences

    init {
        fetchRates()
    }

    var selectedFromCurrency = MutableStateFlow(SpinnerItem())
    var selectedToCurrency = MutableStateFlow(SpinnerItem())

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Idle)
    val conversion: StateFlow<CurrencyEvent> = _conversion


    private val _allRates = MutableStateFlow<Ratings?>(null)
    val allRates: StateFlow<Ratings?> = _allRates


    private fun fetchRates() = viewModelScope.launch(dispatchers.io) {
        when (val response = repo.getRates("EUR", API_KEY)) {
            is AppResponse.Success -> {
                val rates = response.data?.let { CurrencyItemMapper().map(it) }
                if (rates == null) {
                    _conversion.value = CurrencyEvent.Failure("Unexpected error")
                    return@launch
                }
                _allRates.value = rates.rates
                appPreferences.saveRates(rates.rates)
            }
            is AppResponse.NetworkError -> CurrencyEvent.Failure("Network error: ${response.message!!}")
            is AppResponse.ServerError -> CurrencyEvent.Failure("Server error: ${response.message!!}")
        }

    }

    fun convertCurrency(
        amount: String,
        fromCurrency: String = selectedFromCurrency.value.value,
        toCurrency: String = selectedToCurrency.value.value,
        saveConversion: Boolean = true
    ) {
        val fromAmount = amount.toDoubleOrNull()
        if (validateInput(fromAmount)) return

        val final = calculateRatesRatioToBase(fromCurrency, toCurrency, fromAmount)

        val convertedCurrency = round(final * 100) / 100
        _conversion.value = CurrencyEvent.Success("$convertedCurrency")

        if (saveConversion)
            insertConversionToDatabase(fromCurrency, toCurrency, amount, convertedCurrency)
    }


    private fun calculateRatesRatioToBase(
        fromCurrency: String,
        toCurrency: String,
        fromAmount: Double?
    ): Double {
        if (_allRates.value == null) {
            _allRates.value = appPreferences.loadURates()
        }
        val fromCurrencyRatioToBase = 1.0 / RateFromCurrencyParser(fromCurrency, _allRates.value!!)!!
        val toCurrencyRatioToBase = 1.0 / RateFromCurrencyParser(toCurrency, _allRates.value!!)!!

        val conversionValue = fromCurrencyRatioToBase / toCurrencyRatioToBase

        return conversionValue * fromAmount!!
    }


    private fun validateInput(fromAmount: Double?): Boolean {
        if (fromAmount == null) {
            _conversion.value = CurrencyEvent.Failure("")
            return true
        }
        return false
    }


    private fun insertConversionToDatabase(
        fromCurrency: String,
        toCurrency: String,
        amount: String,
        convertedCurrency: Double
    ) {
        viewModelScope.launch(dispatchers.io) {
            repo.insertConversionRecord(
                HistoryEntity(
                    fromCurrency = fromCurrency,
                    toCurrency = toCurrency,
                    amount = amount,
                    result = convertedCurrency.toString(),
                    date = "",
                    timeInMillis = Calendar.getInstance().timeInMillis
                )
            )
        }
    }

}