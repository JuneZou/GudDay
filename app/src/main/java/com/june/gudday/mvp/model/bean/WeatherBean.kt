package com.june.gudday.mvp.model.bean

import com.june.gudday.mvp.model.bean.WeatherBean.City



/**
 * Created by xuekai on 2017/8/20.
 */
data class WeatherBean(var HeWeather5: List<WeatherBeanImp> = ArrayList()) {

    data class WeatherBeanImp(var aqi: Aqi = Aqi(),
                              var daily_forecast: ArrayList<DeilyForecast>,
                              var now: Now,
                              var basic: Basic)

    data class Aqi(var city: City = City())

    data class City(
        var aqi: String = "",
        var co: String = "",
        var no2: String = "",
        var o3: String = "",
        var pm10: String = "",
        var pm25: String = "",
        var qlty: String = "",
        var so2: String = "")

    data class DeilyForecast(var astro: Astro = Astro(),
                             var cond: Cond = Cond(),
                             var tmp: Tmp = Tmp(),
                             var wind: Wind = Wind(),
                             var date: String = "",
                             var pop: String = "")


    /**
     * @Description 天文指数
     * @param mr 月升时间
     * @param ms 日落时间
     * @param sr 日出时间
     * @param ss 日落时间
     */
    data class Astro(var mr: String = "",
                     var ms: String = "",
                     var sr: String = "",
                     var ss: String = "")

    /**
     * @Description 天气状况
     * @param code_d 白天天气状况
     * @param code_n 夜间天气状况代码
     * @param txt_d 夜间天气状况描述
     * @param txt_n 夜间天气状况描述
     */
    data class Cond(var code_d: String = "",
                    var code_n: String = "",
                    var txt_d: String = "",
                    var txt_n: String = "")

    /**
    * @Description 温度
    * */
    data class Tmp(var max: String = "",
                   var min: String = "")


    /**
     * @Description 风力情况
     * @param deg 风向（360度）
     * @param dir 风向
     * @param sc 风力等级
     * @param spd 风速
     * */
    data class Wind(var deg: String = "",
                    var dir: String = "",
                    var sc: String = "",
                    var spd: String = "")

    /**
     * @param fl 体感温度
     * @param hum 	相对湿度
     * @param pcpn 	降水量
     * @param vis 	能见度
     * */
    data class Now(var fl: String = "",
                   var hum: String = "",
                   var pcpn: String = "",
                   var tmp: String = "",
                   var vis: String = "",
                   var cond: NowCond = NowCond(),
                   var wind: NowWind)

    data class NowCond(var code: String = "",
                       var txt: String = "")

    data class NowWind(var deg: String,
                       var dir: String,
                       var spd: String)


    data class Basic(var city: String = "",
                     var cnty: String = "",
                     var id: String = "")

}

