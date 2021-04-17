package com.tatsuki.photom.view.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tatsuki.photom.R

class DailyWeatherAdapter(): RecyclerView.Adapter<DailyWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_weather_item, parent, false)
        return DailyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        holder.date.text = "4/1"
        holder.day.text = "月曜日"
        holder.weatherIcon.setImageResource(android.R.mipmap.sym_def_app_icon)
        holder.minTemp.text = "10℃"
        holder.maxTemp.text = "20℃"
    }

    override fun getItemCount(): Int {
        return 5
    }
}

class DailyWeatherViewHolder(
    view: View
): RecyclerView.ViewHolder(view) {
    val date: TextView = view.findViewById(R.id.date)
    val day: TextView = view.findViewById(R.id.day)
    val weatherIcon: ImageView = view.findViewById(R.id.weatherIcon)
    val maxTemp: TextView = view.findViewById(R.id.maxTemperature)
    val minTemp: TextView = view.findViewById(R.id.minTemperature)
}