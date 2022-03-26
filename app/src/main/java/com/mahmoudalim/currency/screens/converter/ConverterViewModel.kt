package com.mahmoudalim.currency.screens.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudalim.core.date.AppDate
import com.mahmoudalim.core.utils.*
import com.mahmoudalim.currency.BuildConfig
import com.mahmoudalim.currency.shared.CurrencyCalculator
import com.mahmoudalim.data.database.HistoryEntity
import com.mahmoudalim.data.models.Ratings
import com.mahmoudalim.data.models.SpinnerItem
import com.mahmoudalim.data.pref.AppPreferences
import com.mahmoudalim.data.usecase.CurrencyUseCases
import com.mahmoudalim.data.utils.CurrencyItemMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


/**
 * Created by Mahmoud Alim on 19/03/2022.
 */

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val useCases: CurrencyUseCases,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    @Inject lateinit var appPreferences: AppPreferences
    @Inject lateinit var appDate: AppDate


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

    private val currencyCalculator by lazy { CurrencyCalculator(_allRates) }

    private fun fetchRates() = viewModelScope.launch(dispatcher.io) {
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
        if (_allRates.value == null)
            _allRates.value = appPreferences.loadURates()

        val convertedvalue = currencyCalculator(amount, fromCurrency, toCurrency)

        when (convertedvalue) {
            is CurrencyEvent.Idle -> Unit
            is CurrencyEvent.Failure -> _uiEvent.value = UiEvent.ShowToast("Conversion error")
            is CurrencyEvent.Success -> {
                _conversion.value = convertedvalue
                if (saveConversion)
                    insertConversionToDatabase(
                        fromCurrency,
                        toCurrency,
                        amount,
                        convertedvalue.resultText.toDouble()
                    )
            }
        }
    }

    private fun insertConversionToDatabase(
        fromCurrency: String,
        toCurrency: String,
        amount: String,
        convertedCurrency: Double
    ) {
        viewModelScope.launch(dispatcher.io) {
            useCases.insertConversionRecordUseCase(
                HistoryEntity(
                    fromCurrency = fromCurrency,
                    toCurrency = toCurrency,
                    amount = amount,
                    result = convertedCurrency.toString(),
                    date = appDate.format(),
                )
            )
        }
    }

}