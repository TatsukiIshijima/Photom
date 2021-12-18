package com.tatsuki.feature.devicecontrol

import android.view.View
import com.tatsuki.data.entity.DeviceEntity
import com.tatsuki.data.entity.DeviceType
import com.tatsuki.feature.devicecontrol.databinding.ItemDeviceBinding
import com.xwray.groupie.viewbinding.BindableItem

class DeviceItem(
    private val deviceEntity: DeviceEntity
) : BindableItem<ItemDeviceBinding>() {

    override fun bind(binding: ItemDeviceBinding, position: Int) {
        binding.apply {
            deviceItemTitle.text = deviceEntity.name
            deviceEntity.type?.let {
                deviceItemImage.setImageResource(
                    when (it) {
                        DeviceType.AirConditioner -> R.drawable.ic_aircon
                        DeviceType.Fan -> R.drawable.ic_fan
                        DeviceType.Light -> R.drawable.ic_light
                    }
                )
            }
        }
    }

    override fun getLayout(): Int =
        R.layout.item_device

    override fun initializeViewBinding(view: View): ItemDeviceBinding =
        ItemDeviceBinding.bind(view)
}