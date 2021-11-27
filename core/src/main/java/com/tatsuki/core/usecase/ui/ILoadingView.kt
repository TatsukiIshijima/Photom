package com.tatsuki.core.usecase.ui

import kotlinx.coroutines.flow.MutableStateFlow

interface ILoadingView {

    interface IState {
        val mutableLoadingFlow: MutableStateFlow<Boolean>
    }

    val state: IState

    fun showLoading()
    fun hideLoading()
}