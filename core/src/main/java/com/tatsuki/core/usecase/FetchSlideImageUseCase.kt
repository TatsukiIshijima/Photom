package com.tatsuki.core.usecase

import com.tatsuki.core.State
import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.usecase.ui.ISlideShowView
import kotlinx.coroutines.flow.collect
import java.time.LocalTime

class FetchSlideImageUseCase(
    private val slideShowView: ISlideShowView,
    private val slideImageRepository: SlideImageRepository
) {

    companion object {
        private val TAG = FetchSlideImageUseCase::class.java.simpleName
    }

    suspend fun execute() {
        val path: String = when (LocalTime.now().hour) {
            in 1..8 -> "photom/morning"
            in 9..16 -> "photom/noon"
            else -> "photom/evening"
        }

        slideShowView.showLoading()

        // TODO:pathの動的変更
        slideImageRepository.fetchImageReferences("photom/morning").collect { state ->
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