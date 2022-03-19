package com.tatsuki.core.usecase.ui

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlaceNameViewImpl @Inject constructor(
) : IPlaceNameView {

    class State : IPlaceNameView.IState {
        override val mutablePlaceNameFlow = MutableStateFlow<String>("")
    }

    override val state: IPlaceNameView.IState = State()

    override fun showPlaceName(place: String) {
        state.mutablePlaceNameFlow.value = place
    }
}