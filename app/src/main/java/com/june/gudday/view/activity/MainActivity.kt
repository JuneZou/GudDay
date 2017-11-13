package com.june.gudday.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.baidu.location.BDLocation
import com.june.gudday.app.App
import com.june.gudday.R
import com.june.gudday.location.location.LocationListener
import com.june.gudday.mvp.contract.WeatherContract
import com.june.gudday.mvp.model.bean.WeatherBean
import com.june.gudday.mvp.presenter.HomePresenter
import com.june.gudday.view.homebanner.HomeBanner
import com.june.gudday.utils.LogUtils
import com.june.gudday.utils.StatusBarUtil
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity(), WeatherContract.IView, LocationListener {

    val homePresenter: HomePresenter by lazy { HomePresenter(this)}
    val homeBanner: HomeBanner by lazy { HomeBanner(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.transparentStatusBar(this)

        root.addView(homeBanner)

        homeBanner.setOnClickListener {
            startActivity(Intent(this, WeatherMainActivity::class.java))
        }

        (application as App).locationservice.observable
                .observeOn(Schedulers.io())
                .subscribe {
                    LogUtils.e("address: ${it.addrStr}, city: ${it.city}")
                    homePresenter.requestData(it.city) }

        (application as App).locationservice.registerDefaultListener().start()
    }

    override fun onDataLoad(weatherBean: WeatherBean) {
        homeBanner.updateWeatherData(weatherBean)
    }

    override fun onError() {

    }

    override fun onLocationComplete(location: BDLocation?) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
