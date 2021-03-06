package com.mahmoudalim.currency.screens.converter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mahmoudalim.core.utils.CurrencyEvent
import com.mahmoudalim.core.utils.UiEvent
import com.mahmoudalim.core.utils.hideKeyboard
import com.mahmoudalim.currency.R
import com.mahmoudalim.currency.databinding.FragmentConverterBinding
import com.mahmoudalim.currency.utils.showSnackBar
import com.mahmoudalim.data.models.SpinnerItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConverterFragment : Fragment() {

    private var _binding: FragmentConverterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ConverterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinnersObservers()

        handleOnAmountChanged()

        collectCurrencyEvents()

        collectUiEvents()

        binding.btnSwap.setOnClickListener {
            it.hideKeyboard()
            handleOnSwapSpinnerValues()
        }

        binding.btnDetails.setOnClickListener {
            val base = viewModel.selectedFromCurrency.value.value
            val bundle = bundleOf("base" to base)
            findNavController().navigate(R.id.action_converterFragment_to_detailsFragment, bundle)
        }

    }

    private fun collectUiEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect {
                when (it) {
                    is UiEvent.Idle -> Unit
                    is UiEvent.ShowSnackBar -> showSnackBar(
                        view = binding.root,
                        message = it.message
                    )
                    is UiEvent.ShowToast -> Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }

    private fun collectCurrencyEvents() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.conversion.collectLatest {
                    when (it) {
                        is CurrencyEvent.Success -> {
                            binding.etResult.setText(it.resultText)
                        }
                        is CurrencyEvent.Failure -> {
                            binding.etResult.setText("0")
                        }
                        is CurrencyEvent.Idle -> Unit
                    }
                }
            }
        }
    }

    private fun setupSpinnersObservers() {
        setupOnFromSpinnerValueChanged()
        setupOnToSpinnerValueChanged()
    }

    private fun handleOnAmountChanged() {
        binding.etAmount.doAfterTextChanged {
            if (it.isNullOrEmpty()) return@doAfterTextChanged
            if (validateDifferentSpinnerValues()) return@doAfterTextChanged
            viewModel.convertCurrency(amount = binding.etAmount.text.toString())
        }
    }

    private fun validateDifferentSpinnerValues(): Boolean {
        if (binding.spFromCurrency.selectedItem == binding.spToCurrency.selectedItem) {
            binding.etResult.text = binding.etAmount.text
            return true
        }
        return false
    }

    private fun handleOnSwapSpinnerValues() {
        if (binding.spFromCurrency.selectedItem == binding.spToCurrency.selectedItem) return
        swapIndices()
    }

    private fun swapIndices() {
        swapSpinnerValues()

        if (binding.etAmount.text?.isEmpty() == true || binding.etAmount.text?.isEmpty() == true) return
        swapConversionValues()
    }

    private fun swapConversionValues() {
        val resultValueTemp = binding.etResult.text
        binding.etAmount.text = resultValueTemp
        viewModel.convertCurrency(
            amount = resultValueTemp.toString(),
            fromCurrency = viewModel.selectedToCurrency.value.value,
            toCurrency = viewModel.selectedFromCurrency.value.value
        )
    }

    private fun swapSpinnerValues() {
        val fromCurrencyTemp = viewModel.selectedFromCurrency.value
        val toCurrencyTemp = viewModel.selectedToCurrency.value
        binding.spToCurrency.setSelection(fromCurrencyTemp.position)
        binding.spFromCurrency.setSelection(toCurrencyTemp.position)
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
                parent?.rootView?.hideKeyboard()
                viewModel.selectedToCurrency.value = SpinnerItem(
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
                    parent?.rootView?.hideKeyboard()
                    viewModel.selectedFromCurrency.value = SpinnerItem(
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