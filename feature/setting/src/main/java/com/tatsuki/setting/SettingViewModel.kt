package com.tatsuki.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatsuki.core.usecase.FetchCityUseCase
import com.tatsuki.data.entity.PrefectureEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val fetchCityUseCase: FetchCityUseCase
) : ViewModel() {

    val cityNameListFlow = fetchCityUseCase
        .cityListView
        .state
        .mutableCityNameListFlow
        .asStateFlow()

    fun fetchCityNameList(prefectureEntity: PrefectureEntity) {
        viewModelScope.launch {
            fetchCityUseCase.execute(prefectureEntity)
        }
    }
}