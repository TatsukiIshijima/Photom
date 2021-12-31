package com.tatsuki.feature.devicecontrol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tatsuki.core.usecase.SendPowerCommandUseCase
import com.tatsuki.data.entity.DeviceEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class PowerControlViewModel @AssistedInject constructor(
    @Assisted private val deviceEntity: DeviceEntity,
    private val sendPowerCommandUseCase: SendPowerCommandUseCase,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(deviceEntity: DeviceEntity): PowerControlViewModel
    }

    fun sendPowerCommend(isOn: Boolean) {
        viewModelScope.launch {
            sendPowerCommandUseCase.execute(deviceEntity.id, isOn)
        }
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