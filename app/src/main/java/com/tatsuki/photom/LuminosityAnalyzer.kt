package com.tatsuki.photom

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer

typealias LuminosityListener = (Luminosity: Double) -> Unit

/**
 * CameraX を用いた画像処理の動作確認用
 */
class LuminosityAnalyzer(
    private val listener: LuminosityListener
) : ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        val data = ByteArray(remaining())
        get(data)
        return data
    }

    override fun analyze(image: ImageProxy) {
        val buffer = image.planes[0].buffer
        val data = buffer.toByteArray()
        val pixels = data.map { it.toInt() and 0xFF }
        val luminosity = pixels.average()

        listener(luminosity)

        image.close()
    }
}