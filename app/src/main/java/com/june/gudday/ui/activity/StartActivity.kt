package com.june.gudday.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.june.gudday.R
import com.june.gudday.http.NetWork
import com.june.gudday.utils.StatusBarUtil
import kotlinx.android.synthetic.main.start_layout.*
import java.lang.Exception
import java.util.*

/**
 * Created by June on 2017/09/18.
 * Email:upupupgoing@126.com
 */
class StartActivity : Activity() {

    var isIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.start_layout)

        StatusBarUtil.transparentStatusBar(this)

        val index = Random().nextInt(NetWork.TRANSITION_URLS.size)

        Glide.with(this)
                .load(NetWork.TRANSITION_URLS[index])
                .placeholder(R.mipmap.img_transition_default)
                .error(R.mipmap.img_transition_default)
                .listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                        return true
                    }

                    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        Handler(Looper.getMainLooper()).postDelayed({
                            toMainActivity()
                        }, 3000)
                        return false
                    }
                })
                .into(start_act)

        start_jump.setOnClickListener {
            toMainActivity()
        }
    }

    fun toMainActivity() {

        if (isIn) {
            return
        }

        isIn = true
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out)
        finish()
    }



}