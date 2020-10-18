package com.tatsuki.photom

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.photo_item.view.*

class ScreenSlidePagerAdapter(
    private val viewPager2: ViewPager2,
    private val photoItems: MutableList<PhotoItem>
): RecyclerView.Adapter<PhotoItemViewHolder>() {

    companion object {
        private val TAG = ScreenSlidePagerAdapter::class.java.simpleName
    }

    private val runnable = Runnable {
        // スライドしたい項目を追加して更新
        // この方法だと無駄にデータが増える
        photoItems.addAll(photoItems)
        notifyDataSetChanged()

        // currentItemで移動させた場合は再生成することはない？ためか
        // onBindViewHolderが繰り返し呼ばれることはない
        //viewPager2.currentItem = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.photo_item, parent, false)
        return PhotoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        holder.apply {
            imageView.setImageResource(photoItems[position].imageResource)
        }
        if (position == photoItems.count() - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${photoItems.count()}")
        return photoItems.count()
    }
}

class PhotoItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.photoImage
}