package com.june.gudday.utils

import com.alibaba.fastjson.JSON
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Converter

/**
 * Created by June on 2017/09/25.
 * Email:upupupgoing@126.com
 */
class FastJsonRequestBodyConverter<T> : Converter<T, RequestBody> {

    private val MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8")

    override fun convert(value: T): RequestBody {
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value));
    }
}