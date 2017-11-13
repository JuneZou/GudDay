package com.june.gudday.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView

/**
 * Created by June on 2017/10/20.
 * Email:upupupgoing@126.com
 */
class WeatherCityLayout : ScrollView{
    val TAG = "WeatherCityLayout"

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    override fun onFinishInflate() {
        super.onFinishInflate()
    }
}