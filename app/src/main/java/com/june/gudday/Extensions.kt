package com.june.gudday

import android.app.Activity
import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by June on 2017/09/26.
 * Email:upupupgoing@126.com
 */

fun <T> Observable<T>.io_to_main(): Observable<T> {
    return subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Activity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.inflateLayout(@LayoutRes layout: Int, parent: ViewGroup?, isAttach: Boolean = false) : View {
    return LayoutInflater.from(this).inflate(layout, parent, isAttach)

}