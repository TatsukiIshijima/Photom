package com.tatsuki.core.usecase.ui

import kotlinx.coroutines.flow.MutableStateFlow

interface IPlaceNameView {

    interface IState {
        val mutablePlaceNameFlow: MutableStateFlow<String>
    }

    val state: IState

    fun showPlaceName(place: String)
}