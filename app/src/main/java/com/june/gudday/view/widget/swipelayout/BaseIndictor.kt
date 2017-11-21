package com.june.gudday.view.widget.swipelayout

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
/**
 * Created by June on 2017/11/20.
 * Email:upupupgoing@126.com
 */
abstract class BaseIndictor {

    abstract fun createView(layoutInflater: LayoutInflater, viewGroup: ViewGroup) : View

    abstract fun onAction()

    abstract fun unAction()

    abstract fun onRestore()

    abstract fun onLoading()

}