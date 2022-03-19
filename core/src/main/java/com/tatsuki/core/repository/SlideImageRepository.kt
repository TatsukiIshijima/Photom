package com.tatsuki.core.repository

import com.tatsuki.data.api.ApiClient
import com.tatsuki.data.api.Result
import com.tatsuki.data.api.photom.PhotomApi
import com.tatsuki.data.api.photom.photo.response.PhotoListResponse
import javax.inject.Inject

// Firebase-ing with Kotlin Coroutines + Flow
// https://medium.com/firebase-developers/firebase-ing-with-kotlin-coroutines-flow-dab1bc364816
// How to use Kotlin Flows with Firestore
// https://medium.com/firebase-tips-tricks/how-to-use-kotlin-flows-with-firestore-6c7ee9ae12f3

class SlideImageRepository @Inject constructor(
    private val photomApi: PhotomApi
) {

    suspend fun fetchPhotoList(): Result<PhotoListResponse> =
        ApiClient.safeApiCall({ photomApi.getPhotoList() })
}