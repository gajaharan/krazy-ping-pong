package com.gajaharan.krazypingpong.sprites

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import com.gajaharan.krazypingpong.R
import java.util.*

class Box(resources: Resources, displayWidth: Int, displayHeight: Int) : Sprite {
    private var boxWidth:Int = resources.getDimension(R.dimen.box_width).toInt()
    private var boxHeight:Int = resources.getDimension(R.dimen.box_height).toInt()
    private val boxPoint: Point =
        Point(Random().nextInt(displayWidth), Random().nextInt(displayHeight))
    private var randomBoxColor: Triple<Int, Int, Int> =
        Triple(Random().nextInt(256), Random().nextInt(256), Random().nextInt(256))

    override fun draw(canvas: Canvas?) {
        val boxPaint = Paint()
        boxPaint.setARGB(
            255,
            randomBoxColor.first,
            randomBoxColor.second,
            randomBoxColor.third
        )

        canvas?.drawRect(Rect(boxPoint.x, boxPoint.y, boxPoint.x + boxWidth, boxPoint.y + boxHeight), boxPaint)
    }
}