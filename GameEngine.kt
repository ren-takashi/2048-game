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
    private var isPlaying = false
    private val dataManager = DataManager(context)

    // Game Objects
    private var playerX = 500f
    private var playerY = 1500f
    private var targetX = 500f
    private var targetY = 1500f
    
    // Paints for glowing effects (Replacing CSS drop-shadow)
    private val playerPaint = Paint().apply {
        color = Color.WHITE
        setShadowLayer(30f, 0f, 0f, Color.CYAN) // Neon Glow
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
            draw()
            sleep()
        }
    }

    private fun update() {
        // Player smoothly moves towards touch position (Lerp)
        playerX += (targetX - playerX) * 0.1f
        playerY += (targetY - playerY) * 0.1f

        // Collision logic with Orb (Coin)
        val dist = Math.hypot((playerX - orbX).toDouble(), (playerY - orbY).toDouble())
        if (dist < 50) { // Collision detected
            dataManager.coins += 1 // Update unified DB
            // Respawn Orb
            orbX = (100..800).random().toFloat()
            orbY = (200..1800).random().toFloat()
        }
    }

    private fun draw() {
        if (holder.surface.isValid) {
            val canvas: Canvas = holder.lockCanvas()
            
            // Draw Background
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)
            
            // Draw Orb
            canvas.drawCircle(orbX, orbY, 20f, orbPaint)
            
            // Draw Player
            canvas.drawCircle(playerX, playerY, 30f, playerPaint)
            
            holder.unlockCanvasAndPost(canvas)
        }
    }

    // Capture Touch Input
    override fun onTouchEvent(event: MotionEvent): Boolean {
        targetX = event.x
        targetY = event.y
        return true
    }

    private fun sleep() {
        try { Thread.sleep(16) } catch (e: Exception) {} // Target ~60FPS
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
