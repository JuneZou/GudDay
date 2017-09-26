package com.june.gudday.ui.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.june.gudday.App
import com.june.gudday.R
import com.june.gudday.http.ApiController
import com.june.gudday.mvp.contract.WeatherContract
import com.june.gudday.mvp.model.bean.WeatherBean
import com.june.gudday.ui.homebanner.HomeBanner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), WeatherContract.IView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        root.addView(HomeBanner(this))

        ApiController.weatherService.getWeatherData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("test", it.HeWeather5[0].now.tmp)
                }, {
                    Log.e("test", "error")
                    it.printStackTrace()
                })

        (application as App).locationservice.registerDefaultListener().start()
    }

    override fun loadData(weatherBean: WeatherBean) {

    }

    override fun onError() {

    }
}
