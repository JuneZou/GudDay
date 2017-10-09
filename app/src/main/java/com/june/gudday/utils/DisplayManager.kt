package com.june.gudday.utils

import android.content.Context
import android.util.DisplayMetrics

/**
 * Created by June on 2017/09/19.
 * Email:upupupgoing@126.com
 */
object DisplayManager {

    var disPlayMetric: DisplayMetrics? = null
    var disPlaySrceenWidth: Int? = null
    var disPlaySrceenHeight: Int? = null
    var disPlayDpi: Int? = null

    private val STANDARD_WIDTH = 1080
    private val STANDARD_HEIGHT = 1920

    fun init(context: Context) {

        disPlayMetric = context.resources.displayMetrics
        disPlaySrceenWidth = disPlayMetric?.widthPixels
        disPlaySrceenHeight = disPlayMetric?.heightPixels
        disPlayDpi = disPlayMetric?.densityDpi

        LogUtils.e("dpi: $disPlayDpi, screenwidth: $disPlaySrceenWidth, screenheight: $disPlaySrceenHeight")
    }

    fun getSrceenWidth() = disPlaySrceenWidth

    fun getSrceenHeight() = disPlaySrceenHeight

    fun getSrceenDpi() = disPlayDpi

    fun dp2px(dp: Float) = (dp * (disPlayMetric?.density ?: 0f) + 0.5f).toInt()

    /**
     * 输入UI图的尺寸，输出实际的px
     *
     * @param px ui图中的大小
     * @return
     */
    fun getRealWidth(px: Int): Int? {
        //ui图的宽度
        return getRealWidth(px, STANDARD_WIDTH.toFloat())
    }

    /**
     * 输入UI图的尺寸，输出实际的px,第二个参数是父布局
     *
     * @param px          ui图中的大小
     * @param parentWidth 父view在ui图中的高度
     * @return
     */
    fun getRealWidth(px: Int, parentWidth: Float): Int? {
        return (px / parentWidth * getSrceenWidth()!!).toInt()
    }

    fun getRealHeight(px: Int): Int? {
        //ui图的宽度
        return getRealHeight(px, STANDARD_HEIGHT.toFloat())
    }

    /**
     * 输入UI图的尺寸，输出实际的px,第二个参数是父布局
     *
     * @param px           ui图中的大小
     * @param parentHeight 父view在ui图中的高度
     * @return
     */
    fun getRealHeight(px: Int, parentHeight: Float): Int? {
        return (px / parentHeight * getSrceenHeight()!!).toInt()
    }

}