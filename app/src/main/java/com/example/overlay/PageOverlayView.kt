package com.example.overlay

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.graphics.withClip

class PageOverlayView constructor(context: Context) : View(context) {

    var overlay: Bitmap? = null
        set(value) {
            field?.recycle()
            field = value
        }
    var currentPosition: Int = -1
    var currentPositionOffsetPx: Int = 0
        set(value) {
            field = value
            invalidate()
        }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val bitmap = overlay ?: return
        canvas?.withClip(left, top, width - currentPositionOffsetPx, bottom) {
            drawBitmap(bitmap, 0f, 0f, null)
        }
    }
}