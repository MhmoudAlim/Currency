package com.mahmoudalim.core.utils

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */

sealed class CurrencyEvent {
    class Success(val resultText: String) : CurrencyEvent()
    object Failure : CurrencyEvent()
    object Idle : CurrencyEvent()
}