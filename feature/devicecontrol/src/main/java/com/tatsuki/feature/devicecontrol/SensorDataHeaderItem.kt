package com.tatsuki.feature.devicecontrol

import android.view.View
import com.tatsuki.data.entity.SensorEntity
import com.tatsuki.feature.devicecontrol.databinding.ItemSensorDataHeaderBinding
import com.xwray.groupie.viewbinding.BindableItem

class SensorDataHeaderItem(
    private val sensorEntity: SensorEntity
) : BindableItem<ItemSensorDataHeaderBinding>() {

    override fun bind(binding: ItemSensorDataHeaderBinding, position: Int) {
        binding.apply {
            sensorDataItemHeaderTemperatureValue.text = "%.1f".format(sensorEntity.temp)
            sensorDataItemHeaderPressureValue.text = "%.1f".format(sensorEntity.pressure)
            sensorDataItemHeaderHumidityValue.text = "%.1f".format(sensorEntity.humidity)
            sensorDataItemHeaderLuxValue.text = "%.1f".format(sensorEntity.lux)
        }
    }

    override fun getLayout(): Int =
        R.layout.item_sensor_data_header

    override fun initializeViewBinding(view: View): ItemSensorDataHeaderBinding =
        ItemSensorDataHeaderBinding.bind(view)

    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return DeviceControlFragment.SPAN_COUNT
    }
}