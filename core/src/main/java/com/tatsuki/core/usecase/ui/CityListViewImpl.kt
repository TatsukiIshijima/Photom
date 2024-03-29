package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.AddressEntity
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CityListViewImpl @Inject constructor(
) : ICityListView {

    class State : ICityListView.IState {
        override val mutableCityNameListFlow = MutableStateFlow<List<AddressEntity.City>>(listOf())
    }

    override val state: ICityListView.IState = State()

    override fun showCityName(list: List<AddressEntity.City>) {
        state.mutableCityNameListFlow.value = list
    }
}