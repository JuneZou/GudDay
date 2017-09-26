package com.june.gudday.mvp.model

import com.june.gudday.http.ApiController
import com.june.gudday.io_to_main
import com.june.gudday.mvp.model.bean.WeatherBean
import io.reactivex.Observable

/**
 * Created by June on 2017/09/26.
 * Email:upupupgoing@126.com
 */
class WeatherModel {
    fun LoadData(): Observable<WeatherBean>{
        return ApiController.weatherService.getWeatherData().io_to_main()
    }
}