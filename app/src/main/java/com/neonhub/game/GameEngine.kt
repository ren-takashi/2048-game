package com.neonhub.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameEngine(context: Context) : SurfaceView(context), Runnable {
    private var thread: Thread? = null
    @Volatile private var isPlaying = false
    private val dataManager = DataManager(context)

    private var playerX = 500f
    private var playerY = 1500f
    private var targetX = 500f
    private var targetY = 1500f
    
    private val playerPaint = Paint().apply {
        color = Color.WHITE
        setShadowLayer(30f, 0f, 0f, Color.CYAN)
        isAntiAlias = true
    }
    private val bgPaint = Paint().apply { color = Color.parseColor("#050510") }
    private val orbPaint = Paint().apply {
        color = Color.YELLOW
        setShadowLayer(20f, 0f, 0f, Color.YELLOW)
        isAntiAlias = true
    }

    private var orbX = 300f
    private var orbY = 500f

    init {
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) { resume() }
            override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {}
            override fun surfaceDestroyed(holder: SurfaceHolder) { pause() }
        })
    }

    override fun run() {
        while (isPlaying) {
            update()
            render() // FIX: draw() ki jagah render() taaki Android View system se conflict na ho
            sleep()
        }
    }

    private fun update() {
        playerX += (targetX - playerX) * 0.1f
        playerY += (targetY - playerY) * 0.1f

        val dist = Math.hypot((playerX - orbX).toDouble(), (playerY - orbY).toDouble())
        if (dist < 50) {
            dataManager.coins += 1
            orbX = (100..800).random().toFloat()
            orbY = (200..1800).random().toFloat()
        }
    }

    private fun render() {
        if (holder.surface.isValid) {
            // FIX: Null safety add kardi
            val canvas: Canvas = holder.lockCanvas() ?: return
            
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)
            canvas.drawCircle(orbX, orbY, 20f, orbPaint)
            canvas.drawCircle(playerX, playerY, 30f, playerPaint)
            
            holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        targetX = event.x
        targetY = event.y
        return true
    }

    private fun sleep() {
        try { Thread.sleep(16) } catch (e: Exception) {}
    }

    fun resume() {
        isPlaying = true
        thread = Thread(this)
        thread?.start()
    }

    fun pause() {
        isPlaying = false
        thread?.join()
    }
}
