package com.example.paydaybanktesttask.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val UI_DATE_FORMAT = "yyyy-MM-dd"

/**
 * @return target date witout time
 */
fun Date.withoutTime(): Date {
    val thisDate = this
    return Calendar.getInstance()
        .apply { time = thisDate }
        .apply { set(Calendar.HOUR_OF_DAY, 0) }
        .apply { set(Calendar.MINUTE, 0) }
        .apply { set(Calendar.SECOND, 0) }
        .apply { set(Calendar.MILLISECOND, 0) }
        .time
}

/**
 * Parses date strings like "1/14/2019"
 */
fun dateWithoutTimeFromShortString(str: String): Date? {
    return try {
        SimpleDateFormat("M/d/yyyy", Locale.getDefault()).parse(str)?.withoutTime()
    } catch (e: ParseException) {
        null
    }
}

/**
 * Parses date strings like "2019-01-15T00:04:05Z"
 */
fun dateWithoutTimeFromLongString(str: String): Date? {
    return try {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(str)?.withoutTime()
    } catch (e: ParseException) {
        null
    }
}

/**
 * Parses date strings like "1/14/2019"
 */
fun String.fromShortString(): Date? {
    return dateWithoutTimeFromShortString(this)
}

/**
 * Parses date strings like "2019-01-15T00:04:05Z"
 */
fun String.fromLongString(): Date? {
    return dateWithoutTimeFromLongString(this)
}

fun Date.toUiString(): String {
    return SimpleDateFormat(UI_DATE_FORMAT, Locale.getDefault()).format(this)
}