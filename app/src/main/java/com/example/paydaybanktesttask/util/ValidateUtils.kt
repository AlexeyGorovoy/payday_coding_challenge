package com.example.paydaybanktesttask.util

import java.util.regex.Pattern

private const val MAX_PASSWORD_SIZE = 6

fun isEmailValid(email: String?): Boolean {
    val ePattern =
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
    val p = Pattern.compile(ePattern)
    val m = p.matcher(email)
    return m.matches()
}

fun isPasswordValid(password: String?): Boolean {
    val str: String = password ?: ""
    return str.length > MAX_PASSWORD_SIZE
}