package com.gajaharan.krazypingpong.sprites

import android.content.res.Resources
import android.graphics.*
import com.gajaharan.krazypingpong.R

class LeftPaddle(
    resources: Resources,
    displayWidth: Int,
    displayHeight: Int,
    var x: Int = 0,
    var y: Int = 0,
    var width: Int = 0,
    var height: Int = 0
) : Sprite {
    private val paddle: Bitmap

    init {
        width = resources.getDimension(R.dimen.paddle_width).toInt()
        height = resources.getDimension(R.dimen.paddle_height).toInt()
        val oPaddle = BitmapFactory.decodeResource(resources, R.drawable.paddle)
        paddle = Bitmap.createScaledBitmap(oPaddle, width, height, false)
        y = displayHeight / 2 - paddle.height / 2

    }

    override fun draw(canvas: Canvas?): Unit? = canvas?.drawBitmap(paddle, x.toFloat(), y.toFloat(), null)

    override fun getRectangle() = Rect(x, y, x + width, y + height)

}