package com.tatsuki.photom

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_slide_show.*

class SlideShowFragment : Fragment() {

    private lateinit var viewPager: ViewPager2

    private val sliderHandler = Handler()

    private val sliderRunnable = Runnable {
        viewPager.currentItem += 1
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            sliderHandler.removeCallbacks(sliderRunnable)
            // スライドしている時間
            sliderHandler.postDelayed(sliderRunnable, 3000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_slide_show, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view_pager2
        val pagerAdapter = ScreenSlidePagerAdapter(viewPager, requireActivity())
        viewPager.adapter = pagerAdapter

        // https://qiita.com/yfujiki/items/93ba588313ee0edbd8e9
        // https://stackoverflow.com/questions/58735388/how-to-add-endless-infinite-scroll-to-viewpager2
        // https://www.youtube.com/watch?v=iA9iqygq11Q
        // 上の動画の10分あたり
        // RecyclerViewとViewPager2の併用
        viewPager.registerOnPageChangeCallback(onPageChangeCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }
}

