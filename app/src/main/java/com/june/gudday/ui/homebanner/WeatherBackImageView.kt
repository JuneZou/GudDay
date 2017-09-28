package com.june.gudday.ui.homebanner

import android.content.Context
import android.graphics.*
import android.widget.ImageView
import com.june.gudday.R
import com.june.gudday.utils.LogUtils

/**
 * Created by June on 2017/09/28.
 * Email:upupupgoing@126.com
 */
class WeatherBackImageView : ImageView {

    lateinit var left_bg: Bitmap
    lateinit var right_bg: Bitmap

    constructor(context: Context?) : super(context) {
        initView()
    }

    fun initView() {
        left_bg = BitmapFactory.decodeResource(context.resources, R.mipmap.bg_ice_rain_left)
        right_bg = BitmapFactory.decodeResource(context.resources, R.mipmap.bg_ice_rain_right)
        setBackgroundColor(Color.GREEN)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        LogUtils.e("draw, w: ${left_bg.width}, h:${left_bg.height}")
        canvas?.drawBitmap(left_bg, 0f, 0f, Paint())

    }
}