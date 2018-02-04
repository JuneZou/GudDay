package com.june.gudday.http.service

import com.june.gudday.city.SearchCityInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Administrator on 2018/2/4.
 */
interface SearchCityService {
    @GET("?")
    fun getCityByName(@Query("appid") key: String = "abd61c823d46fa420471b84e04452a0b", @Query("city") name: String) : Observable<SearchCityInfo>

}