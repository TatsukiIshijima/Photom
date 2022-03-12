package com.tatsuki.core.usecase

import com.tatsuki.core.repository.PlaceRepository
import com.tatsuki.core.usecase.ui.ILoadingView
import com.tatsuki.core.usecase.ui.IPlaceNameView
import javax.inject.Inject

class LoadLocationUseCase @Inject constructor(
    val loadingView: ILoadingView,
    val placeNameView: IPlaceNameView,
    private val placeRepository: PlaceRepository
) {
    suspend fun execute() {

        loadingView.showLoading()

        val prefecture = placeRepository.getPrefecture()
        val city = placeRepository.getCity()

        val locationName = "${prefecture.name}${city.name}"
        placeNameView.showPlaceName(locationName)

        loadingView.hideLoading()
    }
}