package com.tatsuki.data.api.switchbot

interface SwitchBotResponse<T> {
    val statusCode: Int
    val message: String
    val body: T?
}