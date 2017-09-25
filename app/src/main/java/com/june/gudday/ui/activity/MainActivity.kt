package com.june.gudday.ui.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.june.gudday.R
import com.june.gudday.http.ApiController
import com.june.gudday.http.WeatherBean
import com.june.gudday.ui.homebanner.HomeBanner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        root.addView(HomeBanner(this))

        ApiController.weatherService.getWeatherData2().enqueue(object : Callback<WeatherBean> {
            override fun onFailure(call: Call<WeatherBean>?, t: Throwable?) {
                Log.e("test2", "fail" + "")
            }

            override fun onResponse(call: Call<WeatherBean>?, response: Response<WeatherBean>?) {

                Log.e("test2", response?.body()?.tmp)

            }
        })

        ApiController.weatherService.getWeatherData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("test", it.tmp)
                }, {
                    Log.e("test", "error")
                    it.printStackTrace()
                })


    }
}
