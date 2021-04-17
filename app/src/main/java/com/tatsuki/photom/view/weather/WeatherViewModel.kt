package com.tatsuki.photom.view.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor() : ViewModel() {

    private var job: Job? = null

    private val _autoTransitionMutableLiveData = MutableLiveData<Unit>()

    val autoTransitionLiveData: LiveData<Unit> = _autoTransitionMutableLiveData

    fun startAutoTransitionTimer() {
        job = viewModelScope.launch {
            withContext(Dispatchers.Main) {

                delay(5000)

                if (!isActive) {
                    return@withContext
                }

                Timber.d("observeTouchEvent!!")

                _autoTransitionMutableLiveData.value = Unit
            }
        }

    }

    fun stopAutoTransitionTimer() {
        job?.cancel()
    }
}