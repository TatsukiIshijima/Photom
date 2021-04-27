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
import com.tatsuki.core.entity.DailyWeatherEntity
import com.tatsuki.photom.GlideApp
import com.tatsuki.photom.R

class DailyWeatherAdapter(

) : ListAdapter<DailyWeatherEntity, DailyWeatherViewHolder>(DailyWeatherDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.daily_weather_item, parent, false)
        return DailyWeatherViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val dailyWeather = getItem(position)
        holder.bind(dailyWeather)
    }
}

class DailyWeatherViewHolder(
    view: View,
    private val context: Context
): RecyclerView.ViewHolder(view) {
    private val date: TextView = view.findViewById(R.id.date)
    private val day: TextView = view.findViewById(R.id.day)
    private val weatherIcon: ImageView = view.findViewById(R.id.weatherIcon)
    private val maxTemp: TextView = view.findViewById(R.id.maxTemperature)
    private val minTemp: TextView = view.findViewById(R.id.minTemperature)

    fun bind(dailyWeather: DailyWeatherEntity) {
        date.text = dailyWeather.date ?: ""
        day.text = dailyWeather.day ?: ""
        val minTempText =
            "${dailyWeather.minTemperature}${context.resources.getString(R.string.temperature_unit)}"
        val maxTempText =
            "${dailyWeather.maxTemperature}${context.resources.getString(R.string.temperature_unit)}"
        minTemp.text = minTempText
        maxTemp.text = maxTempText
        if (!dailyWeather.iconUrl.isNullOrEmpty()) {
            val iconUrl = "http://openweathermap.org/img/wn/${dailyWeather.iconUrl}@2x.png"
            GlideApp.with(context).load(iconUrl).into(weatherIcon)
        }
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