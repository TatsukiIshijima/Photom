package com.tatsuki.core.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.tatsuki.core.State
import com.tatsuki.data.api.response.PlaceResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PlaceRepository @Inject constructor(
    private val db: FirebaseFirestore
) {
    companion object {
        private const val COLLECTION = "photom"
        private const val DOCUMENT = "place"
    }

    private var _cache: PlaceResponse? = null

    fun fetchPlace(
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ): Flow<State<PlaceResponse?>> {
        return flow {
            try {
                if (_cache != null) {
                    emit(State.success(_cache))
                    return@flow
                }
                val docRef = db.collection(COLLECTION).document(DOCUMENT)
                val result = docRef.get().await()
                val place = result?.toObject(PlaceResponse::class.java)
                _cache = place
                emit(State.success(place))
            } catch (e: Exception) {
                emit(State.failed<PlaceResponse?>(e))
            }
        }.flowOn(dispatcher)
    }
}