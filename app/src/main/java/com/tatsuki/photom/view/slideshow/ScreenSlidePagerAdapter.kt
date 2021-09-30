package com.tatsuki.photom.view.slideshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.google.firebase.storage.StorageReference
import com.tatsuki.photom.GlideApp
import com.tatsuki.photom.R

class ScreenSlidePagerAdapter(
    val context: Context,
    refList: List<StorageReference>,
    isInfinite: Boolean = true,
) : LoopingPagerAdapter<StorageReference>(refList, isInfinite) {

    companion object {
        private val TAG = ScreenSlidePagerAdapter::class.java.simpleName
    }

    override fun inflateView(
        viewType: Int,
        container: ViewGroup,
        listPosition: Int
    ): View =
        LayoutInflater.from(context).inflate(R.layout.photo_item, container, false)

    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        val photoImage = convertView.findViewById<ImageView>(R.id.photoImage)
        val reference = itemList?.get(listPosition)
        reference?.let {
            GlideApp.with(convertView.context).load(it).into(photoImage)
        }
    }

    fun update(newRefList: List<StorageReference>) {
        itemList = newRefList
        notifyDataSetChanged()
    }
}