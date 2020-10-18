package com.tatsuki.core.repository

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.tatsuki.core.State
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class SlideImageRepository(
    private val storage: FirebaseStorage
) {
    companion object {
        private val TAG = SlideImageRepository::class.java.simpleName
    }

    @ExperimentalCoroutinesApi
    private suspend fun fetchImageReferences(
        path: String,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) = callbackFlow<State<List<StorageReference>>> {
        val ref = storage.reference.child(path)
        ref.listAll()
            .addOnSuccessListener {
                if (it != null) offer(State.success(it.items))
                else offer(State.failed(Exception("Storage list is null.")))
            }
            .addOnFailureListener {
                offer(State.failed(it))
            }
    }.flowOn(dispatcher)

    @ExperimentalCoroutinesApi
    suspend fun fetchMorningRef(): Flow<State<List<StorageReference>>> =
        fetchImageReferences("photom/morning")

    @ExperimentalCoroutinesApi
    suspend fun fetchNoonRef(): Flow<State<List<StorageReference>>> =
        fetchImageReferences("photom/noon")

    @ExperimentalCoroutinesApi
    suspend fun fetchEveningRef(): Flow<State<List<StorageReference>>> =
        fetchImageReferences("photom/evening")
}