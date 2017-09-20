package com.june.gudday

import android.app.Application
import com.june.gudday.utils.DisplayManager

/**
 * Created by June on 2017/09/19.
 * Email:upupupgoing@126.com
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DisplayManager.init(this)
    }
}