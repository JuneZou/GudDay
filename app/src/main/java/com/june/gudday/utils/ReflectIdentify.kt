package com.june.gudday.utils

import android.content.Context

/**
 * Created by June on 2017/08/14.
 */
class ReflectIdentify {

    companion object {

        fun getStringIdentifier(id: String,context: Context) : Int = context.resources.getIdentifier(id, "string", context.packageName)

        fun getLayoutResId(resname: String, context: Context) = context.resources.getIdentifier(resname, "layout", context.packageName)
        fun getIdResId(resname: String, context: Context) = context.resources.getIdentifier(resname, "id", context.packageName)
        fun getStringResId(resname: String, context: Context) = context.resources.getIdentifier(resname, "string", context.packageName)
        fun getDrawableResId(resname: String, context: Context) = context.resources.getIdentifier(resname, "drawable", context.packageName)
        fun getThemeResId(resname: String, context: Context) = context.resources.getIdentifier(resname, "style", context.packageName)

    }

}