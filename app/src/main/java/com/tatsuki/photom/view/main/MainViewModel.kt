package com.tatsuki.photom.view.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatsuki.photom.FaceAnalyzer
import com.tatsuki.photom.LuminosityAnalyzer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    var currentPage: Int = 0
        private set

    private val faceMutableLiveData = MutableLiveData<Int>()
    private val luminosityMutableLiveData = MutableLiveData<Double>()

    val faceLiveData: LiveData<Int> = faceMutableLiveData
    val luminosityLiveData: LiveData<Double> = luminosityMutableLiveData

    val luminosityAnalyzer = LuminosityAnalyzer { luminosity ->
        // メインスレッドで値を入れるため postValue
        luminosityMutableLiveData.postValue(luminosity)
    }

    val faceAnalyzer = FaceAnalyzer { faces ->
        // メインスレッドで値を入れるため postValue
        faceMutableLiveData.postValue(faces)
    }

    fun saveCurrentPage(value: Int) {
        currentPage = value
    }
}