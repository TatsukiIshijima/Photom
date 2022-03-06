package com.tatsuki.core.usecase

import com.tatsuki.core.repository.PlaceRepository
import com.tatsuki.core.usecase.ui.ICityListView
import com.tatsuki.core.usecase.ui.ILoadingView
import com.tatsuki.data.api.Result
import com.tatsuki.data.entity.AddressEntity
import javax.inject.Inject

class FetchCityUseCase @Inject constructor(
    val loadingView: ILoadingView,
    val cityListView: ICityListView,
    private val placeRepository: PlaceRepository
) {
    suspend fun execute(prefecture: AddressEntity.Prefecture) {
        loadingView.showLoading()
        val result = placeRepository.fetchCity(prefecture)
        loadingView.hideLoading()
        when (result) {
            is Result.Success -> {
                if (result.data.status != "OK") {
                    return
                }
                val cityNameList = result.data.data.map {
                    AddressEntity.City(it.id, it.name)
                }
                cityListView.showCityName(cityNameList)
            }
            else -> {

            }
        }
    }
}