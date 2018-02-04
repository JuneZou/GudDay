package com.june.gudday.view.help

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by Administrator on 2018/1/22.
 */
class CityManagerCallBack(private var listener: OnItemTouchCallbackListener) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {

        var dragFlags = 0
        var swipeFlags = 0
        val layoutManager = recyclerView?.layoutManager

        when (layoutManager) {
            is GridLayoutManager -> {
                dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                swipeFlags = 0
            }
            is LinearLayoutManager -> {

                if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                    dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                    swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                } else {
                    dragFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                    swipeFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                }

            }
        }

        return makeMovementFlags(dragFlags, swipeFlags)

    }

    override fun isLongPressDragEnabled(): Boolean {
        return super.isLongPressDragEnabled()
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return listener.onMove(viewHolder!!.adapterPosition, target!!.adapterPosition)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        listener.onSwipe()
    }

    interface OnItemTouchCallbackListener {

        fun onMove(srcPosition: Int, targetPosition: Int) : Boolean

        fun onSwipe()

    }
}

