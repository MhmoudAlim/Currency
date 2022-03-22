package com.mahmoudalim.data.pref


import com.mahmoudalim.data.models.Ratings

/**
 * Created by Mahmoud Alim on 22/03/2022.
 */

interface AppPreferences {

    fun saveRates(ratings: Ratings)
    fun loadURates(): Ratings

    companion object {
        const val KEY_RATINGS = "ratings"
        const val PREF_TAG = "shared_pref"

    }
}