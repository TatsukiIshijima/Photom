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
            usecase.execute(TimeZone.Morning)

            verify(view, times(1)).showLoading()
            verify(repository, times(1)).fetchMorningRef()
            verify(repository, never()).fetchNoonRef()
            verify(repository, never()).fetchEveningRef()
            verify(view, times(1)).showSlide(any())
            verify(view, never()).showError(any())
            verify(view, times(1)).hideLoading()
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun 昼の時間帯の画像参照のリストを取得し表示できること() {
        Mockito.`when`(repository.fetchNoonRef())
            .thenReturn( flow {
                emit(State.success(listOf<StorageReference>()))
            })
        runBlocking {
            usecase.execute(TimeZone.Noon)

            verify(view, times(1)).showLoading()
            verify(repository, times(1)).fetchNoonRef()
            verify(repository, never()).fetchMorningRef()
            verify(repository, never()).fetchEveningRef()
            verify(view, times(1)).showSlide(any())
            verify(view, never()).showError(any())
            verify(view, times(1)).hideLoading()
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun 夜の時間帯の画像参照のリストを取得し表示できること() {
        Mockito.`when`(repository.fetchEveningRef())
            .thenReturn( flow {
                emit(State.success(listOf<StorageReference>()))
            })
        runBlocking {
            usecase.execute(TimeZone.Evening)

            verify(view, times(1)).showLoading()
            verify(repository, times(1)).fetchEveningRef()
            verify(repository, never()).fetchMorningRef()
            verify(repository, never()).fetchNoonRef()
            verify(view, times(1)).showSlide(any())
            verify(view, never()).showError(any())
            verify(view, times(1)).hideLoading()
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun 朝の時間帯の画像参照のリストの取得した時にエラーが表示されること() {
        Mockito.`when`(repository.fetchMorningRef())
            .thenReturn( flow {
                emit(State.failed<List<StorageReference>>(Exception()))
            })
        runBlocking {
            usecase.execute(TimeZone.Morning)

            verify(view, times(1)).showLoading()
            verify(repository, times(1)).fetchMorningRef()
            verify(repository, never()).fetchNoonRef()
            verify(repository, never()).fetchEveningRef()
            verify(view, never()).showSlide(any())
            verify(view, times(1)).showError(any())
            verify(view, times(1)).hideLoading()
        }
    }
}