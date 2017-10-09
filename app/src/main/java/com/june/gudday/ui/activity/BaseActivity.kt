package com.june.gudday.ui.activity

import android.app.Activity
import android.graphics.Color
import com.june.gudday.utils.StatusBarUtil

/**
 * Created by June on 16/2/14.

 */
open class BaseActivity : Activity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setStatusBar()
        initView()
    }

    protected fun setStatusBar() {
        StatusBarUtil.setTranslucent(this, Color.parseColor("#3F9BE9"))
    }

    open fun initView() {

    }
}
