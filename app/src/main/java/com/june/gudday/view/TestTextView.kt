package com.june.gudday.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView
import com.june.gudday.utils.LogUtils

/**
 * Created by June on 2017/11/13.
 * Email:upupupgoing@126.com
 */
class TestTextView : TextView{
    val TAG = "TestTextView"

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        LogUtils.singleE(TAG, "dispatchTouchEvent", true)
        val consult = super.dispatchTouchEvent(event)
        LogUtils.singleE(TAG, "dispatchTouchEvent, ${consult}", true)
        return consult
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        LogUtils.singleE(TAG, "onTouchEvent", true)
        val consult = super.onTouchEvent(event)
        LogUtils.singleE(TAG, "onTouchEvent, ${consult}", true)
        return consult
    }
}