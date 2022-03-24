package com.mahmoudalim.currency.screens.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudalim.core.date.AppDate
import com.mahmoudalim.core.utils.*
import com.mahmoudalim.core.utils.Const.ROUND_VALUE
import com.mahmoudalim.currency.BuildConfig
import com.mahmoudalim.data.database.HistoryEntity
import com.mahmoudalim.data.models.Ratings
import com.mahmoudalim.data.models.SpinnerItem
import com.mahmoudalim.data.pref.AppPreferences
import com.mahmoudalim.data.usecase.CurrencyUseCases
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
    private val useCases: CurrencyUseCases,
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

    private val _uiEvent = MutableStateFlow<UiEvent>(UiEvent.Idle)
    val uiEvent: StateFlow<UiEvent> = _uiEvent

    private val _allRates = MutableStateFlow<Ratings?>(null)


    private fun fetchRates() = viewModelScope.launch(dispatchers.io) {
        when (val response = useCases.getAllRatesUseCase("EUR", BuildConfig.API_KEY)) {
            is AppResponse.Success -> {
                val rates = response.data?.let { CurrencyItemMapper().map(it) }
                if (rates == null) {
                    _uiEvent.value = UiEvent.ShowToast("Unexpected error")
                    return@launch
                }
                _allRates.value = rates.rates
                appPreferences.saveRates(rates.rates)
            }
            is AppResponse.NetworkError -> _uiEvent.value =
                UiEvent.ShowSnackBar("Network error: ${response.message!!}")
            is AppResponse.ServerError -> _uiEvent.value =
                UiEvent.ShowSnackBar("Server error: ${response.message!!}")
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

        val convertedCurrency = round(final * ROUND_VALUE) / ROUND_VALUE
        _conversion.value = CurrencyEvent.Success("$convertedCurrency")

        if (saveConversion)
            insertConversionToDatabase(fromCurrency, toCurrency, amount, convertedCurrency)
    }


    private fun calculateRatesRatioToBase(
        fromCurrency: String,
        toCurrency: String,
        fromAmount: Double?
    ): Double {

        val fromCurrencyRatioToBase = 1.0 / rateFromCurrencyParser(fromCurrency)
        val toCurrencyRatioToBase = 1.0 / rateFromCurrencyParser(toCurrency)

        val conversionValue = fromCurrencyRatioToBase / toCurrencyRatioToBase

        return conversionValue * fromAmount!!
    }

    private fun rateFromCurrencyParser(currency: String): Double {
        if (_allRates.value == null)
            _allRates.value = appPreferences.loadURates()

        val rate = RateFromCurrencyParser(currency, _allRates.value!!)
        if (rate == null) {
            _uiEvent.value = UiEvent.ShowToast("Parsing error")
            return 0.0
        }
        return rate
    }


    private fun validateInput(fromAmount: Double?): Boolean = fromAmount == null


    private fun insertConversionToDatabase(
        fromCurrency: String,
        toCurrency: String,
        amount: String,
        convertedCurrency: Double
    ) {
        viewModelScope.launch(dispatchers.io) {
            useCases.insertConversionRecordUseCase(
                HistoryEntity(
                    fromCurrency = fromCurrency,
                    toCurrency = toCurrency,
                    amount = amount,
                    result = convertedCurrency.toString(),
                    date = AppDate.format(),
                )
            )
        }
    }

}