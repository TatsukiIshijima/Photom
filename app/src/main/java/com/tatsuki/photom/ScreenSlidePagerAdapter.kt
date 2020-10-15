package com.tatsuki.photom

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenSlidePagerAdapter(fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity) {

    companion object {
        private const val NUM_PAGES = 5
    }

    override fun getItemCount(): Int = NUM_PAGES


    override fun createFragment(position: Int): Fragment = SlidePageFragment()
}