package com.june.gudday.decoration

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.june.gudday.utils.Dp2PxUil

/**
 * Created by Administrator on 2018/1/10.
 */
class CityManagerDecoration(private val mContext: Context) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.top = Dp2PxUil.dp2Px(mContext, 6f).toInt()
        outRect.left = Dp2PxUil.dp2Px(mContext, 6f).toInt()
        outRect.right = Dp2PxUil.dp2Px(mContext, 6f).toInt()
    }

}