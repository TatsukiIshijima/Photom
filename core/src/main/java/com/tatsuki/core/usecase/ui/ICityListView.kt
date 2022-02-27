package com.tatsuki.core.usecase.ui

import kotlinx.coroutines.flow.MutableStateFlow

interface ICityListView {

    interface IState {
        val mutableCityNameListFlow: MutableStateFlow<List<String>>
    }

    val state: IState

    fun showCityName(list: List<String>)
}