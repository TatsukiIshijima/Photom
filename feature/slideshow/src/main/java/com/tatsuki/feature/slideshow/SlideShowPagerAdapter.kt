package com.tatsuki.feature.slideshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.bumptech.glide.Glide
import com.tatsuki.data.entity.PhotoEntity

class SlideShowPagerAdapter(
    photoList: List<PhotoEntity>,
    isInfinite: Boolean = true,
) : LoopingPagerAdapter<PhotoEntity>(photoList, isInfinite) {

    override fun inflateView(viewType: Int, container: ViewGroup, listPosition: Int): View =
        LayoutInflater.from(container.context).inflate(R.layout.item_photo, container, false)

    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        val photoImage = convertView.findViewById<ImageView>(R.id.photoImage)
        val photo = itemList?.get(listPosition)
        photo?.let {
            Glide.with(convertView.context).load(it.url).into(photoImage)
        }
    }

    fun update(newPhotoList: List<PhotoEntity>) {
        itemList = newPhotoList
        notifyDataSetChanged()
    }
}