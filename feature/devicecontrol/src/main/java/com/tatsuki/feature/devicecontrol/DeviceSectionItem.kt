package com.tatsuki.feature.devicecontrol

import android.view.View
import com.tatsuki.feature.devicecontrol.databinding.ItemDeviceSectionBinding
import com.xwray.groupie.viewbinding.BindableItem

class DeviceSectionItem(
    private val title: String
) : BindableItem<ItemDeviceSectionBinding>() {

    override fun bind(binding: ItemDeviceSectionBinding, position: Int) {
        binding.deviceItemHeaderTitle.text = title
    }

    override fun getLayout(): Int =
        R.layout.item_device_section

    override fun initializeViewBinding(view: View): ItemDeviceSectionBinding =
        ItemDeviceSectionBinding.bind(view)

    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return DeviceControlFragment.SPAN_COUNT
    }
}