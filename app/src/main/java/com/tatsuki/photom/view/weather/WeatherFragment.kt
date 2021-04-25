package com.tatsuki.photom.view.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatsuki.photom.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.weather_fragment.*

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timelyWeather.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dailyWeather.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        timelyWeather.adapter = TimelyWeatherAdapter()
        dailyWeather.adapter = DailyWeatherAdapter()

        bind()

        view.setOnTouchListener { _, _ ->
            viewModel.stopAutoTransitionTimer()
            viewModel.startAutoTransitionTimer()
            true
        }
    }

    private fun bind() {
        viewModel.autoTransitionLiveData.observe(viewLifecycleOwner, Observer {
            findNavController().popBackStack()
        })
        viewModel.showTimelyWeatherLiveData.observe(viewLifecycleOwner, Observer {

        })
        viewModel.showDailyWeatherLiveData.observe(viewLifecycleOwner, Observer {

        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.startAutoTransitionTimer()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopAutoTransitionTimer()
    }
}