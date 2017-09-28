package com.june.gudday.ui.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.baidu.location.BDLocation
import com.june.gudday.App
import com.june.gudday.R
import com.june.gudday.http.ApiController
import com.june.gudday.location.location.LocationListener
import com.june.gudday.mvp.contract.WeatherContract
import com.june.gudday.mvp.model.bean.WeatherBean
import com.june.gudday.mvp.presenter.HomePresenter
import com.june.gudday.mvp.presenter.WeatherPresenter
import com.june.gudday.ui.homebanner.HomeBanner
import com.june.gudday.utils.LogUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), WeatherContract.IView, LocationListener {

    val homePresenter: HomePresenter by lazy { HomePresenter(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        root.addView(HomeBanner(this))

        (application as App).locationservice.observable
                .observeOn(Schedulers.io())
                .subscribe {
                    LogUtils.e("address: ${it.addrStr}, city: ${it.city}")
                    homePresenter.requestData(it.city) }

        (application as App).locationservice.registerDefaultListener().start()
    }

    override fun onDataLoad(weatherBean: WeatherBean) {
        LogUtils.e("tmp: ${weatherBean.HeWeather5[0].now.tmp}")
    }

    override fun onError() {

    }

    override fun onLocationComplete(location: BDLocation?) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
