package com.june.gudday.view.widget.swipelayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.june.gudday.R
import com.june.gudday.view.widget.CircleView

/**
 * Created by June on 2017/11/20.
 * Email:upupupgoing@126.com
 */
class DefaultHeadIndicator : BaseIndictor() {
    val TAG = "DefaultHeadIndicator"

    private lateinit var circleView: CircleView
    private lateinit var stringIndicator: TextView

    override fun createView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {
        val v = layoutInflater.inflate(R.layout.prj_ptr_header_default, viewGroup, true) as ViewGroup
        val child = v.getChildAt(v.childCount - 1)
        circleView = v.findViewById(R.id.circleView)
        stringIndicator = v.findViewById(R.id.tv_header)
        return child
    }

    override fun onAction() {
        stringIndicator.text = "释放刷新"
    }

    override fun unAction() {
        stringIndicator.text = "下拉刷新"
    }

    override fun onRestore() {
        stringIndicator.text = "下拉刷新"
        circleView.isCanDrawCircle = false
    }

    override fun onLoading() {
        circleView.isCanDrawCircle = true
        stringIndicator.text = "加载中"
    }
}