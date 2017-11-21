package com.june.gudday.view.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.june.gudday.R

/**
 * Author: zhouchenguang
 * Date:   17-3-13.
 */

class CircleView : View {

    private var fillArcPaint: Paint? = null

    private var oval: RectF? = null
    private var cirqueWidth: Float = 0f
    private var startAngle = 0f
    private var crossAngle = 1f

    private var mCircleWidth = 0f
    private var mCircleHeight = 0f

    private var mWidth = 0
    private var mHeight = 0

    private var type: Int = 0

    var isCanDrawCircle = false
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)

    }

    private fun init(attrs: AttributeSet) {
        fillArcPaint = Paint()
        fillArcPaint!!.isAntiAlias = true
        fillArcPaint!!.flags = Paint.ANTI_ALIAS_FLAG
        fillArcPaint!!.style = Paint.Style.STROKE
        fillArcPaint!!.isDither = true
        fillArcPaint!!.strokeJoin = Paint.Join.ROUND
        oval = RectF()
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.circleView)

        if (typeArray.hasValue(R.styleable.circleView_paintColor)) {
            fillArcPaint!!.color = typeArray.getColor(R.styleable.circleView_paintColor, Color.BLUE)
        }

        cirqueWidth = typeArray.getDimension(R.styleable.circleView_circleSize, 5f)

        mCircleWidth = typeArray.getDimension(R.styleable.circleView_circleWidth, 40f)

        mCircleHeight = typeArray.getDimension(R.styleable.circleView_circleWidth, 40f)

        typeArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = MeasureSpec.getSize(widthMeasureSpec)
        } else if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = mCircleWidth.toInt()
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = MeasureSpec.getSize(heightMeasureSpec)
        } else if (heightMode == MeasureSpec.AT_MOST) {
            mHeight = mCircleHeight.toInt()
        }

        Log.e("test", "m:$mCircleWidth, $mHeight")
        setMeasuredDimension(mWidth, mHeight)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val height = mCircleWidth
        val width = mCircleHeight

        val radius = width / 2 - cirqueWidth

        canvas.translate(mWidth / 2.toFloat() - width / 2, mHeight / 2.toFloat() - width / 2)

        fillArcPaint!!.strokeCap = Paint.Cap.ROUND

        fillArcPaint!!.strokeWidth = cirqueWidth

        oval!!.set((width / 2 - radius).toFloat(), (height / 2 - radius).toFloat(), (width / 2 + radius).toFloat(), (height / 2 + radius).toFloat())

        if (!isCanDrawCircle) {
            canvas.drawArc(oval, 0f, 360f, false, fillArcPaint)
            return
        }


        canvas.drawArc(oval!!, startAngle, crossAngle, false, fillArcPaint!!)

        if (crossAngle > MAX_ANGLE) {
            type = COUNTERCLOCKWISE
        }

        if (crossAngle < MIN_ANHLE) {
            type = CLOCKWISE
        }

        if (type == CLOCKWISE) {
            startAngle += 3
            crossAngle += 15
        } else if (type == COUNTERCLOCKWISE) {
            startAngle += 12
            crossAngle -= 8
        }

        if (startAngle > CIRQUE_ANGLE) {
            startAngle -= CIRQUE_ANGLE
        }
//        postInvalidateDelayed(DEPLOY.toLong())
        invalidate()
    }


    private fun reSet() {
        startAngle = 0f
        crossAngle = 1f
        type = CLOCKWISE
    }

    companion object {

        private val MAX_ANGLE = 350
        private val MIN_ANHLE = 10

        private val CIRQUE_ANGLE = 360

        private val CLOCKWISE = 1
        private val COUNTERCLOCKWISE = 0
        private val DEPLOY = 10
    }
}