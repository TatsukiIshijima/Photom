package com.tatsuki.data.entity

sealed class DeviceType {
    object Fan : DeviceType()
    object AirConditioner : DeviceType()
    object Light : DeviceType()
}
