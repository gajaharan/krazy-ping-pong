package com.gajaharan.krazypingpong.sprites

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import com.gajaharan.krazypingpong.R
import java.util.*

class Box(
    resources: Resources,
    private val displayWidth: Int,
    private val displayHeight: Int,
    var x: Int = 0,
    var y: Int = 0,
    var width: Int = 0,
    var height: Int = 0
) : Sprite {
    private var randomBoxColor: Triple<Int, Int, Int> =
        Triple(Random().nextInt(256), Random().nextInt(256), Random().nextInt(256))
    private var boxPoint: Point
    private val boardSize = 20
    private var pointSize = 0f

    init {
        width = resources.getDimension(R.dimen.box_width).toInt()
        height = resources.getDimension(R.dimen.box_height).toInt()
        boxPoint = generateNewPointBox()
        pointSize = displayWidth * 0.9f / boardSize
    }

    override fun draw(canvas: Canvas?) {
        x = boxPoint.x
        y = boxPoint.y

        val boxPaint = Paint()
        boxPaint.setARGB(
            255,
            randomBoxColor.first,
            randomBoxColor.second,
            randomBoxColor.third
        )

        canvas?.drawRect(getRectangle(), boxPaint)
    }

    override fun getRectangle(): Rect {
//        val left = (displayWidth * 0.05f + boxPoint.x * pointSize).toInt()
//        val right = (left + pointSize).toInt()
//        val top = (displayHeight * 0.1f + boxPoint.y * pointSize).toInt()
//        val bottom = (top + pointSize).toInt()

        return Rect(x, y, x + width, y + height)
    }

    fun generateNewPointBox(): Point {
        boxPoint = Point(Random().nextInt((displayWidth * 0.80).toInt()), Random().nextInt((displayHeight * 0.80).toInt()))
        return boxPoint
    }
}