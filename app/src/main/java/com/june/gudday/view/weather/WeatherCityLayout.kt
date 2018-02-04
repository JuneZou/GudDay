package com.june.gudday.view.weather

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.june.gudday.R
import com.june.gudday.bean.WeatherPagerInfo
import kotlinx.android.synthetic.main.page_index_layout.view.*

/**
 * Created by Administrator on 2018/1/4.
 */
class WeatherCityLayout : LinearLayout {

    private var mPageInfo: WeatherPagerInfo? = null
    private lateinit var mTextView: TextView

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        mTextView = findViewById(R.id.test)
    }


    fun setPageInfo(info: WeatherPagerInfo) {
        mPageInfo = info

        mTextView.text = info.index.toString()
    }
}