package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.PhotoEntity
import com.tatsuki.data.entity.PhotoListEntity
import kotlinx.coroutines.flow.MutableStateFlow

interface ISlideShowView {

    interface IState {
        val mutablePhotoListFlow: MutableStateFlow<List<PhotoEntity>>
    }

    val state: IState

    fun showSlide(photoListEntity: PhotoListEntity)
}