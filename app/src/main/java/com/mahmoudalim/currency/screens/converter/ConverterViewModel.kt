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
import kotlinx.coroutines.flow.*
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

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var appDate: AppDate


    init {
        fetchRates()
    }

    var selectedFromCurrency = MutableStateFlow(SpinnerItem())
    var selectedToCurrency = MutableStateFlow(SpinnerItem())

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Idle)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val _allRates = MutableStateFlow<Ratings?>(null)

    private val currencyCalculator by lazy { CurrencyCalculator(_allRates) }

    private fun fetchRates() = viewModelScope.launch(dispatcher.io) {
        when (val response = useCases.getAllRatesUseCase(BuildConfig.API_KEY)) {
            is AppResponse.Success -> {
                val rates = response.data?.let { CurrencyItemMapper().map(it) }
                if (rates == null) {
                    _uiEvent.emit(UiEvent.ShowToast("Unexpected error"))
                    return@launch
                }
                _allRates.value = rates.rates
                appPreferences.saveRates(rates.rates)
            }
            is AppResponse.NetworkError -> _uiEvent.emit(
                UiEvent.ShowSnackBar("Network error: ${response.message!!}")
            )
            is AppResponse.ServerError -> _uiEvent.emit(
                UiEvent.ShowSnackBar("Server error: ${response.message!!}")
            )
        }

    }

    fun convertCurrency(
        amount: String,
        fromCurrency: String = selectedFromCurrency.value.value,
        toCurrency: String = selectedToCurrency.value.value,
        saveConversion: Boolean = true
    ) {
        viewModelScope.launch(dispatcher.default) {
            if (_allRates.value == null)
                _allRates.value = appPreferences.loadURates()

            when (val convertedValue =
                currencyCalculator.invoke(amount, fromCurrency, toCurrency)) {
                is CurrencyEvent.Idle -> Unit
                is CurrencyEvent.Failure -> {
                    _uiEvent.emit(UiEvent.ShowToast("Conversion error"))
                }
                is CurrencyEvent.Success -> {
                    _conversion.value = convertedValue
                    if (saveConversion)
                        insertConversionToDatabase(
                            fromCurrency,
                            toCurrency,
                            amount,
                            convertedValue.resultText.toDouble()
                        )
                }
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