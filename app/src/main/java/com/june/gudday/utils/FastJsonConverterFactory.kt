package com.june.gudday.utils

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * Created by June on 2017/09/21.
 * Email:upupupgoing@126.com
 */
class FastJsonConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        return FastJsonResponseBodyConverter<Any>(type)
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<out Annotation>?, methodAnnotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody>? {
        return FastJsonRequestBodyConverter<Any>()
    }

    companion object {

        fun create() = FastJsonConverterFactory()

    }
}