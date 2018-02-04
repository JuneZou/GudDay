package com.june.gudday.view.homebanner

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.june.gudday.app.App
import com.june.gudday.R
import com.june.gudday.mvp.model.bean.WeatherBean
import com.june.gudday.utils.LogUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.weather_banner_layout.view.*

/**
 * Created by June on 2017/10/09.
 * Email:upupupgoing@126.com
 */
class WeatherDesLayout : LinearLayout {

    constructor(context: Context?) : super(context) {
        LayoutInflater.from(context).inflate(R.layout.weather_banner_layout, this, true)

        (context?.applicationContext as App).locationservice.observable
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    LogUtils.e("address: ${it.addrStr}, city: ${it.city}")
                    weather_banner_city.text = it.district?.toString().plus(it.street)
                }
    }

    fun updateWeatherData(weatherBean: WeatherBean) {
        weather_banner_des.text = weatherBean.HeWeather5[0].now.cond.txt
        weather_banner_quality.text = context.getString(R.string.weather_quality_des,
                weatherBean.HeWeather5[0].aqi.city.qlty,
                weatherBean.HeWeather5[0].aqi.city.pm25)

        weather_banner_current_tmp.text = context.getString(R.string.weather_tmp, weatherBean.HeWeather5[0].now.tmp)
        weather_banner_tmp_interval.text = context.getString(R.string.weather_tmp_interval,
                weatherBean.HeWeather5[0].daily_forecast[0].tmp.max,
                weatherBean.HeWeather5[0].daily_forecast[0].tmp.min)
    }


}