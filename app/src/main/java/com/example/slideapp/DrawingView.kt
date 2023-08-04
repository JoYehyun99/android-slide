package com.example.slideapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.slideapp.ColorUtil.getRandomColor

class DrawingView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var drawPaint = Paint()
    private lateinit var nowSlide: DrawingSlide
    private var rectangle = RectF()

    private val rectPaint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    init {
        context?.theme?.obtainStyledAttributes(
            attrs, R.styleable.DrawingView, 0, 0
        )
    }

    fun setSlide(slide: DrawingSlide) {
        nowSlide = slide
        rectangle = RectF(slide.border[0], slide.border[1], slide.border[2], slide.border[3])
        slide.path = slide.path ?: Path()
        drawPaint.color = Color.parseColor(slide.color.getHexRGBColor())
        drawPaint.isAntiAlias = true
        drawPaint.style = Paint.Style.STROKE
        drawPaint.strokeWidth = 5f
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return true
        val x = event.x
        val y = event.y

        if (nowSlide.isDrawable) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    nowSlide.path?.moveTo(x, y)
                    nowSlide.border[0] = x
                    nowSlide.border[1] = y
                    nowSlide.border[2] = x
                    nowSlide.border[3] = y
                }

                MotionEvent.ACTION_MOVE -> {
                    nowSlide.path?.lineTo(x, y)
                    //path.lineTo(x, y)
                    nowSlide.border[0] = minOf(nowSlide.border[0], x) // min x
                    nowSlide.border[1] = maxOf(nowSlide.border[1], y) // max y
                    nowSlide.border[2] = maxOf(nowSlide.border[2], x) // max x
                    nowSlide.border[3] = minOf(nowSlide.border[3], y) // min y
                }

                MotionEvent.ACTION_UP -> {
                    nowSlide.path?.lineTo(x, y)
                    nowSlide.isDrawable = false
                    drawBorder(nowSlide.border)
                }
            }
        } else {

            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    if (rectangle.contains(x, y)) {
                        drawPaint.color = Color.parseColor(getRandomColor().getHexRGBColor())
                    }
                }
            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        nowSlide.path?.let { canvas?.drawPath(it, drawPaint) }
        canvas?.drawRect(rectangle, rectPaint)
        invalidate()
    }

    fun drawBorder(borderPoint: Array<Float>) {
        rectangle = RectF(borderPoint[0], borderPoint[1], borderPoint[2], borderPoint[3])
    }
}