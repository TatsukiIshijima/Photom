package com.tatsuki.photom.container

interface FactoryInterface<T> {
    fun create(): T
}