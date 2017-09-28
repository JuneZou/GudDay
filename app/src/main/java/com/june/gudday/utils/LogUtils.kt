package com.june.gudday.utils

import android.util.Log

/**
 * Created by June on 2017/09/26.
 * Email:upupupgoing@126.com
 */
class LogUtils {


    companion object {
        val PACKAGE_NAME = "GudDay"

        fun e(log: String?) {
            Log.e(PACKAGE_NAME, log ?: "")
        }

    }

}