package com.june.gudday.ui.homebanner

import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.june.gudday.R
import com.june.gudday.utils.DisplayManager
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Created by June on 2017/09/19.
 * Email:upupupgoing@126.com
 */
class HomeBanner : FrameLayout {

    val viewPager: ViewPager by lazy { ViewPager(context)}

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }


    fun initView() {

        viewPager.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, DisplayManager.getRealHeight(800) ?: 0)

        val decorateView = LinearLayout(context)

        decorateView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        decorateView.gravity = Gravity.CENTER_HORIZONTAL
        decorateView.orientation = LinearLayout.VERTICAL

        val headerIcon = WeatherBackImageView(context)
        headerIcon.layoutParams = LayoutParams(DisplayManager.getRealWidth(200) ?: 0, DisplayManager.getRealWidth(200) ?: 0)

        val titile = TextView(context)

        titile.layoutParams = LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        titile.textSize = DisplayManager.dp2px(12f).toFloat()
        titile.setTextColor(Color.BLACK)
        titile.text = "这是什么"

        decorateView.addView(headerIcon)
        decorateView.addView(titile)

        addView(viewPager)
        addView(decorateView)

        val obsever = object : Observer<String> {

            override fun onNext(t: String) {
                Log.e("test", t)
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }


        }

        val observable = Observable.create(object : ObservableOnSubscribe<String> {

            override fun subscribe(e: ObservableEmitter<String>) {

                e.onNext("hello")
                e.onNext("i")
                e.onComplete()
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Consumer<String> {

                    override fun accept(t: String) {

                    }
                })





        val sub = object : Subscriber<String> {
            override fun onSubscribe(s: Subscription?) {
            }

            override fun onComplete() {
            }

            override fun onNext(t: String?) {
                Log.e("test", t)
            }

            override fun onError(t: Throwable?) {
            }

        }

//        observable.subscribe(obsever)



    }
}