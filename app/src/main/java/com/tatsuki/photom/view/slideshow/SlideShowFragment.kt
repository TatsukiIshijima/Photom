package com.tatsuki.photom.view.slideshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tatsuki.photom.PhotomApplication
import com.tatsuki.photom.container.PhotomContainer
import kotlinx.android.synthetic.main.fragment_slide_show.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SlideShowFragment : Fragment() {

    companion object {
        private val TAG = SlideShowFragment::class.java.simpleName
    }

    private lateinit var photomContainer: PhotomContainer
    private lateinit var slideShowViewModel: SlideShowViewModel
    private lateinit var adapter: ScreenSlidePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(com.tatsuki.photom.R.layout.fragment_slide_show, container, false)

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photomContainer = (requireActivity().application as PhotomApplication).photomContainer
        photomContainer.buildSlideShowContainer()?.let {
            slideShowViewModel = it.create()
        }

        context?.let {
            adapter = ScreenSlidePagerAdapter(it, mutableListOf())
            loopingViewPager.adapter = adapter
        }

        slideShowViewModel.slideImageUrlLiveData.observe(viewLifecycleOwner, {
            it?.let {
                Log.d(TAG, "slideImageUrlLiveData")
                adapter.update(it)
            }
        })

        slideShowViewModel.loadingLiveData.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                if (it) loopingViewPager.pauseAutoScroll()
                else loopingViewPager.resumeAutoScroll()
            }
        })

        slideShowViewModel.fetchSlideImage()
    }

    override fun onResume() {
        loopingViewPager.resumeAutoScroll()
        super.onResume()
    }

    override fun onPause() {
        loopingViewPager.pauseAutoScroll()
        super.onPause()
    }

    override fun onDestroy() {
        photomContainer.disposeSlideShowContainer()
        super.onDestroy()
    }
}

