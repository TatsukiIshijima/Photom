package com.tatsuki.feature.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tatsuki.feature.slideshow.databinding.FragmentSlideShowBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SlideShowFragment : Fragment() {

    private var _binding: FragmentSlideShowBinding? = null
    private val binding get() = _binding!!

    private val slideShowViewModel: SlideShowViewModel by viewModels()

    private lateinit var adapter: SlideShowPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SlideShowPagerAdapter(listOf())
        binding.loopingViewPager.adapter = adapter

        bind()

        slideShowViewModel.showSlide()
        slideShowViewModel.showCurrentWeather(35.682146, 139.769653)
    }

    private fun bind() {
        slideShowViewModel.photoListFlow
            .filter { it.count() != 0 }
            .onEach { adapter.update(it) }
            .launchIn(lifecycleScope)

        slideShowViewModel.currentWeatherFlow
            .filterNotNull()
            .onEach {
                binding.temperature.text = it.temp.toString()
                it.icon?.let { url ->
                    Glide.with(this).load(url).into(binding.weatherIcon)
                }
            }
            .launchIn(lifecycleScope)

        slideShowViewModel.loadingFlow
            .onEach {
                if (it) binding.loopingViewPager.pauseAutoScroll()
                else binding.loopingViewPager.resumeAutoScroll()
            }
            .launchIn(lifecycleScope)
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
}