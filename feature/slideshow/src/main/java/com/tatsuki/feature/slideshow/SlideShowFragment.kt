package com.tatsuki.feature.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tatsuki.feature.slideshow.databinding.FragmentSlideShowBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

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
    }

    private fun bind() {
        slideShowViewModel.photoListFlow
            .filter { it.count() != 0 }
            .onEach { adapter.update(it) }
            .launchIn(lifecycleScope)

        slideShowViewModel.loadingFlow
            .onEach { Timber.d("loading:${it}") }
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