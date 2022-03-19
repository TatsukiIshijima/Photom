package com.tatsuki.photom.view.main

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    var currentPage: Int = 0
        private set

    fun saveCurrentPage(value: Int) {
        currentPage = value
    }
}