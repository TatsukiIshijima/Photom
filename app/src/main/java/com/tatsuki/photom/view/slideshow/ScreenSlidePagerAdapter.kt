package com.tatsuki.photom.view.slideshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import com.tatsuki.photom.R

class ScreenSlidePagerAdapter(
    context: Context,
    private var refList: List<StorageReference>,
    isInfinite: Boolean = true,
): LoopingPagerAdapter<StorageReference>(context, refList, isInfinite) {

    companion object {
        private val TAG = ScreenSlidePagerAdapter::class.java.simpleName
    }

    override fun inflateView(viewType: Int,
                             container: ViewGroup,
                             listPosition: Int): View =
        LayoutInflater.from(context).inflate(R.layout.photo_item, container, false)

    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        val photoImage = convertView.findViewById<ImageView>(R.id.photoImage)
        itemList?.map {
            Glide.with(convertView).load(it).into(photoImage)
        }
    }

    fun update(newRefList: List<StorageReference>) {
        refList = newRefList
        notifyDataSetChanged()
    }
}