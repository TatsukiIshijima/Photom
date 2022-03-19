package com.tatsuki.data.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.*

fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, observe: (value: T) -> Unit) = apply {
    observe(owner, Observer { value ->
        value ?: return@Observer
        observe(value)
    })
}

fun <T> LiveData<T>.observeAtOnce(owner: LifecycleOwner, observe: (value: T) -> Unit) = apply {
    observe(owner, object : Observer<T> {
        override fun onChanged(value: T?) {
            removeObserver(this)
            observeNotNull(owner, observe)
        }
    })
}