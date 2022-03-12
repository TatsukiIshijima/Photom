package com.tatsuki.setting

import android.view.View
import com.tatsuki.data.entity.AddressEntity
import com.tatsuki.setting.databinding.ItemAddressBinding
import com.xwray.groupie.viewbinding.BindableItem

class AddressItem(
    private val addressEntity: AddressEntity,
    private val onAddressItemClickedListener: OnAddressItemClickedListener
) : BindableItem<ItemAddressBinding>() {

    interface OnAddressItemClickedListener {
        fun onItemClicked(item: AddressEntity)
    }

    override fun bind(binding: ItemAddressBinding, position: Int) {
        binding.apply {
            addressItemContainer.setOnClickListener {
                onAddressItemClickedListener.onItemClicked(addressEntity)
            }
            addressName.text = addressEntity.name
        }

    }

    override fun getLayout(): Int = R.layout.item_address

    override fun initializeViewBinding(view: View): ItemAddressBinding =
        ItemAddressBinding.bind(view)
}