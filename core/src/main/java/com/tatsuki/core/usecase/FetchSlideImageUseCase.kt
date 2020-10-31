package com.tatsuki.core.usecase

import com.google.firebase.storage.StorageReference
import com.tatsuki.core.IErrorView
import com.tatsuki.core.ILoadingView
import com.tatsuki.core.State
import com.tatsuki.core.repository.ISlideImageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class FetchSlideImageUseCase(
    private val slideShowView: ISlideShowView,
    private val slideImageRepository: ISlideImageRepository
) {

    companion object {
        private val TAG = FetchSlideImageUseCase::class.java.simpleName
    }

    @ExperimentalCoroutinesApi
    suspend fun execute() {

        slideShowView.showLoading()

        slideImageRepository.fetchMorningRef().collect { state ->
            when (state) {
                is State.Success -> {
                    slideShowView.showSlide(state.data)
                }
                is State.Failed -> {
                    slideShowView.showError(state.exception)
                }
            }

            slideShowView.hideLoading()
        }
    }
}

interface ISlideShowView: ILoadingView, IErrorView {
    fun showSlide(refList: List<StorageReference>)
}