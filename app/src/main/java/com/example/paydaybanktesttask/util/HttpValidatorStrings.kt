package com.example.paydaybanktesttask.util

import androidx.annotation.StringRes

data class HttpValidatorStrings(
    @StringRes val code404StringRes: Int? = null,
    @StringRes val code500StringRes: Int? = null
) {
    fun getForCode(code: Int) = when (code) {
        404 -> code404StringRes
        500 -> code500StringRes
        else -> null
    }
}