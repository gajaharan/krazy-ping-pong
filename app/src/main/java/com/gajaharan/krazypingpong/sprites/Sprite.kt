package com.gajaharan.krazypingpong.sprites

import android.graphics.Canvas
import android.graphics.Rect

interface Sprite {
    fun draw(canvas: Canvas?): Unit?
    fun getRectangle(): Rect
}