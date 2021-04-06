package com.tatsuki.core.usecase

import com.google.firebase.storage.StorageReference
import com.tatsuki.core.State
import com.tatsuki.core.repository.ISlideImageRepository
import com.tatsuki.core.usecase.ui.ISlideShowView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

enum class TimeZone {
    Morning,
    Noon,
    Evening
}

class FetchSlideImageUseCase(
    private val slideShowView: ISlideShowView,
    private val slideImageRepository: ISlideImageRepository
) {

    companion object {
        private val TAG = FetchSlideImageUseCase::class.java.simpleName
    }

    @ExperimentalCoroutinesApi
    suspend fun execute(timeZone: TimeZone) {
        val fetchFlow: Flow<State<List<StorageReference>>> = when(timeZone) {
            TimeZone.Morning -> {
                slideImageRepository.fetchMorningRef()
            }
            TimeZone.Noon -> {
                slideImageRepository.fetchNoonRef()
            }
            TimeZone.Evening -> {
                slideImageRepository.fetchEveningRef()
            }
        }

        slideShowView.showLoading()

        fetchFlow.collect { state ->
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