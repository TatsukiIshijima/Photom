package com.tatsuki.photom.view.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tatsuki.data.entity.TimelyWeatherEntity
import com.tatsuki.photom.GlideApp
import com.tatsuki.photom.R
import com.tatsuki.photom.databinding.TimelyWeatherItemBinding

class TimelyWeatherAdapter(

) : ListAdapter<TimelyWeatherEntity, TimelyWeatherViewHolder>(TimelyWeatherDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelyWeatherViewHolder {
        val binding =
            TimelyWeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimelyWeatherViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: TimelyWeatherViewHolder, position: Int) {
        val timelyWeather = getItem(position)
        holder.bind(timelyWeather)
    }
}

class TimelyWeatherViewHolder(
    binding: TimelyWeatherItemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    private val time: TextView = binding.time
    private val weatherIcon: ImageView = binding.weatherIcon
    private val temperature: TextView = binding.temperature

    fun bind(timelyWeather: TimelyWeatherEntity) {
        time.text = timelyWeather.time ?: ""
        val tempText =
            "${timelyWeather.temperature}${context.resources.getString(R.string.temperature_unit)}"
        temperature.text = tempText
        if (!timelyWeather.iconUrl.isNullOrEmpty()) {
            val iconUrl = "http://openweathermap.org/img/wn/${timelyWeather.iconUrl}@2x.png"
            GlideApp.with(context).load(iconUrl).into(weatherIcon)
        }
    }
}

object TimelyWeatherDiffCallback : DiffUtil.ItemCallback<TimelyWeatherEntity>() {
    override fun areItemsTheSame(
        oldItem: TimelyWeatherEntity,
        newItem: TimelyWeatherEntity
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: TimelyWeatherEntity,
        newItem: TimelyWeatherEntity
    ): Boolean = oldItem == newItem
}