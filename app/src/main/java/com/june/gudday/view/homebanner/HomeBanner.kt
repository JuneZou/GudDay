package com.june.gudday.view.homebanner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.june.gudday.mvp.model.bean.WeatherBean
import com.june.gudday.utils.DisplayManager

/**
 * Created by June on 2017/09/19.
 * Email:upupupgoing@126.com
 */
class HomeBanner : FrameLayout {

    val viewPager: ViewPager by lazy { ViewPager(context)}
    val weatherDesLayout: WeatherDesLayout by lazy { WeatherDesLayout(context) }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }


    fun initView() {

        viewPager.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, DisplayManager.getRealHeight(500) ?: 0)

        val decorateView = LinearLayout(context)

        decorateView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        decorateView.gravity = Gravity.CENTER_HORIZONTAL
        decorateView.orientation = LinearLayout.VERTICAL

        val headerIcon = WeatherBackImageView(context)
//        headerIcon.layoutParams = LayoutParams(DisplayManager.getRealWidth(200) ?: 0, DisplayManager.getRealWidth(200) ?: 0)
        headerIcon.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, DisplayManager.getRealHeight(500) ?: 0)
//        headerIcon.setImageResource(R.mipmap.bg_ice_rain_left)


        val titile = TextView(context)

        titile.layoutParams = LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        titile.textSize = DisplayManager.dp2px(12f).toFloat()
        titile.setTextColor(Color.BLACK)
        titile.text = "这是什么"

//        decorateView.addView(headerIcon)
        decorateView.addView(titile)

        addView(viewPager)
        addView(headerIcon)
        addView(weatherDesLayout)

        setWillNotDraw(false)

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    fun updateWeatherData(weatherBean: WeatherBean) {
        weatherDesLayout.updateWeatherData(weatherBean)
    }
}