package com.june.gudday.view.autoflow

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import com.baidu.mapapi.search.core.CityInfo
import com.june.gudday.R

/**
 * Created by Administrator on 2018/1/31.
 */
class AutoFlowLayout : ViewGroup {

    private val mChildInfos = ArrayList<CityInfo>()
    private val mDataChangeObservable: AdapterDataSetObserver by lazy {
        AdapterDataSetObserver()
    }

    private var mAdapter: AbstractAutoFlowAdapter? = null

    private var mWidth = 0
    private var mHeight = 0

    private var mAddingLine: AutoFlowLine? = null
    private var mAddLine: Int = 0
    private var mAllLines = ArrayList<AutoFlowLine>()

    //边距
    private var mSpaceWidth = 0f
    private var mSpaceHeight = 0f
    private var mSpaceColor: Int = Color.WHITE

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.AutoFlowLayout)

        if (typeArray.hasValue(R.styleable.AutoFlowLayout_space_width)) {
            mSpaceWidth = typeArray.getDimension(R.styleable.AutoFlowLayout_space_width, 0f)
        }

        if (typeArray.hasValue(R.styleable.AutoFlowLayout_space_height)) {
            mSpaceHeight = typeArray.getDimension(R.styleable.AutoFlowLayout_space_height, 0f)
        }

        if (typeArray.hasValue(R.styleable.AutoFlowLayout_space_color)) {
            mSpaceColor = typeArray.getColor(R.styleable.AutoFlowLayout_space_color, Color.WHITE)
        }

        typeArray.recycle()
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentTop = paddingTop
        var currentLeft: Int

        (0 until mAllLines.size).forEach {
            val max = mAllLines[it].mLineMaxHeight
            val currentIndex = it
            //从左边框的padding的位置开始布局
            currentLeft = paddingLeft
            mAllLines[it].mChildList.indices.forEach {

                mAllLines[currentIndex].mChildList[it].layout(currentLeft,
                        currentTop,
                        currentLeft + mAllLines[currentIndex].mChildList[it].measuredWidth,
                        currentTop + max)

                currentLeft += mAllLines[currentIndex].mChildList[it].measuredWidth + mSpaceWidth.toInt()
            }
            currentTop += max + mSpaceHeight.toInt()
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        //多次绘制的情况下需要清空所有数据
        mAllLines.clear()
        mAddingLine = null
        mHeight = 0

        (0 until childCount).map {

            //没有获取到View直接return
            val childView = getChildAt(it) ?: return@map
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
            Log.e("layout", "wid: ${childView.width},height:${childView.height}")

            //判断是否需要新建一个行
            //1.mAddingLine为空:第一次初始化
            //2.mAddingLine的当前宽度加上子view的宽度超过最大宽度
            if (mAddingLine?.isCanAdd(childView) != true) {
                //可占用的最大值减去 左右 padding
                mAddingLine = AutoFlowLine(mWidth - paddingLeft - paddingRight, mAddLine++, mSpaceWidth)
                mAllLines.add(mAddingLine!!)
            }
            mAddingLine!!.addViewAtLine(childView)
        }

        //计算高度
        (0 until mAllLines.size).forEach {
            //加上space宽度
            mHeight += mAllLines[it].mLineMaxHeight + if (mAllLines.size == 1 || it == mAllLines.size - 1) 0 else mSpaceHeight.toInt()
        }

        //高度加上上下padding
        mHeight += paddingBottom + paddingBottom

        Log.e("layout", "wid: $mWidth,height:$mHeight")
        setMeasuredDimension(mWidth, mHeight)

    }


    fun setAdapter(adapter: AbstractAutoFlowAdapter) {
        mAdapter = adapter
        mAdapter?.setDataSetObserver(mDataChangeObservable)
        mAdapter?.notifyDataSetChanged()
    }

    private fun createAllView() {

        removeAllViews()

        (0 until (mAdapter?.getCount() ?: 0)).map {

            mAdapter?.getView(it, this)

        }.forEach {

            addView(it)
        }


    }

    private inner class AdapterDataSetObserver : DataChangeObservar {
        override fun onChange() {
            createAllView()
            //重新布局
            requestLayout()
        }
    }

}