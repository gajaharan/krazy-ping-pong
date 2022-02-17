package com.gajaharan.krazypingpong

import android.graphics.Canvas
import android.view.SurfaceHolder
import java.lang.Exception

class GameEngine(var surfaceHolder: SurfaceHolder, val gameManager: GameManager) : Thread() {
    private var running = false
    private var canvas: Canvas? = null
    private val targetFPS = 30f

    fun setRunning(isRunning: Boolean) {
        running = isRunning
    }

    override fun run() {
        while (running) {
            val targetTime = (1000 / targetFPS).toLong()
            val startTime = System.nanoTime()
            canvas = null

            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    gameManager.update()
                    gameManager.draw(canvas)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }

            val timeMillis = (System.nanoTime() - startTime) / 1000000
            val waitTime = targetTime - timeMillis

            try {
                if (waitTime > 0) {
                    sleep(waitTime)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}

data class Velocity(var x: Int, var y: Int)