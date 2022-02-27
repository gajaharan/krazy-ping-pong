package com.gajaharan.krazypingpong.sprites

import android.content.res.Resources
import android.graphics.*
import com.gajaharan.krazypingpong.R
import com.gajaharan.krazypingpong.sprites.Sprite.Companion.TEXT_SIZE


class RightPaddle(
    resources: Resources,
    val displayWidth: Int,
    val displayHeight: Int,
    var x: Int = 0,
    var y: Int = 0,
    var width: Int = 0,
    var height: Int = 0,
    var score: Int = 0
) : Sprite {
    private val paddle: Bitmap
    private val scorePaint = Paint()

    init {
        width = resources.getDimension(R.dimen.paddle_width).toInt()
        height = resources.getDimension(R.dimen.paddle_height).toInt()
        val oPaddle = BitmapFactory.decodeResource(resources, R.drawable.paddle)
        paddle = Bitmap.createScaledBitmap(oPaddle, width, height, false)
        y = displayHeight / 2 - paddle.height / 2
        x = displayWidth - paddle.width

        scorePaint.color = Color.WHITE
        scorePaint.textSize = TEXT_SIZE
        scorePaint.textAlign = Paint.Align.RIGHT
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(paddle, x.toFloat(), y.toFloat(), null)
        canvas?.drawText("$score", displayWidth - TEXT_SIZE, TEXT_SIZE, scorePaint)
    }

    override fun getRectangle() = Rect(x, y, x + width, y + height)

    fun movePaddle(ball: Ball) {
        when {
            (ball.y <= 0) && (ball.x > displayWidth / 2) -> y = ball.y
            (ball.y >= displayHeight - height) && (ball.x > displayWidth / 2) -> y = displayHeight - height
            (ball.y > ball.y - ball.velocity.y) && (ball.x < displayWidth / 2) -> y += 15
            (ball.y == ball.y - ball.velocity.y) -> y = ball.y
            (ball.y < ball.y - ball.velocity.y) && (ball.x < displayWidth / 2) -> y -= 15
            else -> y = ball.y
        }
    }
}