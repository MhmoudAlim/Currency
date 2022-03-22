package com.mahmoudalim.currency.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mahmoudalim.currency.databinding.FragmentDetailsBinding
import com.mahmoudalim.data.database.HistoryEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val rootView = binding.root
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Row(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .weight(.7f)
                            .fillMaxHeight()
                    ) {
                        HistoricalListView()
                    }
                    Column(
                        modifier = Modifier
                            .weight(.5f)
                            .fillMaxHeight()
                    ) {
                        PopularCurrenciesListView()
                    }
                }
            }
        }
        return rootView
    }

    @Composable
    private fun HistoricalListView() {
        LaunchedEffect(key1 = viewModel.historyList) {
            viewModel.fetchHistoryFromDatabase()
        }
        Column() {
            viewModel.lastThreeDays().forEach { day ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(.33f)
                ) {
                    DayItemView(day)
                }
            }
        }
    }

    @Composable
    private fun DayItemView(day: String) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = day,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                textAlign = Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                Modifier.padding(4.dp), contentPadding = PaddingValues
                    (vertical = 16.dp)
            ) {
                items(viewModel.historyList) { item ->
                    HistoryListItemView(item, day)
                }
            }
        }
    }



    @Composable
    private fun PopularCurrenciesListView() {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}