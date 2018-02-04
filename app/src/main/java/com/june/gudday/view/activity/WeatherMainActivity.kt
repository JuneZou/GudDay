package com.june.gudday.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.widget.LinearLayout
import com.june.gudday.R
import com.june.gudday.adapter.WeatherCityAdapter
import com.june.gudday.utils.StatusBarUtil
import kotlinx.android.synthetic.main.weather_main_bottom.*
import kotlinx.android.synthetic.main.weather_main_layout.*

/**
 * Created by June on 2017/10/10.
 * Email:upupupgoing@126.com
 */
class WeatherMainActivity : FragmentActivity(){

    private val mCityList = ArrayList<Fragment>()
    private val mWeatherCityAdapter: WeatherCityAdapter by lazy { WeatherCityAdapter(mCityList, supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.transparentStatusBar(this)

        setContentView(R.layout.weather_main_layout)

        weather_main_refresh_root.init(weather_main_refresh_item as LinearLayout)
        weather_main_cities.adapter = mWeatherCityAdapter

        initListenr()
    }

    private fun initListenr() {

        weather_bottom_add_city.setOnClickListener{

//            val tempView = WeatherCityFragment()
//            tempView.setInfo(WeatherPagerInfo(mCityList.size - 1))
//            mCityList.add(tempView)
//
//            mWeatherCityAdapter.notifyDataSetChanged()
            startActivity(Intent(this, CityManagerActivity::class.java))

        }

    }

}