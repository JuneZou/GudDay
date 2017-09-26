package com.june.gudday.ui.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.baidu.location.BDLocation
import com.june.gudday.App
import com.june.gudday.R
import com.june.gudday.http.ApiController
import com.june.gudday.location.location.LocationListener
import com.june.gudday.mvp.contract.WeatherContract
import com.june.gudday.mvp.model.bean.WeatherBean
import com.june.gudday.mvp.presenter.WeatherPresenter
import com.june.gudday.ui.homebanner.HomeBanner
import com.june.gudday.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), WeatherContract.IView, LocationListener {

    val weatherPresenter: WeatherPresenter by lazy { WeatherPresenter(this)}

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

        (application as App).locationservice.registerDefaultListener().start().observable
                ?.subscribe {
                    LogUtils.e(it.addrStr)
                }



    }

    override fun loadData(weatherBean: WeatherBean) {

    }

    override fun onError() {

    }

    override fun onLocationComplete(location: BDLocation?) {

    }
}
