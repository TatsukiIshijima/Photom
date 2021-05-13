package com.tatsuki.photom

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.Task

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import timber.log.Timber

typealias DetectListener = (faces: MutableList<Face>) -> Unit

class FaceAnalyzer(
    private val listener: DetectListener
) : ImageAnalysis.Analyzer {

    private fun detectFace(image: InputImage): Task<MutableList<Face>> {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
            .setMinFaceSize(0.1f)
            .build()

        val detector = FaceDetection.getClient(options)

        return detector.process(image)
    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        mediaImage?.let {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            detectFace(image)
                .addOnSuccessListener { faces ->
                    listener(faces)
                }
                .addOnFailureListener { e ->
                    Timber.e("detect faces failed: ${e.localizedMessage}")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}