package com.june.gudday.app

import android.app.Application
import com.june.gudday.location.location.LocationManager
import com.june.gudday.location.location.LocationService
import com.june.gudday.utils.DisplayManager

/**
 * Created by June on 2017/09/19.
 * Email:upupupgoing@126.com
 */
class App : Application() {

    lateinit var locationservice: LocationService

    override fun onCreate() {
        super.onCreate()
        DisplayManager.init(this)
        locationservice = LocationManager.init(this)
    }
}