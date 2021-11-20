package com.tatsuki.core.usecase.ui

import com.google.firebase.storage.StorageReference

interface ILegacySlideShowView : ILoadingView, IErrorView {
    fun showSlide(refList: List<StorageReference>)
}