package com.tatsuki.photom.view.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tatsuki.data.entity.CurrentWeatherInfoItem
import com.tatsuki.photom.R

class CurrentWeatherDetailAdapter(

) : ListAdapter<CurrentWeatherInfoItem, CurrentWeatherInfoViewHolder>(CurrentWeatherInfoDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrentWeatherInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.current_weather_detail_item, parent, false)
        return CurrentWeatherInfoViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: CurrentWeatherInfoViewHolder, position: Int) {
        val currentWeatherInfo = getItem(position)
        holder.bind(currentWeatherInfo)
    }
}

class CurrentWeatherInfoViewHolder(
    view: View,
    private val context: Context
) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.detailTitle)
    private val value: TextView = view.findViewById(R.id.detailValue)
    private val unit: TextView = view.findViewById(R.id.detailUnit)

    fun bind(currentWeatherInfoItem: CurrentWeatherInfoItem) {
        when (currentWeatherInfoItem) {
            is CurrentWeatherInfoItem.PressureItem -> {
                title.text = context.resources.getText(R.string.pressure)
                value.text = currentWeatherInfoItem.value.toString()
                unit.text = context.resources.getText(R.string.pressure_unit)
            }
            is CurrentWeatherInfoItem.HumidityItem -> {
                title.text = context.resources.getText(R.string.humidity)
                value.text = currentWeatherInfoItem.value.toString()
                unit.text = context.resources.getText(R.string.humidity_unit)
            }
            is CurrentWeatherInfoItem.WindSpeedItem -> {
                title.text = context.resources.getText(R.string.wind_speed)
                value.text = currentWeatherInfoItem.value.toString()
                unit.text = context.resources.getText(R.string.wind_speed_unit)
            }
        }
    }
}

object CurrentWeatherInfoDiffCallback : DiffUtil.ItemCallback<CurrentWeatherInfoItem>() {
    override fun areItemsTheSame(
        oldItem: CurrentWeatherInfoItem,
        newItem: CurrentWeatherInfoItem
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: CurrentWeatherInfoItem,
        newItem: CurrentWeatherInfoItem
    ): Boolean = oldItem == newItem
}