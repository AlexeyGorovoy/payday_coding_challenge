package com.example.paydaybanktesttask.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes

private const val ERROR_TAG = "ERROR"

/**
 * Here is the proper point to place any logs and play messages about the error
 */
fun catchThrowable(e: Throwable, context: Context? = null, toastMessage: String? = null) {
    Log.d(ERROR_TAG, "Caught: $e")
    context?.let { Toast.makeText(it, toastMessage ?: e.message, Toast.LENGTH_SHORT).show() }
}

fun catchThrowableWithStringRes(
    e: Throwable,
    context: Context? = null,
    @StringRes toastMessageStringRes: Int? = null
) {
    Log.d(ERROR_TAG, "Caught: $e")
    context?.let {
        catchThrowable(e, it, toastMessageStringRes?.let { strRes -> it.getString(strRes) })
    }
}
