package com.mahmoudalim.currency.screens.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.isEmpty
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mahmoudalim.core.utils.CurrencyEvent
import com.mahmoudalim.currency.databinding.FragmentConverterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConverterFragment : Fragment() {

    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ConverterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinnersObservers()

        handleOnAmountChangedConversion()

        binding.btnSwap.setOnClickListener {
            handleOnSwapSpinnerValues()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.conversion.collect {
                when (it) {
                    is CurrencyEvent.Success -> {
                        binding.etResult.setText(it.resultText)
                    }
                    is CurrencyEvent.Failure -> {
                        binding.etResult.setText(it.errorText)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setupSpinnersObservers() {
        setupOnFromSpinnerValueChanged()
        setupOnToSpinnerValueChanged()
    }

    private fun handleOnAmountChangedConversion() {
        binding.etAmount.doAfterTextChanged {
            viewModel.convert(amount = binding.etAmount.text.toString())
        }
    }

    private fun handleOnSwapSpinnerValues() {
        if (binding.spFromCurrency.selectedItem == binding.spToCurrency.selectedItem) return
        swapIndices()
    }

    private fun swapIndices() {
        val fromCurrencyTemp = viewModel.selectedFromCurrency.value
        val toCurrencyTemp = viewModel.selectedToCurrency.value
        binding.spToCurrency.setSelection(fromCurrencyTemp.position)
        binding.spFromCurrency.setSelection(toCurrencyTemp.position)

        if (binding.etAmount.text?.isEmpty() == true || binding.etAmount.text?.isEmpty() == true) return
        val amountValueTemp = binding.etAmount.text
        val resultValueTemp = binding.etResult.text
        binding.etResult.text = amountValueTemp
        binding.etAmount.text = resultValueTemp
    }


    private fun setupOnToSpinnerValueChanged() {
        binding.spToCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.selectedToCurrency.value = ConverterViewModel.SpinnerItem(
                    position = position,
                    value = parent?.selectedItem.toString()
                )
            }
        }
    }

    private fun setupOnFromSpinnerValueChanged() {
        binding.spFromCurrency.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.selectedFromCurrency.value = ConverterViewModel.SpinnerItem(
                        position = position,
                        value = parent?.selectedItem.toString()
                    )
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}