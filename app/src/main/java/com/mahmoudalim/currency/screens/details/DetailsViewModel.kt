package com.mahmoudalim.currency.screens.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmoudalim.core.utils.DispatcherProvider
import com.mahmoudalim.data.database.HistoryEntity
import com.mahmoudalim.data.pref.AppPreferences
import com.mahmoudalim.data.repo.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


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


}