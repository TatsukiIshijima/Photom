package com.tatsuki.feature.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatsuki.data.entity.WeatherCondition
import com.tatsuki.feature.weather.databinding.FragmentWeatherDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!

    private val weatherDetailViewModel: WeatherDetailViewModel by viewModels()

    private var dailyWeatherAdapter: DailyWeatherAdapter? = null
    private var dailyWeatherInfoAdapter: DailyWeatherInfoAdapter? = null
    private var timelyWeatherAdapter: TimelyWeatherAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dailyWeatherAdapter = DailyWeatherAdapter()
        dailyWeatherInfoAdapter = DailyWeatherInfoAdapter()
        timelyWeatherAdapter = TimelyWeatherAdapter()

        binding.dailyWeather.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = dailyWeatherAdapter
        }
        binding.dailyWeatherInfo.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = dailyWeatherInfoAdapter
        }
        binding.timelyWeather.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = timelyWeatherAdapter
        }

        bind()

        weatherDetailViewModel.fetchWeatherDetail()
    }

    private fun bind() {
        weatherDetailViewModel.loadingFlow
            .onEach { binding.progressbar.isVisible = it }
            .launchIn(lifecycleScope)

        weatherDetailViewModel.placeFlow
            .onEach {
                val locationName = if (it.isNotEmpty()) it else "---"
                binding.locationName.text = locationName
            }
            .launchIn(lifecycleScope)

        weatherDetailViewModel.conditionFlow
            .onEach {
                when (it) {
                    is WeatherCondition.Atmosphere -> {
                        binding.currentWeather.text = resources.getText(R.string.cloudy)
                        binding.weatherDetailLayout.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_cloudy,
                                null
                            )
                    }
                    is WeatherCondition.Clear -> {
                        binding.currentWeather.text = resources.getText(R.string.sunny)
                        binding.weatherDetailLayout.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_sunny,
                                null
                            )
                    }
                    is WeatherCondition.Cloud -> {
                        binding.currentWeather.text = resources.getText(R.string.cloudy)
                        binding.weatherDetailLayout.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_cloudy,
                                null
                            )
                    }
                    is WeatherCondition.Drizzle -> {
                        binding.currentWeather.text = resources.getText(R.string.rainy)
                        binding.weatherDetailLayout.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_rainy,
                                null
                            )
                    }
                    is WeatherCondition.Rain -> {
                        binding.currentWeather.text = resources.getText(R.string.rainy)
                        binding.weatherDetailLayout.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_rainy,
                                null
                            )
                    }
                    is WeatherCondition.Snow -> {
                        binding.currentWeather.text = resources.getText(R.string.snow)
                        binding.weatherDetailLayout.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_snow,
                                null
                            )
                    }
                    is WeatherCondition.Thunderstorm -> {
                        binding.currentWeather.text = resources.getText(R.string.rainy)
                        binding.weatherDetailLayout.background =
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.bg_rainy,
                                null
                            )
                    }
                    else -> {}
                }
            }
            .launchIn(lifecycleScope)

        weatherDetailViewModel.temperatureFlow
            .onEach {
                val value = it ?: "-"
                val tempText =
                    "${value}${requireContext().resources.getString(R.string.temperature_unit)}"
                binding.currentTemp.text = tempText
            }
            .launchIn(lifecycleScope)

        weatherDetailViewModel.weatherInfoListFlow
            .onEach { dailyWeatherInfoAdapter?.submitList(it) }
            .launchIn(lifecycleScope)

        weatherDetailViewModel.timelyWeatherFlow
            .onEach { timelyWeatherAdapter?.submitList(it) }
            .launchIn(lifecycleScope)

        weatherDetailViewModel.dailyWeatherFlow
            .onEach { dailyWeatherAdapter?.submitList(it) }
            .launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}