package com.june.gudday.http.service

import com.june.gudday.mvp.model.bean.WeatherBean
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET

/**
 * Created by June on 2017/09/21.
 * Email:upupupgoing@126.com
 */
interface WeatherService {

    @GET("weather?city=chengdu&key=c83259c51b6c463cbda596190784564d")
    fun getWeatherData() : Observable<WeatherBean>

    @GET("weather?city=chengdu&key=c83259c51b6c463cbda596190784564d")
    fun getWeatherData2() : Call<Result<WeatherBean>>

    @GET("weather?city=chengdu&key=c83259c51b6c463cbda596190784564d")
    fun getWeatherData3() : Call<ResponseBody>


}