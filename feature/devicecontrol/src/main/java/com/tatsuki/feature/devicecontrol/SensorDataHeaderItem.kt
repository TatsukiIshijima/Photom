package com.tatsuki.feature.devicecontrol

import android.view.View
import com.tatsuki.data.entity.SensorEntity
import com.tatsuki.feature.devicecontrol.databinding.ItemSensorDataHeaderBinding
import com.xwray.groupie.viewbinding.BindableItem

class SensorDataHeaderItem(
    private val sensorEntity: SensorEntity?
) : BindableItem<ItemSensorDataHeaderBinding>() {

    override fun bind(binding: ItemSensorDataHeaderBinding, position: Int) {
        binding.apply {
            sensorDataItemHeaderTemperatureValue.text =
                if (sensorEntity?.temp != null) "%.1f".format(sensorEntity.temp) else "-"
            sensorDataItemHeaderPressureValue.text =
                if (sensorEntity?.pressure != null) "%.1f".format(sensorEntity.pressure) else "-"
            sensorDataItemHeaderHumidityValue.text =
                if (sensorEntity?.humidity != null) "%.1f".format(sensorEntity.humidity) else "-"
            sensorDataItemHeaderLuxValue.text =
                if (sensorEntity?.lux != null) "%.1f".format(sensorEntity.lux) else "-"
        }
    }

    override fun getLayout(): Int =
        R.layout.item_sensor_data_header

    override fun initializeViewBinding(view: View): ItemSensorDataHeaderBinding =
        ItemSensorDataHeaderBinding.bind(view)
}