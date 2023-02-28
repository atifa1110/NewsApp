package com.example.projekakhir.Util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    fun getCurrentDate(date : String?): String {
        val dateFormat = SimpleDateFormat("dd MMM yyy", Locale.getDefault())
        val datetime = dateFormat.format(date)
        var splitString = datetime.split(" ")
        var day = splitString[0] + " " + splitString[1] + " " + splitString[2]
        Log.i("tanggal",day)
        return day
    }
}