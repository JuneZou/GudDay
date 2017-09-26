package com.june.gudday.http

import android.util.Log
import com.june.gudday.http.service.EyeService
import com.june.gudday.http.service.WeatherService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.google.gson.GsonBuilder
import com.google.gson.Gson



/**
 * Created by June on 2017/09/19.
 * Email:upupupgoing@126.com
 */
object ApiController {

    private val weatherRetrofit: Retrofit
    private val okHttpClient: OkHttpClient
    private val DEFAULT_TIMEOUT = 5L

    private val WEATHER_BASEURL = "https://free-api.heweather.com/v5/"

    init {
        val longging = Interceptor { chain ->
            val request = chain.request()
            Log.d("okhttp", "okhttp--->" + request.url().toString())
            chain.proceed(request)
        }

        okHttpClient = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(longging)
                .build()

        val gson = GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create()

        weatherRetrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(WEATHER_BASEURL)
                .build()

    }

    val eyeService: EyeService by lazy { EyeService() }

    val weatherService: WeatherService by lazy { weatherRetrofit.create(WeatherService::class.java) }


}