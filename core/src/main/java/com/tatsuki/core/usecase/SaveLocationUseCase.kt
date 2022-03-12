package com.tatsuki.core.usecase

import com.tatsuki.core.repository.PlaceRepository
import com.tatsuki.core.usecase.ui.IErrorView
import com.tatsuki.core.usecase.ui.ILoadingView
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.addresssearch.response.toGeoLocationEntity
import javax.inject.Inject

class SaveLocationUseCase @Inject constructor(
    val loadingView: ILoadingView,
    val errorView: IErrorView,
    private val placeRepository: PlaceRepository
) {
    suspend fun execute() {

        val prefecture = placeRepository.prefectureCash
        val city = placeRepository.cityCash
        if (prefecture == null || city == null) {
            return
        }
        val locationName = "${prefecture.name}${city.name}"

        loadingView.showLoading()

        when (val addressResult = placeRepository.fetchAddress(locationName)) {
            is Result.ClientError -> {}
            is Result.Error -> {}
            Result.NetworkError -> errorView.showNetworkError()
            Result.ServerError -> errorView.showInternalServerError()
            is Result.Success -> {
                val geoLocation = addressResult.data.firstOrNull()?.toGeoLocationEntity()
                geoLocation?.let {
                    placeRepository.saveGeoLocation(geoLocation)
                    placeRepository.savePrefecture(prefecture)
                    placeRepository.saveCity(city)
                }
            }
        }

        loadingView.hideLoading()
    }
}