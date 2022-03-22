package com.mahmoudalim.currency.app

import android.app.Application
import com.mahmoudalim.core.date.AppDateTimeInitializer
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Mahmoud Alim on 19/03/2022.
 */

@HiltAndroidApp
class CurrencyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDateTimeInitializer.setup(this)
    }
}