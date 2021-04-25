package com.tatsuki.photom.view.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tatsuki.core.entity.TimelyWeatherEntity
import com.tatsuki.photom.R

class TimelyWeatherAdapter(
    private val context: Context
) : ListAdapter<TimelyWeatherEntity, TimelyWeatherViewHolder>(TimelyWeatherDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelyWeatherViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.timely_weather_item, parent, false)
        return TimelyWeatherViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: TimelyWeatherViewHolder, position: Int) {
        val timelyWeather = getItem(position)
        holder.bind(timelyWeather)
    }
}

class TimelyWeatherViewHolder(
    view: View,
    private val context: Context
) : RecyclerView.ViewHolder(view) {
    private val time: TextView = view.findViewById(R.id.time)
    private val weatherIcon: ImageView = view.findViewById(R.id.weatherIcon)
    private val temperature: TextView = view.findViewById(R.id.temperature)

    fun bind(timelyWeather: TimelyWeatherEntity) {
        time.text = timelyWeather.time ?: ""
        // TODO:画像ダウンロード&セット
        weatherIcon.setImageResource(android.R.mipmap.sym_def_app_icon)
        val tempText =
            "${timelyWeather.temperature}${context.resources.getString(R.string.temperature_unit)}"
        temperature.text = tempText
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