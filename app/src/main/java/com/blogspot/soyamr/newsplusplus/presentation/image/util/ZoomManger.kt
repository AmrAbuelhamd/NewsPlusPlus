package com.blogspot.soyamr.newsplusplus.presentation.image.util

import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import kotlin.math.atan2
import kotlin.math.sqrt


private const val NONE = 0
private const val DRAG = 1
private const val ZOOM = 2

class ZoomManger {
    private var lastEvent: FloatArray? = null
    private var d = 0f
    private var newRot = 0f
    private var isZoomAndRotate = false
    private var isOutSide = false
    private var mode = NONE
    private val start = PointF()
    val mid = PointF()
    private var oldDist = 1f
    private var xCoOrdinate = 0f
    private var yCoOrdinate: Float = 0f


    fun viewTransformation(view: View, event: MotionEvent) {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                xCoOrdinate = view.x - event.rawX
                yCoOrdinate = view.y - event.rawY
                start[event.x] = event.y
                isOutSide = false
                mode = DRAG
                lastEvent = null
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                oldDist = spacing(event).toFloat()
                if (oldDist > 10f) {
                    midPoint(mid, event)
                    mode = ZOOM
                }
                lastEvent = FloatArray(4)
                lastEvent!![0] = event.getX(0)
                lastEvent!![1] = event.getX(1)
                lastEvent!![2] = event.getY(0)
                lastEvent!![3] = event.getY(1)
                d = rotation(event)
            }
            MotionEvent.ACTION_UP -> {
                isZoomAndRotate = false
                isOutSide = true
                mode = NONE
                lastEvent = null
                mode = NONE
                lastEvent = null
            }
            MotionEvent.ACTION_OUTSIDE -> {
                isOutSide = true
                mode = NONE
                lastEvent = null
                mode = NONE
                lastEvent = null
            }
            MotionEvent.ACTION_POINTER_UP -> {
                mode = NONE
                lastEvent = null
            }
            MotionEvent.ACTION_MOVE -> if (!isOutSide) {
                if (mode == DRAG) {
                    isZoomAndRotate = false
                    view.animate().x(event.rawX + xCoOrdinate).y(event.rawY + yCoOrdinate)
                        .setDuration(0).start()
                }
                if (mode == ZOOM && event.pointerCount == 2) {
                    val newDist1 = spacing(event)
                    if (newDist1 > 10f) {
                        val scale: Float = newDist1 / oldDist * view.scaleX
                        this.scale = scale
                        view.scaleX = scale
                        view.scaleY = scale
                    }
                    if (lastEvent != null) {
                        newRot = rotation(event)
                        view.rotation = (view.rotation + (newRot - d))
                    }
                }
            }
        }
    }

    var scale = 0F
        private set;

    private fun rotation(event: MotionEvent): Float {
        val deltaX = (event.getX(0) - event.getX(1)).toDouble()
        val deltaY = (event.getY(0) - event.getY(1)).toDouble()
        val radians = atan2(deltaY, deltaX)
        return Math.toDegrees(radians).toFloat()
    }

    private fun spacing(event: MotionEvent): Int {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt((x * x + y * y).toDouble()).toInt()
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point[x / 2] = y / 2
    }
}