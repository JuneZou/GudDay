package com.june.gudday.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Administrator on 2018/1/4.
 */
class WeatherCityAdapter(private var mPageList: ArrayList<Fragment>,
                         private var mFm: FragmentManager) : FragmentPagerAdapter(mFm) {
    override fun getCount(): Int {
        return mPageList.size
    }

    override fun getItem(position: Int): Fragment {
        return mPageList[position]
    }

}