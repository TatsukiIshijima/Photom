package com.tatsuki.feature.slideshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatsuki.core.usecase.FetchSlideImageUseCase
import com.tatsuki.core.usecase.ui.ISlideShowView
import com.tatsuki.data.entity.PhotoEntity
import com.tatsuki.data.entity.PhotoListEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlideShowViewModel @Inject constructor(
    private val fetchSlideImageUseCase: FetchSlideImageUseCase
) : ViewModel() {

    private val mutableLoadingFlow = MutableStateFlow(false)
    val loadingFlow = mutableLoadingFlow.asStateFlow()

    private val mutablePhotoListFlow = MutableStateFlow(listOf<PhotoEntity>())
    val photoListFlow = mutablePhotoListFlow.asStateFlow()

    private val view = object : ISlideShowView {
        override fun showSlide(photoListEntity: PhotoListEntity) {
            mutablePhotoListFlow.value = photoListEntity.list
        }

        override fun showLoading() {
            mutableLoadingFlow.value = true
        }

        override fun hideLoading() {
            mutableLoadingFlow.value = false
        }

        override fun showError(e: Exception) {

        }

        override fun showError(code: Int?, message: String?) {

        }

        override fun showInternalServerError() {

        }

        override fun showNetworkError() {

        }
    }

    fun showSlide() {
        viewModelScope.launch {
            fetchSlideImageUseCase.execute(view)
        }
    }
}