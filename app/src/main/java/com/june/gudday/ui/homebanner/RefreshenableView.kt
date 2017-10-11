package com.june.gudday.ui.homebanner

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.june.gudday.R
import com.june.gudday.utils.LogUtils

/**
 * Created by June on 2017/10/10.
 * Email:upupupgoing@126.com
 */
class RefreshenableView : LinearLayout {

    /*刷新界面*/
    lateinit var mRefreshView: LinearLayout
    lateinit var mRefreshSun: ImageView
    lateinit var mRefreshTime: TextView

    /*刷新布局的宽度，为了保持与layout中一致，使用values来获取*/
    private var mRefreshTargetHeight = 0f
    /*刷新中布局的宽度，为了保持与layout中一致，使用values来获取*/
    private var mRefreshingTatgetHeight = 0f

    var mLastX = 0f
    var mLastY = 0f

    var motionX = 0f
    var motionY = 0f

    /*滑动的相对距离*/
    var mCurrentActDistance = 0f

    /*横竖比系数*/
    val COMPARE_RATE = 1.5f

    /*判断是否正在进行加载数据*/
    var isRefreshing = false

    /*判断是否可以下拉*/
    var mCanPull = true

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    fun init(refreshView: LinearLayout) {

        mRefreshView = refreshView
        mRefreshSun = mRefreshView.findViewById(R.id.refresh_sun)
        mRefreshTime = mRefreshView.findViewById(R.id.refresh_time)

        mRefreshTargetHeight = context.resources.getDimension(R.dimen.refresh_view_hight)
        mRefreshingTatgetHeight = context.resources.getDimension(R.dimen.refreshing_view_hight)
        mRefreshView.translationY = -mRefreshTargetHeight
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        motionX = ev.rawX
        motionY = ev.rawY
        LogUtils.debug(super.onInterceptTouchEvent(ev).toString())
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = motionX
                mLastY = motionY
                return false
            }
            MotionEvent.ACTION_MOVE -> {
                if (COMPARE_RATE * Math.abs(motionY - mLastY) > Math.abs(mLastX - motionX) /*&& mCanPull*/) {
                    LogUtils.debug("move")
                    return true
                }
                return false
            }
        }


        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> mLastY = event.rawY
            MotionEvent.ACTION_MOVE -> {
                doMovent(event.rawY - mLastY)
                mLastY = event.rawY
            }
            MotionEvent.ACTION_UP -> {
                fling()
            }
        }
        return true
    }

    fun doMovent(distance: Float) {
        mCurrentActDistance = Math.min(mRefreshTargetHeight, translationY + distance)

        if (mCurrentActDistance >= 0f) {
            mRefreshView.translationY = -mRefreshTargetHeight + mCurrentActDistance
            mRefreshSun.scaleX = 1f - (mRefreshTargetHeight - mCurrentActDistance) / mRefreshTargetHeight
            mRefreshSun.scaleY = mRefreshSun.scaleX
            mRefreshSun.translationY = mRefreshTargetHeight - mCurrentActDistance + mCurrentActDistance * 0.37f - mRefreshTime.height
            mRefreshTime.translationY = (mRefreshTargetHeight + mRefreshSun.translationY) / 2f - mRefreshTime.top
            translationY = mCurrentActDistance
        } else {
            translationY = 0f
        }
    }

    fun fling() {

        startRefreshAnimation()

//        reutunInitState()
    }

    fun reutunInitState() {
        animate().translationY(0f).setDuration(200).start()
        mRefreshView.animate().translationY(-mRefreshTargetHeight).setDuration(200).start()
        mCanPull = true
    }

    fun startRefreshAnimation() {
        isRefreshing = true
        mRefreshView.animate().translationY(mRefreshingTatgetHeight - mRefreshTargetHeight).setDuration(200).setListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                mRefreshSun.setImageResource(R.drawable.refresh)
                val animation2 = mRefreshSun.drawable as AnimationDrawable
                animation2.isOneShot = false
                animation2.start()
            }
        }).start()

        val trans = mRefreshTargetHeight - mRefreshingTatgetHeight + 0.37 * mRefreshingTatgetHeight - mRefreshTime.height
        mRefreshSun.animate().translationY(trans.toFloat()).setDuration(200).start()
        mRefreshTime.animate().translationY((mRefreshTargetHeight + trans.toFloat())/2f - mRefreshTime.top).setDuration(200).start()
        animate().translationY(mRefreshingTatgetHeight).setDuration(200).start()
        mCanPull = false
    }
}