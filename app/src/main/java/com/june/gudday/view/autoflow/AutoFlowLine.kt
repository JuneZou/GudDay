package com.june.gudday.view.autoflow

import android.view.View

/**
 * Created by June on 2017/11/27.
 * Email:upupupgoing@126.com
 */
class AutoFlowLine(private var mMaxWidth: Int,
                   var mLineIndex: Int,
                   private var mSpaceWidth: Float = 0f) {

    val TAG = "AutoFlowLine"
    var mAllocatedWidth = 0f
    var mCount = 0
    var mChildList = ArrayList<View>()
    var mLineMaxHeight = 0

    fun addViewAtLine(view: View): Boolean {
        if (isCanAdd(view)) {
            mChildList.add(view)
            mCount++
            mAllocatedWidth += view.measuredWidth + mSpaceWidth

            //当前行的最大宽度
            if (view.measuredHeight > mLineMaxHeight) {
                mLineMaxHeight = view.measuredHeight
            }
            return true
        }
        return false
    }

    fun removeViewAtLineByPosition(position: Int) {
        mChildList.removeAt(position)
        mCount--
    }

    fun isCanAdd(view: View): Boolean {
        val childWidth = view.measuredWidth

        /*是否可以在当前行添加*/
        if ((mAllocatedWidth + childWidth + mSpaceWidth) > mMaxWidth) {
            return false
        }
        return true
    }
}
