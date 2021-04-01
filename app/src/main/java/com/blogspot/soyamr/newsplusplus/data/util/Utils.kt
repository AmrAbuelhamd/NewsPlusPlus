package com.blogspot.soyamr.recipes2.data.util

import java.text.SimpleDateFormat
import java.util.*


private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}
private fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}
fun getCurrentData(): String {
    val date = getCurrentDateTime()
    return date.toString("dd, MMM YYYY HH:mm:ss")
}