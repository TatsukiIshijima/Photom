package com.tatsuki.feature.devicecontrol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tatsuki.data.entity.DeviceEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import timber.log.Timber

class AirConditionerControlViewModel @AssistedInject constructor(
    @Assisted private val deviceEntity: DeviceEntity
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(deviceEntity: DeviceEntity): AirConditionerControlViewModel
    }

    fun execute() {
        Timber.d("name: ${deviceEntity.name}")
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            deviceEntity: DeviceEntity
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(deviceEntity) as T
            }
        }
    }
}