package com.tatsuki.photom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_slide_show.*

class SlideShowFragment : Fragment() {

    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_slide_show, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())

        viewPager = view_pager2
        viewPager.adapter = pagerAdapter
    }

    private inner class ScreenSlidePagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

        private val numPages = 5

        override fun getItemCount(): Int = numPages

        override fun createFragment(position: Int): Fragment = SlidePageFragment()

    }
}