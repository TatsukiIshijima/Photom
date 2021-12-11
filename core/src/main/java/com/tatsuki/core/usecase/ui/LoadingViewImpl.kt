package com.tatsuki.core.usecase.ui

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LoadingViewImpl @Inject constructor(
) : ILoadingView {

    class State : ILoadingView.IState {
        override val mutableLoadingFlow = MutableStateFlow(false)
    }

    override val state: ILoadingView.IState = State()

    override fun showLoading() {
        state.mutableLoadingFlow.value = true
    }

    override fun hideLoading() {
        state.mutableLoadingFlow.value = false
    }
}