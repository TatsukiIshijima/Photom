package com.tatsuki.photom.view.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatsuki.photom.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.weather_fragment.*

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()

    private var dailyWeatherAdapter: DailyWeatherAdapter? = null
    private var timelyWeatherAdapter: TimelyWeatherAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            dailyWeatherAdapter = DailyWeatherAdapter(it)
            timelyWeatherAdapter = TimelyWeatherAdapter(it)
        }

        timelyWeather.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = timelyWeatherAdapter
        }
        dailyWeather.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = dailyWeatherAdapter
        }

        bind()

        view.setOnTouchListener { _, _ ->
            viewModel.stopAutoTransitionTimer()
            viewModel.startAutoTransitionTimer()
            true
        }

        viewModel.showWeatherDetail()
    }

    private fun bind() {
        viewModel.autoTransitionLiveData.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })
        viewModel.showCurrentTempLiveData.observe(viewLifecycleOwner, {
            it?.let {
                val tempText = "${it}${context?.resources?.getString(R.string.temperature_unit)}"
                currentTemp.text = tempText
            }
        })
        viewModel.showTimelyWeatherLiveData.observe(viewLifecycleOwner, { timelyWeatherList ->
            timelyWeatherList?.let {
                timelyWeatherAdapter?.submitList(it)
            }
        })
        viewModel.showDailyWeatherLiveData.observe(viewLifecycleOwner, { dailyWeatherList ->
            dailyWeatherList?.let {
                dailyWeatherAdapter?.submitList(it)
            }
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