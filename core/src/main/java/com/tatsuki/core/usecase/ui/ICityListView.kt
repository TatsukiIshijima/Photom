package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.LocationEntity
import kotlinx.coroutines.flow.MutableStateFlow

interface ICityListView {

    interface IState {
        val mutableCityNameListFlow: MutableStateFlow<List<LocationEntity.City>>
    }

    val state: IState

    fun showCityName(list: List<LocationEntity.City>)
}