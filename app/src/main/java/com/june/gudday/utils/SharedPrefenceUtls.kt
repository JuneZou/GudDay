package com.june.gudday.utils

import android.content.Context

/**
 * Created by Administrator on 2018/2/4.
 */
class SharedPrefenceUtls {

    companion object {

        val HISTORY = "history"
        val HISTORY_KEY = "cities"

        fun saveHistory(context: Context, value: String) {

            val edit = context.getSharedPreferences(HISTORY, Context.MODE_PRIVATE).edit()
            edit.putString(HISTORY_KEY, value)
            edit.apply()

        }

        fun getHistory(context: Context) : String {

            return context.getSharedPreferences(HISTORY, Context.MODE_PRIVATE).getString(HISTORY_KEY, "")

        }

    }

}