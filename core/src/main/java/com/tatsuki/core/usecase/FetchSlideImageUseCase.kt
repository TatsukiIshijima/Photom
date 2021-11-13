package com.tatsuki.core.usecase

import com.google.firebase.storage.StorageReference
import com.tatsuki.core.State
import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.usecase.ui.ILegacySlideShowView
import com.tatsuki.core.usecase.ui.ISlideShowView
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.photom.photo.response.toPhotoListEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FetchSlideImageUseCase @Inject constructor(
    private val slideImageRepository: SlideImageRepository,
) {
    suspend fun execute(view: ISlideShowView) {

        view.showLoading()

        val result = slideImageRepository.fetchPhotoList()

        view.hideLoading()

        when (result) {
            is Result.ClientError -> view.showError(result.code, result.message)
            is Result.Error -> view.showError(result.code, result.message)
            is Result.NetworkError -> view.showNetworkError()
            is Result.ServerError -> view.showInternalServerError()
            is Result.Success -> view.showSlide(result.data.toPhotoListEntity())
        }
    }

    // https://github.com/OverLordAct/HiltCleanArchitecture
    suspend fun execute(hour: Int, view: ILegacySlideShowView) {
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