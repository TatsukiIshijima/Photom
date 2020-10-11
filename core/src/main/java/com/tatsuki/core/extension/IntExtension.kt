package com.tatsuki.core.extension

import java.util.*

fun Int.toDate(): Date = Date(this * 1000L)