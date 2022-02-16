package com.gajaharan.krazypingpong

import android.graphics.Canvas
import android.view.SurfaceHolder
import java.lang.Exception

class GameEngine(var surfaceHolder: SurfaceHolder, val gameManager: GameManager) : Thread() {
    private var running = false
    private var canvas: Canvas? = null
    private val targetFPS = 1f

    fun setRunning(isRunning: Boolean) {
        running = isRunning
    }

    override fun run() {
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long
        var targetTime: Long

        while (running) {
            targetTime = (1000 / targetFPS).toLong()
            startTime = System.nanoTime()
            canvas = null

            try {
                canvas = surfaceHolder?.lockCanvas()
                surfaceHolder?.let {
                    synchronized(it) {
                        gameManager.draw(canvas)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder?.unlockCanvasAndPost(canvas)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis

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
