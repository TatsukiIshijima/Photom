package com.tatsuki.core.extension


import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.format(pattern: String = "yyyy/MM/dd kk:mm:ss"): String? {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.JAPAN)
    return simpleDateFormat.format(this)
}