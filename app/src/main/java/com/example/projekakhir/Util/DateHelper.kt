package com.example.projekakhir.Util

import android.icu.util.ULocale.getCountry
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
//    fun DateTimeHourAgo(dateTime: String?): String? {
//        //val prettyTime = PrettyTime(Locale.getDefault())
//        var isTime: String? = null
//        try {
//            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
//            val date = simpleDateFormat.parse(dateTime)
//            isTime = date.toString()
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return isTime
//    }

    fun DateFormat(dateNews: String?): String? {
        val isDate: String?
        val dateFormat = SimpleDateFormat("dd MMMM yyyy")
        isDate = try {
            val date = SimpleDateFormat("yyyy-mm-dd").parse(dateNews)
            dateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            dateNews
        }
        return isDate
    }
}