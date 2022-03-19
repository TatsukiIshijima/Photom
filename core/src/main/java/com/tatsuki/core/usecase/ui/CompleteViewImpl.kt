package com.tatsuki.core.usecase.ui

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CompleteViewImpl @Inject constructor(
) : ICompleteView {

    class State : ICompleteView.IState {
        override val mutableCompleteFlow = MutableStateFlow<Unit?>(null)
    }

    override val state: ICompleteView.IState = State()

    override fun onComplete() {
        state.mutableCompleteFlow.value = Unit
    }
}