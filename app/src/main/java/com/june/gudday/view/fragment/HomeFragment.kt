package com.june.gudday.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.june.gudday.R
import com.june.gudday.http.HomeBean
import com.june.gudday.view.adapter.HomeAdapter

/**
 * Created by Administrator on 2017/11/9.
 */
class HomeFragment : Fragment() {

    var homeItems = ArrayList<HomeBean>()

    val mHomeAdapter: HomeAdapter by lazy { HomeAdapter(context, homeItems) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home_main_layout, container, false)
    }


}