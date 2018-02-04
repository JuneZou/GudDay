package com.june.gudday.city

/**
 * Created by Administrator on 2018/2/4.
 */
data class SearchCityInfo(var error_code: String = "",
                          var data: Data = Data(),
                          var reason: String = "") {

    data class Data(var province: String = "",
                    var provinceShort: String = "",
                    var city: String = "")
}

