package com.mahmoudalim.core.utils


/**
 * Created by Mahmoud Alim on 24/03/2022.
 */
sealed class UiEvent {
    object Idle : UiEvent()
    data class ShowSnackBar(val message: String) : UiEvent()
    data class ShowToast(val message: String) : UiEvent()
}