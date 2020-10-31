package com.tatsuki.core.usecase

import com.google.firebase.storage.StorageReference
import com.nhaarman.mockitokotlin2.*
import com.tatsuki.core.State
import com.tatsuki.core.repository.ISlideImageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class FetchSlideImageUseCaseTest {

    private val view: ISlideShowView = mock()
    private val repository: ISlideImageRepository = mock()
    private val usecase = FetchSlideImageUseCase(view, repository)

    @Before
    fun initialize() {
    }

    @ExperimentalCoroutinesApi
    @Test
    fun 朝の時間帯の画像参照のリストを取得し表示できること() {
        Mockito.`when`(repository.fetchMorningRef())
            .thenReturn( flow {
                emit(State.success(listOf<StorageReference>()))
            })
        runBlocking {
            usecase.execute()

            verify(view, times(1)).showLoading()
            verify(view, times(1)).showSlide(any())
            verify(view, never()).showError(any())
            verify(view, times(1)).hideLoading()
        }
    }
}