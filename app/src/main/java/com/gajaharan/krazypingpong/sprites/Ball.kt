package com.gajaharan.krazypingpong.sprites

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import com.gajaharan.krazypingpong.R
import com.gajaharan.krazypingpong.Velocity
import java.util.*

class Ball(
    resources: Resources,
    private val displayWidth: Int,
    private val displayHeight: Int,
    var x: Int = 0,
    var y: Int = 0
) :
    Sprite {
    private val ball: Bitmap
    private var ballWidth: Int = 0
    private var ballHeight: Int = 0
    private val velocity: Velocity = Velocity(32, 25)

    init {
        ballWidth = resources.getDimension(R.dimen.ball_width).toInt()
        ballHeight = resources.getDimension(R.dimen.ball_height).toInt()
        val originalBall = BitmapFactory.decodeResource(resources, R.drawable.ball)
        ball = Bitmap.createScaledBitmap(originalBall, ballWidth, ballHeight, false)
        y = Random().nextInt(displayHeight)
    }

    override fun draw(canvas: Canvas?) {
        x += velocity.x
        y += velocity.y

        if (x >= displayWidth - ball.width || x <= 0) {
            velocity.x *= -1
        }
        if (y >= displayHeight - ball.height || y <= 0) {
            velocity.y *= -1
        }

        canvas?.drawBitmap(ball, x.toFloat(), y.toFloat(), null)
    }

    override fun getRectangle() = Rect(x, y, x + ballWidth, y + ballHeight)
}