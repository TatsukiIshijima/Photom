package com.tatsuki.photom

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer

typealias LuminosityListener = (luminosity: Double?) -> Unit

/**
 * CameraX を用いた画像処理の動作確認用
 */
class LuminosityAnalyzer(
    private val skipFrame: Int = 30,
    private val luminosityThreshold: Int = 50,
    private val listener: LuminosityListener
) : ImageAnalysis.Analyzer {

    private var frame = 0

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        val data = ByteArray(remaining())
        get(data)
        return data
    }

    override fun analyze(image: ImageProxy) {
        if (frame != skipFrame) {
            frame++
        } else {
            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luminosity = pixels.average()
            listener(if (luminosity < luminosityThreshold) luminosity else null)
            frame = 0
        }
        image.close()
    }
}