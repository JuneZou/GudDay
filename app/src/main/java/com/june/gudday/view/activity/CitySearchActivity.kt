package com.june.gudday.view.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import com.june.gudday.R
import com.june.gudday.app.App
import com.june.gudday.city.CityInfo
import com.june.gudday.city.CitySearchHistoryAdapter
import com.june.gudday.http.ApiController
import com.june.gudday.io_to_main
import com.june.gudday.showToast
import com.june.gudday.utils.SharedPrefenceUtls
import com.june.gudday.utils.SoftEnterUtils
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search_main_layout.*
import java.net.URLEncoder

/**
 * Created by Administrator on 2018/1/31.
 */
class CitySearchActivity : Activity() {

    private val MAX_HISTORY_ITEM = 10

    private var mHistoryList = ArrayList<CityInfo>()

    private val mHistoryAdapter: CitySearchHistoryAdapter by lazy {

        CitySearchHistoryAdapter(this,
                mHistoryList)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search_main_layout)

        initHistoryData()

        initListener()
    }

    private fun initHistoryData() {



        (SharedPrefenceUtls.getHistory(this).split(";") as ArrayList<String>).iterator().forEach {
            if (it != "") {
                mHistoryList.add(CityInfo(it))
            }
        }


    }


    private fun initListener() {

        initEditListener()

        activity_search_back_btn.setOnClickListener {
            finish()
        }

        activity_search_clear_btn.setOnClickListener {

            activity_search_cityName_edit.setText("")
        }

        city_search_history_autoflow.setAdapter(mHistoryAdapter)

    }

    private fun initEditListener() {

        activity_search_cityName_edit.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

                if (s?.isEmpty() == true) {
                    activity_search_clear_btn.visibility = View.GONE
                } else {
                    activity_search_clear_btn.visibility = View.VISIBLE
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        activity_search_cityName_edit.setOnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                startSearch()
                true
            } else {
                false
            }

        }

    }

    private fun startSearch() {

        SoftEnterUtils.closeKeybord(activity_search_cityName_edit, this)

        ApiController.searctCityService.getCityByName(ApiController.CITY_SEARCH_KEY, activity_search_cityName_edit.text.toString())
                .io_to_main()
                .subscribe({

                    if (it.error_code == "0") {

                        if (it.data.city != "") {
                            showToast(it.data.city)
                            saveHistory()
                        }

                    } else {
                        showToast(it.reason)
                    }

                })

    }

    private fun saveHistory() {

        if (activity_search_cityName_edit.text.toString() == "") {
            return
        }

        if (mHistoryList.size >= MAX_HISTORY_ITEM) {

            mHistoryList.removeAt(mHistoryList.size - 1)

        }

        mHistoryList.add(0, CityInfo(activity_search_cityName_edit.text.toString()))
        mHistoryAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()

        var value = ""

        mHistoryList.iterator().forEach {

            value += it.mCityName + ";"

        }

        SharedPrefenceUtls.saveHistory(this, value)

    }

}