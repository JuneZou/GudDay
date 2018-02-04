package com.june.gudday.view.autoflow

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import com.june.gudday.R

/**
 * Created by June on 2017/11/28.
 * Email:upupupgoing@126.com
 */
class AutoFlowTextView : TextView, OnTouchListener {
    val TAG = "AutoFlowTextView"

    //use to mark is click in icon or other
    private var isClickDelete = false

    var canShow = false

    //icon property
    private var mIconWidth = 0f
    private var mIconHeight = 0f
    private var mIconPaintSize = 0f
    private var mIconColor: Int = Color.BLACK

    private var mPaint = Paint()

    constructor(context: Context?) : super(context) {
        initListener()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initListener()
        initAttr(attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initListener()
        initAttr(attrs)
    }

    private fun initAttr(attrs: AttributeSet?) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.AutoFlowTextView)

        mIconWidth = typeArray.getDimension(R.styleable.AutoFlowTextView_icon_width, 0f)
        mIconHeight = typeArray.getDimension(R.styleable.AutoFlowTextView_icon_height, 0f)
        mIconColor = typeArray.getColor(R.styleable.AutoFlowTextView_icon_color, Color.BLACK)
        mIconPaintSize = typeArray.getDimension(R.styleable.AutoFlowTextView_icon_paintSize, 1f)

        typeArray.recycle()

        mPaint.color = mIconColor
        mPaint.strokeWidth = mIconPaintSize
        mPaint.style = Paint.Style.FILL
    }

    private fun initListener() {
        setOnTouchListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        isClickDelete = !(event.x > mIconWidth && event.y > mIconHeight)
        return false
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canShow) {
            canvas?.drawLine(0f, 0f , mIconWidth, mIconHeight, mPaint)
            canvas?.drawLine(mIconWidth, 0f , 0f, mIconHeight, mPaint)
        }

    }

    fun getIsClickDelete() : Boolean {
        return isClickDelete
    }
}
