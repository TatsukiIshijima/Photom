package com.tatsuki.core.usecase

import com.google.firebase.storage.StorageReference
import com.tatsuki.core.State
import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.usecase.ui.ISlideShowView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FetchSlideImageUseCase @Inject constructor(
    private val slideImageRepository: SlideImageRepository,
) {
    // https://github.com/OverLordAct/HiltCleanArchitecture
    suspend fun execute(hour: Int, view: ISlideShowView) {
        val fetchImageRefFlow: Flow<State<out List<StorageReference>>> = when (hour) {
            in 1..8 -> slideImageRepository.fetchMorningImageReferences()
            in 9..16 -> slideImageRepository.fetchNoonImageReferences()
            else -> slideImageRepository.fetchEveningImageReferences()
        }

        view.showLoading()

        fetchImageRefFlow.collect {
            when (it) {
                is State.Failed -> {
                    view.showError(it.exception)
                }
                is State.Success -> {
                    view.showSlide(it.data)
                }
            }
            view.hideLoading()
        }
    }
}