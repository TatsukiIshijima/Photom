package com.tatsuki.core.usecase.ui

import com.tatsuki.data.entity.PhotoListEntity

interface ISlideShowView {
    fun showSlide(photoListEntity: PhotoListEntity)
}