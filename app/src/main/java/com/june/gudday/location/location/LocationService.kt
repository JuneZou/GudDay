package com.june.gudday.location.location

import android.content.Context
import android.util.Log
import com.baidu.location.*
import com.june.gudday.utils.LogUtils
import io.reactivex.Observable

/**
 * Created by June on 2017/08/18.
 */
class LocationService(locationContext: Context) {

    var client: LocationClient = LocationClient(locationContext)
    var mOption: LocationClientOption? = null
    var DIYoption:LocationClientOption? = null
    val objLock = Any()
    val listeners = ArrayList<LocationListener>()
    var observable: Observable<BDLocation>? = null

    init {

        client.locOption = getDefaultLocationClientOption()
//        registerDefaultListener()
    }

    fun getDefaultLocationClientOption(): LocationClientOption {

        val option = LocationClientOption()

        option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll")//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        option.setScanSpan(0)//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true)//可选，设置是否需要地址信息，默认不需要
        option.setIsNeedLocationDescribe(false)//可选，设置是否需要地址描述
        option.setNeedDeviceDirect(false)//可选，设置是否需要设备方向结果
        option.isLocationNotify = false//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true)//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIsNeedLocationDescribe(false)//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false)//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.SetIgnoreCacheException(false)//可选，默认false，设置是否收集CRASH信息，默认收集

        option.isOpenGps = false //设置是否进行gps定位

        option.setIsNeedAltitude(false)//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        return option
    }

    fun start(): LocationService {
        synchronized(objLock) {

            Log.e(LocationService::class.java.simpleName, client.isStarted.toString())
            client.start()
//            if (!client.isStarted) {
//                client.start()
//            }
        }
        return this
    }

    fun stop() {

        synchronized(objLock) {
            if (client.isStarted) {
                client.stop()
            }
        }
    }

    fun requestHotSpotState(): Boolean {

        return client.requestHotSpotState()

    }

    fun setLocationOption(option: LocationClientOption?): Boolean {
        val isSuccess = false
        if (option != null) {
            if (client.isStarted) {
                client.stop()
            }
            DIYoption = option
            client.locOption = option
        }
        return isSuccess
    }

    fun registerListener(listener: BDLocationListener): Boolean {
        var isSuccess = false
        if (true) {
            client.registerLocationListener(listener)
            isSuccess = true
        }
        return isSuccess
    }

    fun unregisterListener(listener: BDLocationListener) {
        if (true) {
            client.unRegisterLocationListener(listener)
        }
    }

    fun registerDefaultListener(): LocationService {
        client.registerLocationListener(mListener)
        return this
    }

    fun addListener(listener: LocationListener) {
        listeners.add(listener)
    }

    fun removeLiser(listener: LocationListener) {
        listeners.remove(listener)
    }

    private val mListener = BDLocationListener { location ->
        // TODO Auto-generated method stub
        if (null != location && location.locType != BDLocation.TypeServerError) {
            val sb = StringBuffer(256)
            sb.append("time : ")
            /**
             * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
             * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
             */
            /**
             * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
             * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
             */
            sb.append(location.time)
            sb.append("\nlocType : ")// 定位类型
            sb.append(location.locType)
            sb.append("\nlocType description : ")// *****对应的定位类型说明*****
            sb.append(location.locTypeDescription)
            sb.append("\nlatitude : ")// 纬度
            sb.append(location.latitude)
            sb.append("\nlontitude : ")// 经度
            sb.append(location.longitude)
            sb.append("\nradius : ")// 半径
            sb.append(location.radius)
            sb.append("\nCountryCode : ")// 国家码
            sb.append(location.countryCode)
            sb.append("\nCountry : ")// 国家名称
            sb.append(location.country)
            sb.append("\ncitycode : ")// 城市编码
            sb.append(location.cityCode)
            sb.append("\ncity : ")// 城市
            sb.append(location.city)
            sb.append("\nDistrict : ")// 区
            sb.append(location.district)
            sb.append("\nStreet : ")// 街道
            sb.append(location.street)
            sb.append("\naddr : ")// 地址信息
            sb.append(location.addrStr)
            sb.append("\nUserIndoorState: ")// *****返回用户室内外判断结果*****
            sb.append(location.userIndoorState)
            sb.append("\nDirection(not all devices have value): ")
            sb.append(location.direction)// 方向
            sb.append("\nlocationdescribe: ")
            sb.append(location.locationDescribe)// 位置语义化信息
            sb.append("\nPoi: ")// POI信息
            if (location.poiList != null && !location.poiList.isEmpty()) {
                for (i in 0..location.poiList.size - 1) {
                    val poi = location.poiList[i] as Poi
                    sb.append(poi.name + ";")
                }
            }
            if (location.locType == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ")
                sb.append(location.speed)// 速度 单位：km/h
                sb.append("\nsatellite : ")
                sb.append(location.satelliteNumber)// 卫星数目
                sb.append("\nheight : ")
                sb.append(location.altitude)// 海拔高度 单位：米
                sb.append("\ngps status : ")
                sb.append(location.gpsAccuracyStatus)// *****gps质量判断*****
                sb.append("\ndescribe : ")
                sb.append("gps定位成功")
            } else if (location.locType == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                // 运营商信息
                if (location.hasAltitude()) {// *****如果有海拔高度*****
                    sb.append("\nheight : ")
                    sb.append(location.altitude)// 单位：米
                }
                sb.append("\noperationers : ")// 运营商信息
                sb.append(location.operators)
                sb.append("\ndescribe : ")
                sb.append("网络定位成功")
            } else if (location.locType == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ")
                sb.append("离线定位成功，离线定位结果也是有效的")
            } else if (location.locType == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ")
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因")
            } else if (location.locType == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ")
                sb.append("网络不同导致定位失败，请检查网络是否通畅")
            } else if (location.locType == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ")
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机")
            }

            Log.e(LocationService::class.java.simpleName, sb.toString())
        }

        listeners.iterator().forEach {
            it.onLocationComplete(location)
        }

        observable = Observable.create {
            it.onNext(location)
            LogUtils.e("start: ${location.addrStr}")
        }

        stop()
    }


}