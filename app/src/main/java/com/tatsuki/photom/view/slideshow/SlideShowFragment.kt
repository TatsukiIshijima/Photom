package com.tatsuki.photom.view.slideshow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.work.WorkInfo
import com.google.android.material.snackbar.Snackbar
import com.tatsuki.data.extension.observeNotNull
import com.tatsuki.photom.GlideApp
import com.tatsuki.photom.R
import com.tatsuki.photom.databinding.FragmentSlideShowBinding
import com.tatsuki.photom.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SlideShowFragment : Fragment() {

    companion object {
        private val TAG = SlideShowFragment::class.java.simpleName
    }

    private val slideShowViewModel: SlideShowViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentSlideShowBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ScreenSlidePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @FlowPreview
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            adapter = ScreenSlidePagerAdapter(it, mutableListOf())
            binding.loopingViewPager.adapter = adapter
        }

        bind()

        slideShowViewModel.fetchSlideImage()

        // カメラを使わずに画面遷移の動作確認をするためのデバッグコード
//        loopingViewPager.setOnTouchListener { _, event ->
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                mainViewModel.saveCurrentPage(loopingViewPager.currentItem)
//                findNavController().navigate(R.id.action_slideshow_to_weather)
//            }
//            true
//        }
    }

    @FlowPreview
    private fun bind() {
        slideShowViewModel.slideImageUrlLiveData.observe(viewLifecycleOwner, {
            if (it?.count() == 0) {
                Snackbar.make(
                    binding.slideShowConstraintLayout,
                    R.string.slide_show_image_is_empty,
                    Snackbar.LENGTH_SHORT
                ).show()
                return@observe
            }
            it?.let {
                Timber.d("slideImageUrlLiveData.observe called.")
                // 前に表示していたページを表示
                binding.loopingViewPager.currentItem =
                    if (mainViewModel.currentPage < it.count()) mainViewModel.currentPage
                    else 0
                adapter.update(it)
            }
        })

        slideShowViewModel.loadingLiveData.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                if (it) binding.loopingViewPager.pauseAutoScroll()
                else binding.loopingViewPager.resumeAutoScroll()
            }
        })

        slideShowViewModel.currentWeatherIconUrlLiveData.observe(viewLifecycleOwner, {
            it?.let {
                GlideApp.with(this).load(it).into(binding.weatherIcon)
            }
        })

        slideShowViewModel.currentTemperatureLiveData.observe(viewLifecycleOwner, {
            it?.let {
                val temperatureText = "$it${resources.getString(R.string.temperature_unit)}"
                binding.temperature.text = temperatureText
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

        mainViewModel.luminosityLiveData
            .observeNotNull(viewLifecycleOwner, { luminosity ->
                Timber.d("Luminosity: $luminosity")
                lifecycleScope.launch {
                    // FIXME: observeNotNull と delay を併用しないと画面遷移しても同じ値がずっと流れてくるため画面遷移のループが発生する
                    delay(500)
                    mainViewModel.saveCurrentPage(binding.loopingViewPager.currentItem)
                    findNavController().navigate(R.id.action_slideshow_to_weather)
                }
            })

        mainViewModel.faceLiveData
            .observeNotNull(viewLifecycleOwner, { faces ->
                Timber.d("faces: ${faces}")
                lifecycleScope.launch {
                    // FIXME: observeNotNull と delay を併用しないと画面遷移しても同じ値がずっと流れてくるため画面遷移のループが発生する
                    delay(500)
                    mainViewModel.saveCurrentPage(binding.loopingViewPager.currentItem)
                    findNavController().navigate(R.id.action_slideshow_to_weather)
                }
            })
    }

    override fun onResume() {
        binding.loopingViewPager.resumeAutoScroll()
        super.onResume()
    }

    override fun onPause() {
        binding.loopingViewPager.pauseAutoScroll()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        slideShowViewModel.cancelUpdateWeatherWork()
        super.onDestroy()
    }
}

