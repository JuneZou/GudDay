package com.june.gudday.view.homebanner

import android.animation.TypeEvaluator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

/**
 * Created by June on 2017/10/09.
 * Email:upupupgoing@126.com
 */
class WeatherScheduler(var min: Float, var max: Float) {

    var max_value = 1.0f
    var min_value = 0f
    var currentScgedule = 0f
    var PERIOD_DURATION = 1000f
    var INCREASE: Float = 1 / PERIOD_DURATION

    var interpolator: Interpolator? = null
        get() {
            if (field == null) {
                field = LinearInterpolator() }
            return field
        }

    var evaluator: TypeEvaluator<Float>? = null
        get() {
            if (field == null) {
                field = TypeEvaluator{
                    fraction, _, _ ->  min + (max - min) * fraction }
            }
            return field
        }


    fun getValue() : Float {
        currentScgedule += INCREASE
        return evaluator?.evaluate(interpolator?.getInterpolation(currentScgedule)!!, min, max)!!
    }

}