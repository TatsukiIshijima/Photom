package com.tatsuki.core.usecase

import com.tatsuki.core.repository.PlaceRepository
import com.tatsuki.core.usecase.ui.IPlaceNameView
import com.tatsuki.data.entity.AddressEntity
import javax.inject.Inject

class CashCityUseCase @Inject constructor(
    val placeNameView: IPlaceNameView,
    private val placeRepository: PlaceRepository
) {
    fun execute(city: AddressEntity.City) {
        placeRepository.cashCity(city)
        val prefecture = placeRepository.prefectureCash
        prefecture?.let {
            placeNameView.showPlaceName("${it.name}${city.name}")
        }
    }
}