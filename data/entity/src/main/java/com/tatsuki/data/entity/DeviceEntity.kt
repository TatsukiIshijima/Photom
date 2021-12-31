package com.tatsuki.data.entity

import java.io.Serializable

data class DeviceEntity(
    val id: String,
    val name: String,
    val type: DeviceType?
) : Serializable
