package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.AddressEntity
import kotlinx.coroutines.flow.MutableStateFlow

interface ICityListView {

    interface IState {
        val mutableCityNameListFlow: MutableStateFlow<List<AddressEntity.City>>
    }

    val state: IState

    fun showCityName(list: List<AddressEntity.City>)
}