package com.mahmoudalim.data.pref

import android.content.SharedPreferences
import com.google.gson.Gson
import com.mahmoudalim.data.models.Ratings

/**
 * Created by Mahmoud Alim on 22/03/2022.
 */

class DefaultAppPreferences(
    private val sharedPref: SharedPreferences
) : AppPreferences {

    override fun saveRates(ratings: Ratings) {
        val rates = Gson().toJson(ratings)
        sharedPref.edit()
            .putString(AppPreferences.KEY_RATINGS, rates)
            .apply()
    }

    override fun loadURates(): Ratings {
        val ratesJsonString = sharedPref.getString(AppPreferences.KEY_RATINGS, "")
        return Gson().fromJson(ratesJsonString, Ratings::class.java)
    }


}