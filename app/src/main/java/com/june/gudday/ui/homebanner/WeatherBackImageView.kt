package com.june.gudday.ui.homebanner

import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.*
import android.view.animation.Interpolator
import android.widget.ImageView
import com.june.gudday.R
import com.june.gudday.utils.DisplayManager
import com.june.gudday.utils.LogUtils

/**
 * Created by June on 2017/09/28.
 * Email:upupupgoing@126.com
 */
class WeatherBackImageView(context: Context?) : ImageView(context) {

    lateinit var left_bg: Bitmap
    lateinit var right_bg: Bitmap
    lateinit var bg: Bitmap
    lateinit var first_rod: Bitmap
    lateinit var first_head: Bitmap
    lateinit var first_cloud: Bitmap

    val paint: Paint by lazy { val it = Paint()
        it.isAntiAlias = true
        it.isFilterBitmap = true
        it
    }

    val bg_paint: Paint by lazy { val it = Paint()
        it.alpha = 150
        it
    }

    var mMatrix = Matrix()

    var mWidth = 0f
    var mHeight = 0f
    var mLeftOriginalWidth = 0
    var mLeftOriginalHeight = 0
    var mRightOriginalWidth = 0
    var mRightOriginalHeight = 0

    var currentRotate = 2f
    var currentPrecent = 0f

    fun initView() {

        val left_option = BitmapFactory.Options()
        left_option.inJustDecodeBounds = true

        val right_option = BitmapFactory.Options()
        right_option.inJustDecodeBounds = true

        val bg_option = BitmapFactory.Options()
        right_option.inJustDecodeBounds = true

        BitmapFactory.decodeResource(context.resources, R.mipmap.bg_cloudy_left, left_option)
        BitmapFactory.decodeResource(context.resources, R.mipmap.bg_cloudy_right, right_option)
        BitmapFactory.decodeResource(context.resources, R.mipmap.bg_cloudy, bg_option)


        mLeftOriginalWidth = left_option.outWidth
        mLeftOriginalHeight = left_option.outHeight

        mRightOriginalWidth = right_option.outWidth
        mRightOriginalHeight = right_option.outHeight

        left_option.inSampleSize = DisplayManager.disPlaySrceenWidth!! / mLeftOriginalWidth
        left_option.inJustDecodeBounds = false

        right_option.inSampleSize = DisplayManager.disPlaySrceenWidth!! / mRightOriginalWidth
        right_option.inJustDecodeBounds = false

        bg_option.inSampleSize = DisplayManager.disPlaySrceenWidth!! / bg_option.outWidth
        bg_option.inJustDecodeBounds = false

        left_bg = BitmapFactory.decodeResource(context.resources, R.mipmap.bg_cloudy_left, left_option)
        right_bg = BitmapFactory.decodeResource(context.resources, R.mipmap.bg_cloudy_right, right_option)
        bg = BitmapFactory.decodeResource(context.resources, R.mipmap.bg_cloudy, bg_option)

        val head_option = BitmapFactory.Options()
        head_option.inSampleSize = 1
        head_option.inTargetDensity = 320

        first_rod = BitmapFactory.decodeResource(context.resources, R.mipmap.bg_cloudy_windmill_first_rod)
        first_head = BitmapFactory.decodeResource(context.resources, R.mipmap.bg_cloudy_windmill_first_head, head_option)
        first_cloud = BitmapFactory.decodeResource(context.resources, R.mipmap.bg_cloudy_first_cloud, head_option)

        first_head = small(first_head)

//        LogUtils.e("first_head:w:${first_head.width}, h:${first_head.height}")
//        LogUtils.e("first_rod:w:${first_rod.width}, h:${first_rod.height}")
    }

    private fun small(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.postScale(0.8f, 0.8f) //长和宽放大缩小的比例
        val resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        return resizeBmp
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        LogUtils.e("left_draw, w: ${left_bg.width}, h:${left_bg.height}")
//        LogUtils.e("right_draw, w: ${right_bg.width}, h:${right_bg.height}")
//        LogUtils.e("bg, w: ${bg.width}, h:${bg.height}")
        canvas?.drawBitmap(bg, 0f, 0f, bg_paint)
        canvas?.drawBitmap(left_bg, 0f, 300f, paint)
        canvas?.drawBitmap(right_bg, 0f, 300f, paint)
        canvas?.drawBitmap(first_rod, 700f, 200f, paint)

        canvas?.drawBitmap(first_cloud, DisplayManager.disPlaySrceenWidth!! * (1 - currentPrecent), 100f, paint)
        currentPrecent += 0.0005f

        mMatrix.postRotate(currentRotate, first_head.width/2f, first_head.height/2f)
        canvas?.translate(700f - first_head.width/2 + 7, 200f - first_head.height/2)
        canvas?.drawBitmap(first_head, mMatrix, paint)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec).toFloat()
        mHeight = MeasureSpec.getSize(heightMeasureSpec).toFloat()
    }

    init {
        initView()
    }

}