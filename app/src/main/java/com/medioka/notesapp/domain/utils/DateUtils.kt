package com.medioka.notesapp.domain.utils

import java.text.SimpleDateFormat
import java.util.Date

object DateUtils {
    private const val DATE_FORMAT = "yyyy-MM-dd"

    fun getCurrentDateInStringFormat(): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT)
        return try {
            val date = Date()
            return dateFormat.format(date)
        } catch (e: Exception) {
            ""
        }
    }
}