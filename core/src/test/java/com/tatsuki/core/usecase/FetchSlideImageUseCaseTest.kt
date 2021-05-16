package com.tatsuki.core.usecase

import com.google.firebase.storage.StorageReference
import com.tatsuki.core.State
import com.tatsuki.core.repository.SlideImageRepository
import com.tatsuki.core.usecase.ui.ISlideShowView
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.*

class FetchSlideImageUseCaseTest {

    @Test
    fun 朝の時間帯の画像参照のリストを取得し表示できること() {
        val slideImageRepository = mock<SlideImageRepository> {
            on { fetchMorningImageReferences() } doReturn flow<State<List<StorageReference>>> {
                emit(State.success(listOf()))
            }
        }
        val view = mock<ISlideShowView> {
            on { showLoading() } doAnswer { }
            on { hideLoading() } doAnswer { }
            on { showSlide(any()) } doAnswer { }
        }
        val usecase = FetchSlideImageUseCase(view, slideImageRepository)
        runBlocking {
            usecase.execute(7)
        }

        verify(view, times(1)).showLoading()
        verify(slideImageRepository, times(1)).fetchMorningImageReferences()
        verify(slideImageRepository, never()).fetchNoonImageReferences()
        verify(slideImageRepository, never()).fetchEveningImageReferences()
        verify(view, times(1)).showSlide(any())
        verify(view, never()).showError(any())
        verify(view, times(1)).hideLoading()
    }

    @Test
    fun 昼の時間帯の画像参照のリストを取得し表示できること() {
        val slideImageRepository = mock<SlideImageRepository> {
            on { fetchNoonImageReferences() } doReturn flow<State<List<StorageReference>>> {
                emit(State.success(listOf()))
            }
        }
        val view = mock<ISlideShowView> {
            on { showLoading() } doAnswer { }
            on { hideLoading() } doAnswer { }
            on { showSlide(any()) } doAnswer { }
        }
        val usecase = FetchSlideImageUseCase(view, slideImageRepository)
        runBlocking {
            usecase.execute(9)
        }

        verify(view, times(1)).showLoading()
        verify(slideImageRepository, never()).fetchMorningImageReferences()
        verify(slideImageRepository, times(1)).fetchNoonImageReferences()
        verify(slideImageRepository, never()).fetchEveningImageReferences()
        verify(view, times(1)).showSlide(any())
        verify(view, never()).showError(any())
        verify(view, times(1)).hideLoading()
    }

    @Test
    fun 夜の時間帯の画像参照のリストを取得し表示できること() {
        val slideImageRepository = mock<SlideImageRepository> {
            on { fetchEveningImageReferences() } doReturn flow<State<List<StorageReference>>> {
                emit(State.success(listOf()))
            }
        }
        val view = mock<ISlideShowView> {
            on { showLoading() } doAnswer { }
            on { hideLoading() } doAnswer { }
            on { showSlide(any()) } doAnswer { }
        }
        val usecase = FetchSlideImageUseCase(view, slideImageRepository)
        runBlocking {
            usecase.execute(17)
        }

        verify(view, times(1)).showLoading()
        verify(slideImageRepository, never()).fetchMorningImageReferences()
        verify(slideImageRepository, never()).fetchNoonImageReferences()
        verify(slideImageRepository, times(1)).fetchEveningImageReferences()
        verify(view, times(1)).showSlide(any())
        verify(view, never()).showError(any())
        verify(view, times(1)).hideLoading()
    }
}