package com.gajaharan.krazypingpong.sprites

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import com.gajaharan.krazypingpong.R
import com.gajaharan.krazypingpong.Velocity
import java.util.*

class Ball(
    resources: Resources,
    private val displayWidth: Int,
    private val displayHeight: Int,
    var x: Int = 0,
    var y: Int = 0,
    var width: Int = 0,
    var height: Int = 0
) :
    Sprite {
    private val ball: Bitmap
    private val velocity: Velocity = Velocity(32, 25)

    init {
        width = resources.getDimension(R.dimen.ball_width).toInt()
        height = resources.getDimension(R.dimen.ball_height).toInt()
        val originalBall = BitmapFactory.decodeResource(resources, R.drawable.ball)
        ball = Bitmap.createScaledBitmap(originalBall, width, height, false)
        y = Random().nextInt(displayHeight)
    }

    override fun draw(canvas: Canvas?) {
        x += velocity.x
        y += velocity.y

        if (x >= displayWidth - ball.width) {
            velocity.x *= -1
        }
        if (y >= displayHeight - ball.height || y <= 0) {
            velocity.y *= -1
        }

        canvas?.drawBitmap(ball, x.toFloat(), y.toFloat(), null)
    }

    fun setVelocityX() {
        velocity.x *= -1
    }

    fun setVelocityY() {
        velocity.y += 1
    }

    fun setVelocity(x: Int, y: Int) {
        velocity.x = x
        velocity.y = y
    }

    override fun getRectangle() = Rect(x, y, x + width, y + height)
}