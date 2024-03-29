package com.tatsuki.feature.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tatsuki.data.entity.DailyWeatherEntity
import com.tatsuki.feature.weather.databinding.ItemDailyWeatherBinding

class DailyWeatherAdapter(

) : ListAdapter<DailyWeatherEntity, DailyWeatherViewHolder>(DailyWeatherDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val binding =
            ItemDailyWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyWeatherViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val dailyWeather = getItem(position)
        holder.bind(dailyWeather)
    }
}

class DailyWeatherViewHolder(
    binding: ItemDailyWeatherBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    private val date: TextView = binding.date
    private val day: TextView = binding.day
    private val weatherIcon: ImageView = binding.weatherIcon
    private val maxTemp: TextView = binding.maxTemperature
    private val minTemp: TextView = binding.minTemperature

    fun bind(dailyWeather: DailyWeatherEntity) {
        date.text = dailyWeather.date ?: ""
        day.text = dailyWeather.day ?: ""
        val minTempText =
            "${dailyWeather.minTemperature}${context.resources.getString(R.string.temperature_unit)}"
        val maxTempText =
            "${dailyWeather.maxTemperature}${context.resources.getString(R.string.temperature_unit)}"
        minTemp.text = minTempText
        maxTemp.text = maxTempText
        if (dailyWeather.iconUrl.isNullOrEmpty()) {
            return
        }
        Glide.with(context).load(dailyWeather.iconUrl).into(weatherIcon)
    }
}

object DailyWeatherDiffCallback : DiffUtil.ItemCallback<DailyWeatherEntity>() {
    override fun areItemsTheSame(
        oldItem: DailyWeatherEntity,
        newItem: DailyWeatherEntity
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: DailyWeatherEntity,
        newItem: DailyWeatherEntity
    ): Boolean = oldItem == newItem

}