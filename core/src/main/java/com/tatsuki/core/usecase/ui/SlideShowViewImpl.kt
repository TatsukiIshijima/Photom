package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.PhotoEntity
import com.tatsuki.data.entity.PhotoListEntity
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SlideShowViewImpl @Inject constructor(
) : ISlideShowView {

    class State : ISlideShowView.IState {
        override val mutablePhotoListFlow = MutableStateFlow<List<PhotoEntity>>(listOf())
    }

    override val state: ISlideShowView.IState = State()

    override fun showSlide(photoListEntity: PhotoListEntity) {
        state.mutablePhotoListFlow.value = photoListEntity.list
    }
}