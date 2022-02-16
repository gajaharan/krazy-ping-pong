package com.gajaharan.krazypingpong.sprites

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.gajaharan.krazypingpong.R
import com.gajaharan.krazypingpong.Velocity
import java.util.*

class Ball(resources: Resources, private val displayWidth: Int, private val displayHeight: Int) : Sprite {
    private val ball: Bitmap
    private var ballX: Float = 0f
    private var ballY: Float = 0f
    private var ballWidth:Int = 0
    private var ballHeight:Int = 0
    private val velocity: Velocity = Velocity(32, 25)

    init {
        ballWidth = resources.getDimension(R.dimen.ball_width).toInt()
        ballHeight = resources.getDimension(R.dimen.ball_height).toInt()
        val originalBall = BitmapFactory.decodeResource(resources, R.drawable.ball)
        ball = Bitmap.createScaledBitmap(originalBall, ballWidth, ballHeight, false)
        ballY = Random().nextInt(displayHeight).toFloat()
    }

    override fun draw(canvas: Canvas?) {
        ballX += velocity.x
        ballY += velocity.y

        if (ballX >= displayWidth - ball.width || ballX <= 0) {
            velocity.x *=  -1
        }
        if (ballY >= displayHeight - ball.height || ballY <= 0) {
            velocity.y *= -1
        }

        canvas?.drawBitmap(ball, ballX, ballY, null)
    }
}