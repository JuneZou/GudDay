package com.june.gudday.location.location

import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener

/**
 * Created by June on 2017/08/21.
 */
interface LocationListener {

    fun onLocationComplete(location: BDLocation?)

}