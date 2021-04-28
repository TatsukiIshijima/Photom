package com.tatsuki.photom.view.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatsuki.core.entity.WeatherCondition
import com.tatsuki.photom.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.weather_fragment.*

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()

    private var currentWeatherDetailAdapter: CurrentWeatherDetailAdapter? = null
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

        currentWeatherDetailAdapter = CurrentWeatherDetailAdapter()
        dailyWeatherAdapter = DailyWeatherAdapter()
        timelyWeatherAdapter = TimelyWeatherAdapter()

        currentWeatherDetail.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = currentWeatherDetailAdapter
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
        viewModel.showCurrentWeatherConditionLiveData.observe(viewLifecycleOwner, {
            it?.let {
                weatherCondition.background =
                    when (it) {
                        is WeatherCondition.Atmosphere -> ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.bg_cloudy,
                            null
                        )
                        is WeatherCondition.Clear -> ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.bg_sunny,
                            null
                        )
                        is WeatherCondition.Cloud -> ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.bg_cloudy,
                            null
                        )
                        is WeatherCondition.Drizzle -> ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.bg_rainy,
                            null
                        )
                        is WeatherCondition.Rain -> ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.bg_rainy,
                            null
                        )
                        is WeatherCondition.Snow -> ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.bg_snow,
                            null
                        )
                        is WeatherCondition.Thunderstorm -> ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.bg_rainy,
                            null
                        )
                    }
            }
        })
        viewModel.showCurrentTempLiveData.observe(viewLifecycleOwner, {
            it?.let {
                val tempText = "${it}${context?.resources?.getString(R.string.temperature_unit)}"
                currentTemp.text = tempText
            }
        })
        viewModel.showCurrentWeatherDetailLiveData.observe(
            viewLifecycleOwner,
            { currentWeatherDetail ->
                currentWeatherDetail?.let {
                    currentWeatherDetailAdapter?.submitList(it)
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