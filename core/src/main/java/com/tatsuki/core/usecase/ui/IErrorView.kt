package com.tatsuki.core.usecase.ui

interface IErrorView {
    fun showError(e: Exception)
    fun showError(code: Int?, message: String?)
    fun showInternalServerError()
    fun showNetworkError()
}