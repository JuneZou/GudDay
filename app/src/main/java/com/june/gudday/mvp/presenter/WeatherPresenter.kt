package com.june.gudday.mvp.presenter

import android.util.Log
import com.june.gudday.mvp.contract.WeatherContract
import com.june.gudday.mvp.model.WeatherModel
import com.june.gudday.utils.LogUtils

/**
 * Created by June on 2017/09/26.
 * Email:upupupgoing@126.com
 */
class WeatherPresenter(var view: WeatherContract.IView) : WeatherContract.IPresenter {

    private val weatherModel: WeatherModel by lazy { WeatherModel() }

    override fun requestData(address: String) {

        weatherModel.LoadData(address)
                .subscribe( {
                    view.onDataLoad(it)
                }, {
                    view.onError()
                    it.printStackTrace()
                    LogUtils.e("error")
                })

    }

    override fun requestCityList() {
    }
}