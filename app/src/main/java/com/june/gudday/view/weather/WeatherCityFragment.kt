package com.june.gudday.view.weather

import android.content.pm.PackageInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.june.gudday.R
import com.june.gudday.bean.WeatherPagerInfo

/**
 * Created by Administrator on 2018/1/4.
 */
class WeatherCityFragment : Fragment() {

    private lateinit var mCityLayout: WeatherCityLayout
    private var mInfo: WeatherPagerInfo? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val temp = inflater.inflate(R.layout.page_index_layout, container, false)
        initView(temp)

        return temp
    }

    private fun initView(view: View) {

        mCityLayout = view.findViewById(R.id.test_layout)

        if (mInfo!= null)
        mCityLayout.setPageInfo(mInfo!!)
    }

    fun setInfo(info: WeatherPagerInfo) {
        mInfo = info
    }
}