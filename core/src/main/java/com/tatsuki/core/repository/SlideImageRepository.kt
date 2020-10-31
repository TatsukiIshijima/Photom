package com.tatsuki.core.repository

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.tatsuki.core.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

interface ISlideImageRepository {
    suspend fun fetchMorningRef(): Flow<State<List<StorageReference>>>
    suspend fun fetchNoonRef(): Flow<State<List<StorageReference>>>
    suspend fun fetchEveningRef(): Flow<State<List<StorageReference>>>
}

// Firebase-ing with Kotlin Coroutines + Flow
// https://medium.com/firebase-developers/firebase-ing-with-kotlin-coroutines-flow-dab1bc364816

class SlideImageRepository(
    private val storage: FirebaseStorage
): ISlideImageRepository {
    companion object {
        private val TAG = SlideImageRepository::class.java.simpleName
    }

    @ExperimentalCoroutinesApi
    private suspend fun fetchImageReferences(
        path: String,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Flow<State<List<StorageReference>>> {
        return flow {
            try {
                val ref = storage.reference.child(path)
                val result = ref.listAll().await()
                // Flow の emit が Coroutine Scope でしか実行できないので
                // kotlinx-coroutines-play-services で await を使えるようにした
                result?.let {
                    emit(State.success(it.items))
                }
            } catch (e: Exception) {
                emit(State.failed<List<StorageReference>>(e))
            }
        }.flowOn(dispatcher)
    }

    @ExperimentalCoroutinesApi
    override suspend fun fetchMorningRef(): Flow<State<List<StorageReference>>> =
        fetchImageReferences("photom/morning")

    @ExperimentalCoroutinesApi
    override suspend fun fetchNoonRef(): Flow<State<List<StorageReference>>> =
        fetchImageReferences("photom/noon")

    @ExperimentalCoroutinesApi
    override suspend fun fetchEveningRef(): Flow<State<List<StorageReference>>> =
        fetchImageReferences("photom/evening")
}