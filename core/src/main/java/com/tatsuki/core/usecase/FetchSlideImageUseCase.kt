package com.tatsuki.core.usecase

import com.google.firebase.storage.StorageReference
import com.tatsuki.core.State
import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.usecase.ui.IErrorView
import com.tatsuki.core.usecase.ui.ILegacySlideShowView
import com.tatsuki.core.usecase.ui.ILoadingView
import com.tatsuki.core.usecase.ui.ISlideShowView
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.photom.photo.response.toPhotoListEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class FetchSlideImageUseCase @Inject constructor(
    val loadingView: ILoadingView,
    val errorView: IErrorView,
    val slideShowView: ISlideShowView,
    private val slideImageRepository: SlideImageRepository,
) {
    suspend fun execute() {

        loadingView.showLoading()

        val photoListResult = slideImageRepository.fetchPhotoList()

        loadingView.hideLoading()

        when (photoListResult) {
            is Result.ClientError -> errorView.showError(
                photoListResult.code,
                photoListResult.message
            )
            is Result.Error -> errorView.showError(photoListResult.code, photoListResult.message)
            is Result.NetworkError -> errorView.showNetworkError()
            is Result.ServerError -> errorView.showInternalServerError()
            is Result.Success -> slideShowView.showSlide(photoListResult.data.toPhotoListEntity())
        }
    }

    @Deprecated("Firebaseを使用しない方向へ修正中のため非推奨")
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