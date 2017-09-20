package com.june.gudday.ui.activity

import android.app.Activity
import android.os.Bundle
import com.june.gudday.R
import com.june.gudday.http.ApiController
import com.june.gudday.ui.homebanner.HomeBanner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        root.addView(HomeBanner(this))

        ApiController.test()
    }
}
