package com.june.gudday.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import com.june.gudday.utils.LogUtils

/**
 * Created by June on 2017/11/13.
 * Email:upupupgoing@126.com
 */
class TestViewPager : ViewPager {
    val TAG = "TestViewPager"

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val reult = super.dispatchTouchEvent(ev)
        LogUtils.singleE(TAG, "dispatchTouchEvent, ${reult}", true)
        return reult
    }
}