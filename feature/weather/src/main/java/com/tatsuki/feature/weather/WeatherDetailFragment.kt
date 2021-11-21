package com.tatsuki.feature.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind()

        weatherDetailViewModel.fetchWeatherDetail()
    }

    private fun bind() {
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
            .onEach { }
            .launchIn(lifecycleScope)

        weatherDetailViewModel.timelyWeatherFlow
            .onEach { }
            .launchIn(lifecycleScope)

        weatherDetailViewModel.dailyWeatherFlow
            .onEach { }
            .launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}