package com.tatsuki.core.repository

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.tatsuki.core.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

// Firebase-ing with Kotlin Coroutines + Flow
// https://medium.com/firebase-developers/firebase-ing-with-kotlin-coroutines-flow-dab1bc364816
// How to use Kotlin Flows with Firestore
// https://medium.com/firebase-tips-tricks/how-to-use-kotlin-flows-with-firestore-6c7ee9ae12f3

class SlideImageRepository @Inject constructor(
    private val storage: FirebaseStorage
) {
    companion object {
        private val TAG = SlideImageRepository::class.java.simpleName
    }

    private var tempPath = ""
    private var cache: List<StorageReference>? = null

    private fun fetchImageReferences(
        path: String,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Flow<State<List<StorageReference>>> {
        return flow {
            if (tempPath == path && cache != null) {
                cache?.let {
                    emit(State.success(it))
                }
                return@flow
            }
            try {
                val ref = storage.reference.child(path)
                val result = ref.listAll().await()
                // Flow の emit が Coroutine Scope でしか実行できないので
                // kotlinx-coroutines-play-services で await を使えるようにした
                result?.let {
                    tempPath = path
                    cache = it.items
                    emit(State.success(it.items))
                }
            } catch (e: Exception) {
                emit(State.failed<List<StorageReference>>(e))
            }
        }.flowOn(dispatcher)
    }

    fun fetchMorningImageReferences(): Flow<State<List<StorageReference>>> =
        fetchImageReferences("photom/morning")

    fun fetchNoonImageReferences(): Flow<State<List<StorageReference>>> =
        fetchImageReferences("photom/noon")

    fun fetchEveningImageReferences(): Flow<State<List<StorageReference>>> =
        fetchImageReferences("photom/evening")
}