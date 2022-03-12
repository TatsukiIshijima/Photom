package com.tatsuki.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatsuki.core.usecase.CashCityUseCase
import com.tatsuki.core.usecase.FetchCityUseCase
import com.tatsuki.core.usecase.SaveLocationUseCase
import com.tatsuki.data.entity.AddressEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SettingViewModel @Inject constructor(
    private val fetchCityUseCase: FetchCityUseCase,
    private val cashCityUseCase: CashCityUseCase,
    private val saveLocationUseCase: SaveLocationUseCase
) : ViewModel() {

    val loadingFlow = merge(
        fetchCityUseCase.loadingView.state.mutableLoadingFlow.asStateFlow(),
        saveLocationUseCase.loadingView.state.mutableLoadingFlow.asStateFlow()
    )

    val cityNameListFlow = fetchCityUseCase
        .cityListView
        .state
        .mutableCityNameListFlow
        .asStateFlow()

    val placeNameFlow = merge(
        fetchCityUseCase.placeNameView.state.mutablePlaceNameFlow.asStateFlow(),
        cashCityUseCase.placeNameView.state.mutablePlaceNameFlow.asStateFlow()
    )

    fun fetchCityNameList(prefecture: AddressEntity.Prefecture) {
        viewModelScope.launch {
            fetchCityUseCase.execute(prefecture)
        }
    }

    fun cashSelectedCity(city: AddressEntity.City) {
        cashCityUseCase.execute(city)
    }

    fun saveLocation() {
        viewModelScope.launch {
            saveLocationUseCase.execute()
        }
    }
}