package com.tatsuki.core.usecase

import com.google.firebase.storage.StorageReference
import com.tatsuki.core.State
import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.usecase.ui.ISlideShowView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class FetchSlideImageUseCase(
    private val slideShowView: ISlideShowView,
    private val slideImageRepository: SlideImageRepository,
) {

    companion object {
        private val TAG = FetchSlideImageUseCase::class.java.simpleName
    }

    suspend fun execute(hour: Int) {
        val fetchImageRefFlow: Flow<State<out List<StorageReference>>> = when (hour) {
            in 1..8 -> slideImageRepository.fetchMorningImageReferences()
            in 9..16 -> slideImageRepository.fetchNoonImageReferences()
            else -> slideImageRepository.fetchEveningImageReferences()
        }

        slideShowView.showLoading()

        fetchImageRefFlow.collect { state ->
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