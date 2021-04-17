package com.tatsuki.photom.view.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tatsuki.photom.R

class TimelyWeatherAdapter(): RecyclerView.Adapter<TimelyWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelyWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timely_weather_item, parent, false)
        return TimelyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimelyWeatherViewHolder, position: Int) {
        holder.apply {
            time.text = "12AM"
            weatherIcon.setImageResource(android.R.mipmap.sym_def_app_icon)
            temperature.text = "22℃"
        }
    }

    override fun getItemCount(): Int {
        return 10
    }
}

class TimelyWeatherViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    val time: TextView = view.findViewById(R.id.time)
    val weatherIcon: ImageView = view.findViewById(R.id.weatherIcon)
    val temperature: TextView = view.findViewById(R.id.temperature)
}