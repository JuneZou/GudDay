package com.june.gudday.location.location

import android.content.Context

/**
 * Created by June on 2017/08/18.
 */
class LocationManager {

    companion object {

        var isInit = false

        fun init(context: Context) : LocationService {

            if (!isInit) {
                isInit = true
//                SDKInitializer.initialize(context)
                return LocationService(context)
            } else {
                throw Exception("已经初始化")
            }

        }

    }


}