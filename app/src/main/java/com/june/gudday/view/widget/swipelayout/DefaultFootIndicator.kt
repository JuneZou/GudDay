package com.june.gudday.view.widget.swipelayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.june.gudday.R

/**
 * Created by June on 2017/11/20.
 * Email:upupupgoing@126.com
 */
class DefaultFootIndicator : BaseIndictor() {
    val TAG = "DefaultHeadIndicator"

    override fun createView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {
        val view = layoutInflater.inflate(R.layout.refresh_top_item, viewGroup, true) as ViewGroup
        val child = view.getChildAt(view.childCount - 1)
        return child
    }

    override fun onAction() {
    }

    override fun unAction() {
    }

    override fun onRestore() {
    }

    override fun onLoading() {
    }
}