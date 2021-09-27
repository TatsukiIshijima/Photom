package com.tatsuki.data.extension

import java.util.*

fun Int.toDate(): Date = Date(this * 1000L)