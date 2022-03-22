package com.mahmoudalim.core.date

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

/**
 * Created by Mahmoud Alim on 22/03/2022.
 */

object AppDateTimeInitializer {

    fun setup(app: Application) {
        AndroidThreeTen.init(app)
    }
}