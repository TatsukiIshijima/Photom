package com.tatsuki.data.entity

sealed class DeviceType {
    object AirConditioner : DeviceType()
    object Fan : DeviceType()
    object HubMini : DeviceType()
    object Light : DeviceType()
}
