package com.tatsuki.core.usecase.ui

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CityListViewImpl @Inject constructor(
) : ICityListView {

    class State : ICityListView.IState {
        override val mutableCityNameListFlow = MutableStateFlow<List<String>>(listOf())
    }

    override val state: ICityListView.IState = State()

    override fun showCityName(list: List<String>) {
        state.mutableCityNameListFlow.value = list
    }
}