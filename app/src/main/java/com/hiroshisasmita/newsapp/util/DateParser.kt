package com.hiroshisasmita.newsapp.util

import java.text.SimpleDateFormat
import java.util.*

object DateParser {
    fun convertDate(dateString: String): String {
        val formatter = SimpleDateFormat(Const.TIMEZONE_FORMAT,Locale.US)
        formatter.timeZone = TimeZone.getTimeZone("GMT");
        val date = formatter.parse(dateString)
        val newFormat = SimpleDateFormat(Const.TIME_FORMAT,Locale.US)
        return newFormat.format(date)
    }
}