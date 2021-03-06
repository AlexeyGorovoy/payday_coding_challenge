package com.example.paydaybanktesttask.util

import android.view.View

fun <T : View> T.visible() {
    this.visibility = View.VISIBLE
}

fun <T : View> T.invisible() {
    this.visibility = View.INVISIBLE
}

fun <T : View> T.gone() {
    this.visibility = View.GONE
}
