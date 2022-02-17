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
    var y: Int = 0
) : Sprite {
    private var boxWidth: Int = resources.getDimension(R.dimen.box_width).toInt()
    private var boxHeight: Int = resources.getDimension(R.dimen.box_height).toInt()
    private var randomBoxColor: Triple<Int, Int, Int> =
        Triple(Random().nextInt(256), Random().nextInt(256), Random().nextInt(256))
    private var boxPoint: Point

    init {
        boxPoint = generateNewPointBox()
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

    override fun getRectangle(): Rect = Rect(x, y, x + boxWidth, y + boxHeight)

    fun generateNewPointBox(): Point {
        boxPoint = Point(Random().nextInt(displayWidth / 2), Random().nextInt(displayHeight / 2))
        return boxPoint
    }
}