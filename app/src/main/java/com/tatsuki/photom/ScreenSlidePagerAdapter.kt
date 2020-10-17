package com.tatsuki.photom

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import androidx.viewpager2.widget.ViewPager2

class ScreenSlidePagerAdapter(private val viewPager2: ViewPager2,
                              fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    companion object {
        private const val NUM_PAGES = 5
    }

    private val runnable = Runnable {
        // TODO:スライドしたい項目を追加（疑似こーど）
        // slideItems.addAll()
        // notifyDataSetChanged()
    }

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SlidePageFragment(android.R.color.holo_purple)
            1 -> SlidePageFragment(android.R.color.holo_red_light)
            2 -> SlidePageFragment(android.R.color.holo_orange_light)
            3 -> SlidePageFragment(android.R.color.holo_green_light)
            4 -> SlidePageFragment(android.R.color.holo_blue_light)
            else -> SlidePageFragment(R.color.colorPrimary)
        }
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (position == NUM_PAGES - 2) {
            viewPager2.post(runnable)
        }
    }
}