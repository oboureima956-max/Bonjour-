package com.neuralos.app.ai

import android.content.Context

class TFLiteEngine(private val context: Context) {

    fun infer(input: FloatArray): FloatArray {
        return floatArrayOf(
            input.average().toFloat(),
            1f - input.average().toFloat(),
            input[0]
        )
    }
}