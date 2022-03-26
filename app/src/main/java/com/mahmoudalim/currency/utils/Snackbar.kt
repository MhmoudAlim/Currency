package com.mahmoudalim.currency.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import com.google.android.material.snackbar.Snackbar
import com.mahmoudalim.currency.R

/**
 * Created by Mahmoud Alim on 24/03/2022.
 */


fun showSnackBar(
    view: View,
    @SuppressLint("ResourceAsColor") @ColorInt textColor: Int =  R.color.white,
    @SuppressLint("ResourceAsColor") @ColorInt bgColor: Int = R.color.purple_700,
    message: String,
) {
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    val snackbarView = snackbar.view
    snackbarView.setBackgroundColor(bgColor)
    val textView =
        snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.setTextColor(textColor)
    snackbar.show()
}

