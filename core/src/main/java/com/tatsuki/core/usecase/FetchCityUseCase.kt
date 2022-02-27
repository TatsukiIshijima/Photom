package com.tatsuki.core.usecase

import com.tatsuki.core.repository.PlaceRepository
import com.tatsuki.core.usecase.ui.ICityListView
import com.tatsuki.data.api.Result
import com.tatsuki.data.entity.PrefectureEntity
import javax.inject.Inject

class FetchCityUseCase @Inject constructor(
    val cityListView: ICityListView,
    private val placeRepository: PlaceRepository
) {
    suspend fun execute(prefectureEntity: PrefectureEntity) {
        when (val result = placeRepository.fetchCity(prefectureEntity)) {
            is Result.Success -> {
                if (result.data.status != "OK") {
                    return
                }
                val cityNameList = result.data.data.map { it.name }
                cityListView.showCityName(cityNameList)
            }
            else -> {

            }
        }
    }
}