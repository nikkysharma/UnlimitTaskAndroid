package com.example.unlimittaskapp

import android.annotation.SuppressLint
import android.os.Build
import android.text.format.DateUtils
import androidx.annotation.RequiresApi
import io.realm.OrderedRealmCollection
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAccessor
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object DateTimeUtils {

    private val localTimeZoneCalendar = Calendar.getInstance()
    private val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZ")
    val dateTimeStampFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
    private val dateTimeFormatterStandard = SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss")
    val DISPLAY_DATE_TIME_FORMATTER = SimpleDateFormat("dd/MM/yy, HH:mm")
    val EVENT_DATE_TIME_FORMATTER = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    private val DISPLAY_DATE_TIME_FORMATTER_24_HRS = SimpleDateFormat("dd/MM/yyyy HH:mm")
    val CURRENT_DATE = SimpleDateFormat("yyyy-MM-dd")



    /**
     *this function is used for convert to date based on region
     *  @param date is string date
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToDateBasedOnRegion(date: String?): Date? {
        if (date == null) {
            return null
        }
        return try {
            val timeFormatter = java.time.format.DateTimeFormatter.ISO_DATE_TIME
            val accessor: TemporalAccessor = timeFormatter.parse(date)
            Date.from(Instant.from(accessor))
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }

    }
    fun getDisplayDateForEvent(input: Date?): String {
        if (input == null) return ""
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        return simpleDateFormat.format(input)
    }
    /**
     * this function is used for get string time from date
     *  @param date is  date
     * */
    fun getDisplayDateWithTime(date: Date?): String? {
        if (date == null) {
            return null
        }
        return DISPLAY_DATE_TIME_FORMATTER.format(date)
    }
}