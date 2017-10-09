package com.june.gudday.utils

import android.util.Log

/**
 * Created by June on 2017/09/26.
 * Email:upupupgoing@126.com
 */
class LogUtils {


    companion object {
        val PACKAGE_NAME = "GudDay"

        var DEBUG_OPEN = true

        fun e(log: String?) {
            if (DEBUG_OPEN) {
                Log.e(PACKAGE_NAME, log ?: "")
            }
        }

        fun debug(log: String?) {
            if (DEBUG_OPEN) {
                Log.e(PACKAGE_NAME, log ?: "")
            }
        }

    }


}