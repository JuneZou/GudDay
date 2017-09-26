package com.june.gudday.mvp.model.bean

/**
 * Created by xuekai on 2017/8/20.
 */
data class WeatherBean(var HeWeather5: List<WeatherNow> = ArrayList()) {
    class WeatherNow(var now: Now = Now())

    class Now(var tmp: String = "")
}

