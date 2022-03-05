package com.tatsuki.setting

import android.view.View
import com.tatsuki.data.entity.LocationEntity
import com.tatsuki.setting.databinding.ItemLocationBinding
import com.xwray.groupie.viewbinding.BindableItem

class LocationItem(
    private val locationEntity: LocationEntity,
    private val onLocationItemClickedListener: OnLocationItemClickedListener
) : BindableItem<ItemLocationBinding>() {

    interface OnLocationItemClickedListener {
        fun onItemClicked(item: LocationEntity)
    }

    override fun bind(binding: ItemLocationBinding, position: Int) {
        binding.apply {
            locationItemContainer.setOnClickListener {
                onLocationItemClickedListener.onItemClicked(locationEntity)
            }
            locationName.text = locationEntity.name
        }

    }

    override fun getLayout(): Int = R.layout.item_location

    override fun initializeViewBinding(view: View): ItemLocationBinding =
        ItemLocationBinding.bind(view)
}