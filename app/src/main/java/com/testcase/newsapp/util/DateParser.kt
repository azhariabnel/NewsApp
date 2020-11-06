package com.testcase.newsapp.util

import java.text.SimpleDateFormat
import java.util.*

object DateParser {

    private val dayName = arrayOf("Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu")
    private val monthName = arrayOf(
        "Januari",
        "Februari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "November",
        "Desember"
    )

    fun dayNumberToDayname(int: Int?): String? {
        return if (int != null) {
            dayName[int]
        } else {
            ""
        }

    }

    fun dateToIndo(calendar: Calendar): String {
        return dayName[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + calendar.get(Calendar.DATE) + " " + monthName[calendar.get(
            Calendar.MONTH
        )] + " " + calendar.get(Calendar.YEAR)
    }

    fun convertDate(dateString: String): String {
        val formatter = SimpleDateFormat(Const.TIMEZONE_FORMAT,Locale.US)
        formatter.timeZone = TimeZone.getTimeZone("GMT");
        val date = formatter.parse(dateString)
        val newFormat = SimpleDateFormat(Const.TIME_FORMAT,Locale.US)
        return newFormat.format(date)
    }
}