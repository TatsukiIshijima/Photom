package com.tatsuki.feature.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tatsuki.data.entity.CurrentWeatherInfoItem
import com.tatsuki.feature.weather.databinding.ItemDailyWeatherInfoBinding

class DailyWeatherInfoAdapter(

) : ListAdapter<CurrentWeatherInfoItem, DailyWeatherInfoViewHolder>(DailyWeatherInfoDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyWeatherInfoViewHolder {
        val binding = ItemDailyWeatherInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DailyWeatherInfoViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: DailyWeatherInfoViewHolder, position: Int) {
        val currentWeatherInfo = getItem(position)
        holder.bind(currentWeatherInfo)
    }
}

class DailyWeatherInfoViewHolder(
    binding: ItemDailyWeatherInfoBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    private val title: TextView = binding.humidityTitle
    private val value: TextView = binding.humidityValue
    private val unit: TextView = binding.humidityUnit

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

object DailyWeatherInfoDiffCallback : DiffUtil.ItemCallback<CurrentWeatherInfoItem>() {
    override fun areItemsTheSame(
        oldItem: CurrentWeatherInfoItem,
        newItem: CurrentWeatherInfoItem
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: CurrentWeatherInfoItem,
        newItem: CurrentWeatherInfoItem
    ): Boolean = oldItem == newItem
}