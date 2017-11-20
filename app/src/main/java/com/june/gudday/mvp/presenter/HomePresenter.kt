package com.june.gudday.mvp.presenter

import com.june.gudday.mvp.contract.WeatherContract
import com.june.gudday.mvp.model.WeatherModel

/**
 * Created by June on 2017/09/28.
 * Email:upupupgoing@126.com
 */
class HomePresenter(var view: WeatherContract.IView?) : WeatherContract.IPresenter {

    private val weatherModel: WeatherModel by lazy { WeatherModel() }

    override fun requestData(address: String) {
        weatherModel.LoadData(address)
                .subscribe {
                    view?.onDataLoad(it)
                }
    }

    override fun requestCityList() {

    }

    override fun detachView() {
        view = null
    }

}