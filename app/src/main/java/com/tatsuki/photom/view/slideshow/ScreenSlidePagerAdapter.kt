package com.tatsuki.photom.view.slideshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.tatsuki.photom.R
import com.tatsuki.photom.model.PhotoItem

class ScreenSlidePagerAdapter(
    context: Context,
    photoItems: ArrayList<PhotoItem>,
    isInfinite: Boolean = true,
): LoopingPagerAdapter<PhotoItem>(context, photoItems, isInfinite) {

    companion object {
        private val TAG = ScreenSlidePagerAdapter::class.java.simpleName
    }

    override fun inflateView(viewType: Int,
                             container: ViewGroup,
                             listPosition: Int): View =
        LayoutInflater.from(context).inflate(R.layout.photo_item, container, false)

    override fun bindView(convertView: View, listPosition: Int, viewType: Int) {
        val photoImage = convertView.findViewById<ImageView>(R.id.photoImage)
        itemList?.let {
            photoImage.setImageResource(it[listPosition].imageResource)
        }
    }
}