package com.june.gudday.city

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.june.gudday.R
import com.june.gudday.view.autoflow.AbstractAutoFlowAdapter
import com.june.gudday.view.autoflow.AutoFlowTextView

/**
 * Created by Administrator on 2018/2/1.
 */
class CitySearchHistoryAdapter(private var mContext: Context,
                               private var mCityInfos: ArrayList<CityInfo>) : AbstractAutoFlowAdapter() {

    override fun getCount(): Int {
        return mCityInfos.size
    }

    override fun getView(position: Int, parent: ViewGroup): View {

        val view = LayoutInflater.from(mContext).inflate(R.layout.auto_flow_textview_layout, parent, false) as AutoFlowTextView
        view.text = mCityInfos[position].mCityName
        return view

    }
}