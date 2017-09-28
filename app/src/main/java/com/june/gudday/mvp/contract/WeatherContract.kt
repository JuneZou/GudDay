package com.june.gudday.mvp.contract

import com.june.gudday.mvp.base.BasePresenter
import com.june.gudday.mvp.base.BaseView
import com.june.gudday.mvp.model.bean.WeatherBean

/**
 * Created by June on 2017/09/18.
 * Email:upupupgoing@126.com
 */
interface WeatherContract {

    interface IView : BaseView<BasePresenter> {
        fun onDataLoad(weatherBean: WeatherBean)
        fun onError()
    }

    interface IPresenter: BasePresenter {
        fun requestData(address: String)
    }
}