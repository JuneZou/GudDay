package com.june.gudday.mvp.contract

import com.june.gudday.mvp.base.BasePresenter
import com.june.gudday.mvp.base.BaseView

/**
 * Created by June on 2017/09/18.
 * Email:upupupgoing@126.com
 */
interface WeatherContract {

    interface IView : BaseView<BasePresenter> {

        fun loadData()
        fun onError()

    }

    interface IPresenter: BasePresenter {



    }

}