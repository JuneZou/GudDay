package com.june.gudday.ui.activity

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import com.june.gudday.R
import com.june.gudday.utils.StatusBarUtil
import kotlinx.android.synthetic.main.weather_main_layout.*

/**
 * Created by June on 2017/10/10.
 * Email:upupupgoing@126.com
 */
class WeatherMainActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.transparentStatusBar(this)

        setContentView(R.layout.weather_main_layout)

        weather_main_refresh_root.init(weather_main_refresh_item as LinearLayout)
    }
}