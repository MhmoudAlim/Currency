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

        LazyColumn() {
            items(viewModel.historyList) {
                HistoryListItemView(it)
            }
        }

    }

    @Composable
    private fun HistoryListItemView(item: HistoryEntity) {
        Card(modifier = Modifier.padding(8.dp)) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = item.date, style = TextStyle(textDecoration = TextDecoration.Underline))
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${item.amount} ${item.fromCurrency}")
                    Text(text = " : ")
                    Text(text = "${item.result} ${item.toCurrency}")
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