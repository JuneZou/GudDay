package com.june.gudday.utils

import okhttp3.ResponseBody
import retrofit2.Converter
import java.lang.reflect.Type
import com.alibaba.fastjson.JSON
import okio.Okio
import okio.BufferedSource



/**
 * Created by June on 2017/09/25.
 * Email:upupupgoing@126.com
 */
class FastJsonResponseBodyConverter<T> : Converter<ResponseBody, T> {

    private var type: Type? = null

    constructor(type: Type?) {
        this.type = type
    }

    override fun convert(value: ResponseBody?): T {

        val bufferedSource = Okio.buffer(value?.source())
        val tempStr = bufferedSource.readUtf8()
        bufferedSource.close()
        return JSON.parseObject(tempStr, type)

    }
}