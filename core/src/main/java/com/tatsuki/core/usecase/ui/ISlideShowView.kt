package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.PhotoListEntity

interface ISlideShowView: ILoadingView, IErrorView {
    fun showSlide(photoListEntity: PhotoListEntity)
}