package com.tatsuki.core.usecase

import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.usecase.ui.IErrorView
import com.tatsuki.core.usecase.ui.ILoadingView
import com.tatsuki.core.usecase.ui.ISlideShowView
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.photom.photo.response.toPhotoListEntity
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
}