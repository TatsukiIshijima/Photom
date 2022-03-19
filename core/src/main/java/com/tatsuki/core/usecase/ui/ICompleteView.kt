package com.tatsuki.core.usecase.ui

import kotlinx.coroutines.flow.MutableStateFlow

interface ICompleteView {

    interface IState {
        val mutableCompleteFlow: MutableStateFlow<Unit?>
    }

    val state: IState

    fun onComplete()
}