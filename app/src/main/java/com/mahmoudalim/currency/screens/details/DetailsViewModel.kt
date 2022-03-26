package com.mahmoudalim.currency.screens.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudalim.core.date.AppDate
import com.mahmoudalim.core.utils.DispatcherProvider
import com.mahmoudalim.currency.shared.CurrencyPopularList
import com.mahmoudalim.data.database.HistoryEntity
import com.mahmoudalim.data.pref.AppPreferences
import com.mahmoudalim.data.usecase.CurrencyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Mahmoud Alim on 22/03/2022.
 */

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: CurrencyUseCases,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    @Inject lateinit var appPreferences: AppPreferences
    @Inject lateinit var appDate: AppDate

    var historyList by mutableStateOf(listOf<HistoryEntity>())

    var popularCurrenciesList by mutableStateOf(listOf<Pair<String, Double>>())

    fun fetchHistoryFromDatabase() {
        viewModelScope.launch(dispatcher.io) {
            historyList = useCases.fetchConversionsHistoryUseCase()
        }
    }

    fun lastThreeDays() = appDate.pastDaysOf(3)

    fun populateCurrenciesList(base: String) {
        val allRates = appPreferences.loadURates()
        popularCurrenciesList = CurrencyPopularList(allRates).populateList(base)
    }

}