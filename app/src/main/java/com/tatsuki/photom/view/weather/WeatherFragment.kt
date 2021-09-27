package com.tatsuki.photom.view.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatsuki.data.entity.WeatherCondition
import com.tatsuki.photom.R
import com.tatsuki.photom.extension.observeNotNull
import com.tatsuki.photom.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.weather_fragment.*
import timber.log.Timber

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

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

        weatherViewModel.showWeatherDetail()
    }

    private fun bind() {
        weatherViewModel.autoTransitionLiveData.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })
        weatherViewModel.showPlaceLiveData.observe(viewLifecycleOwner, {
            it?.let {
                locationName.text = it
            }
        })
        weatherViewModel.showCurrentWeatherConditionLiveData.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    is WeatherCondition.Atmosphere -> {
                        currentWeather.text = resources.getText(R.string.cloudy)
                        weatherCondition.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_cloudy,
                                null
                            )
                    }
                    is WeatherCondition.Clear -> {
                        currentWeather.text = resources.getText(R.string.sunny)
                        weatherCondition.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_sunny,
                                null
                            )
                    }
                    is WeatherCondition.Cloud -> {
                        currentWeather.text = resources.getText(R.string.cloudy)
                        weatherCondition.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_cloudy,
                                null
                            )
                    }
                    is WeatherCondition.Drizzle -> {
                        currentWeather.text = resources.getText(R.string.rainy)
                        weatherCondition.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_rainy,
                                null
                            )
                    }
                    is WeatherCondition.Rain -> {
                        currentWeather.text = resources.getText(R.string.rainy)
                        weatherCondition.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_rainy,
                                null
                            )
                    }
                    is WeatherCondition.Snow -> {
                        currentWeather.text = resources.getText(R.string.snow)
                        weatherCondition.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_snow,
                                null
                            )
                    }
                    is WeatherCondition.Thunderstorm -> {
                        currentWeather.text = resources.getText(R.string.rainy)
                        weatherCondition.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_rainy,
                                null
                            )
                    }
                }
            }
        })
        weatherViewModel.showCurrentTempLiveData.observe(viewLifecycleOwner, {
            it?.let {
                val tempText = "${it}${context?.resources?.getString(R.string.temperature_unit)}"
                currentTemp.text = tempText
            }
        })
        weatherViewModel.showCurrentWeatherDetailLiveData.observe(
            viewLifecycleOwner,
            { currentWeatherDetail ->
                currentWeatherDetail?.let {
                    currentWeatherDetailAdapter?.submitList(it)
                }
            })
        weatherViewModel.showTimelyWeatherLiveData.observe(
            viewLifecycleOwner,
            { timelyWeatherList ->
                timelyWeatherList?.let {
                    timelyWeatherAdapter?.submitList(it)
                }
            })
        weatherViewModel.showDailyWeatherLiveData.observe(viewLifecycleOwner, { dailyWeatherList ->
            dailyWeatherList?.let {
                dailyWeatherAdapter?.submitList(it)
            }
        })
        mainViewModel.luminosityLiveData
            .observeNotNull(viewLifecycleOwner, {
                Timber.d("Luminosity: $it")
                weatherViewModel.stopAutoTransitionTimer()
                weatherViewModel.startAutoTransitionTimer()
            })
        mainViewModel.faceLiveData
            .observeNotNull(viewLifecycleOwner, {
                Timber.d("faces: $it")
                weatherViewModel.stopAutoTransitionTimer()
                weatherViewModel.startAutoTransitionTimer()
            })
    }

    override fun onResume() {
        super.onResume()
        weatherViewModel.startAutoTransitionTimer()
    }

    override fun onPause() {
        super.onPause()
        weatherViewModel.stopAutoTransitionTimer()
    }
}