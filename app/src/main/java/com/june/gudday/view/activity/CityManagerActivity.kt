package com.june.gudday.view.activity

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import com.june.gudday.R
import com.june.gudday.adapter.CityManagerAdapter
import com.june.gudday.city.CityInfo
import com.june.gudday.decoration.CityManagerDecoration
import com.june.gudday.mvp.contract.WeatherContract
import com.june.gudday.mvp.model.bean.WeatherBean
import com.june.gudday.mvp.presenter.HomePresenter
import com.june.gudday.utils.StatusBarUtil
import com.june.gudday.view.help.CityManagerCallBack
import kotlinx.android.synthetic.main.activity_city_manager_main_layout.*
import java.util.*
import kotlin.collections.ArrayList

/**
* Created by Administrator on 2018/1/8.
*/
class CityManagerActivity : Activity(), WeatherContract.IView{


    companion object {

        const val ADD_ANIMATOR_DURATION = 500L

    }
    private val mCities = ArrayList<CityInfo>()
    private val mCitiesWeather = ArrayList<WeatherBean>()

    private val homePresenter: HomePresenter by lazy { HomePresenter(this) }

    private val mCityManagerAdapter: CityManagerAdapter by lazy {
        CityManagerAdapter(this, mCities)
    }

    private val mTouchHelper: ItemTouchHelper by lazy {

        ItemTouchHelper(CityManagerCallBack(object : CityManagerCallBack.OnItemTouchCallbackListener{
            override fun onMove(srcPosition: Int, targetPosition: Int): Boolean {
                Collections.swap(mCities, srcPosition, targetPosition)
                // 更新UI中的Item的位置，主要是给用户看到交互效果
                mCityManagerAdapter.notifyItemMoved(srcPosition, targetPosition)
                return true
            }

            override fun onSwipe() {
            }
        }))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_manager_main_layout)

        StatusBarUtil.transparentStatusBar(this)

        initRecyclerView()

        crateData()

        initListener()

        startAddBtnAnimator()

    }

    private fun startAddBtnAnimator() {

        val height = resources.getDimension(R.dimen.city_add_btn_size)

        val animator = ObjectAnimator.ofFloat(city_add, "translationY", height * 2, 0f)
        animator.duration = ADD_ANIMATOR_DURATION
        animator.start()

    }

    private fun requestAllCityWeather() {

        mCitiesWeather.clear()

        mCities.iterator().forEach {
            homePresenter.requestData(it.mCityName)
        }

    }

    private fun initListener() {

        custom_btn.setOnClickListener {

            mCityManagerAdapter.changeState()

        }

        city_add.setOnClickListener {

            startActivity(Intent(this, CitySearchActivity::class.java))

        }
    }

    override fun onDataLoad(weatherBean: WeatherBean) {



    }

    override fun onError() {

    }

    private fun crateData() {

        mCities.add(CityInfo(
                "成都",
                "四川",
                "0",
                "1",
                "15"
        ))

        mCities.add(CityInfo(
                "南充",
                "四川",
                "0",
                "1",
                "15"
        ))

        mCities.add(CityInfo(
                "达州",
                "四川",
                "0",
                "1",
                "15"
        ))

        mCities.add(CityInfo(
                "眉山",
                "四川",
                "0",
                "1",
                "15"
        ))

        requestAllCityWeather()

        mCityManagerAdapter.notifyDataSetChanged()

    }


    private fun initRecyclerView() {

        city_recyclerView.layoutManager = LinearLayoutManager(this)
        city_recyclerView.adapter = mCityManagerAdapter
        city_recyclerView.addItemDecoration(CityManagerDecoration(this))
        mTouchHelper.attachToRecyclerView(city_recyclerView)

        mCityManagerAdapter.setDragListener( {
            mTouchHelper.startDrag(it)
        }, {
            mCities.removeAt(it.adapterPosition)
            mCityManagerAdapter.notifyItemRemoved(it.adapterPosition)
//            mCityManagerAdapter.notifyDataSetChanged()
        })
    }
}