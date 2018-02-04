package com.june.gudday.utils

import android.content.Context

/**
 * Created by Administrator on 2018/1/10.
 */
class Dp2PxUil {


    companion object {

        fun dp2Px(context: Context, dp: Float) : Float {

            return context.resources.displayMetrics.density * dp

        }

    }
}