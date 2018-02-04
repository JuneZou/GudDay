package com.june.gudday.view.autoflow

import android.view.View
import android.view.ViewGroup

/**
 * Created by Administrator on 2018/2/1.
 */
abstract class AbstractAutoFlowAdapter {

    private var mDataObserver: DataChangeObservar? = null

    abstract fun getCount() : Int

    abstract fun getView(position: Int, parent: ViewGroup) : View

    fun setDataSetObserver(observar: DataChangeObservar) {
        mDataObserver = observar
    }

    fun notifyDataSetChanged() {
        mDataObserver?.onChange()
    }


}