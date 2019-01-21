package com.wegrzyn_a.weatherapp.util

import android.text.format.DateUtils
import java.text.SimpleDateFormat

class DateFormatterImpl : DateFormatter {
    val fromFormatter = SimpleDateFormat("yyyy-MM-dd")

    override fun getReadableDate(date_str: String): String {
        val date = fromFormatter.parse(date_str)
        return when {
            DateUtils.isToday(date.time) -> "Dzisiaj"
            DateUtils.isToday(date.time - DateUtils.DAY_IN_MILLIS) -> "Jutro"
            else -> date_str
        }
    }
}