package com.tatsuki.setting

import android.view.View
import com.tatsuki.setting.databinding.ItemLocationBinding
import com.xwray.groupie.viewbinding.BindableItem

class LocationItem(
    private val onLocationItemClickedListener: OnLocationItemClickedListener
) : BindableItem<ItemLocationBinding>() {

    interface OnLocationItemClickedListener {
        fun onItemClicked()
    }

    override fun bind(binding: ItemLocationBinding, position: Int) {
        binding.apply {
            locationItemContainer.setOnClickListener {
                onLocationItemClickedListener.onItemClicked()
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_location

    override fun initializeViewBinding(view: View): ItemLocationBinding =
        ItemLocationBinding.bind(view)
}