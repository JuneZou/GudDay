package com.june.gudday.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * Created by June on 2017/10/20.
 * Email:upupupgoing@126.com
 */
class FiniTest : LinearLayout {
    val TAG = "FiniTest"

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

}