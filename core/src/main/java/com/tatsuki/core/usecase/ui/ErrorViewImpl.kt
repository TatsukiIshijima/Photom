package com.tatsuki.core.usecase.ui

import javax.inject.Inject

class ErrorViewImpl @Inject constructor(
) : IErrorView {

    override fun showError(e: Exception) {

    }

    override fun showError(code: Int?, message: String?) {

    }

    override fun showInternalServerError() {

    }

    override fun showNetworkError() {

    }
}