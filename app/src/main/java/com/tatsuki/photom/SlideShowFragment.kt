package com.tatsuki.photom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_slide_show.*

class SlideShowFragment : Fragment() {

    companion object {
        private val TAG = SlideShowFragment::class.java.simpleName
    }

    private val photoItems: ArrayList<PhotoItem> = arrayListOf(
        PhotoItem(android.R.mipmap.sym_def_app_icon),
        PhotoItem(android.R.drawable.ic_dialog_alert),
        PhotoItem(android.R.drawable.ic_dialog_email),
        PhotoItem(android.R.drawable.ic_dialog_info),
        PhotoItem(android.R.drawable.ic_dialog_map),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_slide_show, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            val adapter = ScreenSlidePagerAdapter(it, photoItems)
            loopingViewPager.adapter = adapter
        }
    }

    override fun onResume() {
        loopingViewPager.resumeAutoScroll()
        super.onResume()
    }

    override fun onPause() {
        loopingViewPager.pauseAutoScroll()
        super.onPause()
    }
}

