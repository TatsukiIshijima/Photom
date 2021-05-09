package com.tatsuki.photom.view.slideshow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.work.WorkInfo
import com.tatsuki.photom.GlideApp
import com.tatsuki.photom.R
import com.tatsuki.photom.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_slide_show.*
import timber.log.Timber

@AndroidEntryPoint
class SlideShowFragment : Fragment() {

    companion object {
        private val TAG = SlideShowFragment::class.java.simpleName
    }

    private val slideShowViewModel: SlideShowViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: ScreenSlidePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_slide_show, container, false)

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            adapter = ScreenSlidePagerAdapter(it, mutableListOf())
            loopingViewPager.adapter = adapter
        }

        bind()

        slideShowViewModel.fetchSlideImage()

        loopingViewPager.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                findNavController().navigate(R.id.action_slideshow_to_weather)
            }
            true
        }
    }

    private fun bind() {
        slideShowViewModel.slideImageUrlLiveData.observe(viewLifecycleOwner, {
            it?.let {
                Timber.d("slideImageUrlLiveData.observe called.")
                adapter.update(it)
            }
        })

        slideShowViewModel.loadingLiveData.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                if (it) loopingViewPager.pauseAutoScroll()
                else loopingViewPager.resumeAutoScroll()
            }
        })

        slideShowViewModel.currentWeatherIconUrlLiveData.observe(viewLifecycleOwner, {
            it?.let {
                GlideApp.with(this).load(it).into(weatherIcon)
            }
        })

        slideShowViewModel.currentTemperatureLiveData.observe(viewLifecycleOwner, {
            it?.let {
                val temperatureText = "$it${resources.getString(R.string.temperature_unit)}"
                temperature.text = temperatureText
            }
        })

        slideShowViewModel.executeUpdateWeatherWork().observe(viewLifecycleOwner, {
            it?.let {
                Timber.d("Work(${it.tags.first()}) state ${it.state}")
                // Periodic なので State は Enqueued と Running を繰り返す、終了時は Cancel
                if (it.state == WorkInfo.State.ENQUEUED) {
                    slideShowViewModel.fetchCurrentWeather()
                }
            }
        })

        mainViewModel.luminosityLiveData.observe(viewLifecycleOwner, {
            Timber.d("Luminosity: $it")
        })
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
        slideShowViewModel.cancelUpdateWeatherWork()
        super.onDestroy()
    }
}

