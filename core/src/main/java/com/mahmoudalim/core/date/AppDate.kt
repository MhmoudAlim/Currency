package com.mahmoudalim.core.date

import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter


/**
 * Created by Mahmoud Alim on 22/03/2022.
 */

object AppDate {
    enum class Format(val value: String) {
        DD_MM_YYYY("dd-MM-yyyy"),
        HH_MM_SS("HH:MM:ss");
    }

    fun format(format: Format = Format.DD_MM_YYYY): String {
        val date = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
        return date.format(DateTimeFormatter.ofPattern(format.value))
    }

    fun format(
        milli: Long,
        format: Format,
    ): String {
        val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneId.systemDefault())
        return date.format(DateTimeFormatter.ofPattern(format.value))
    }

    fun pastDaysOf(days: Int): List<String> {
        var date = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault())
        val daysItems = mutableListOf<String>()
        for (i in 0 until days) {
            daysItems.add(date.format(DateTimeFormatter.ofPattern(Format.DD_MM_YYYY.value)))
            date = date.minusDays(1)
        }
        return daysItems
    }

}