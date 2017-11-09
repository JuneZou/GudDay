package com.june.gudday.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.june.gudday.R
import com.june.gudday.http.HomeBean
import com.june.gudday.view.homebanner.HomeBanner

/**
 * Created by Administrator on 2017/11/9.
 */
class HomeAdapter(private var context: Context,
                  private var homeItems: ArrayList<HomeBean>,
                  private var hasHeader: Boolean = true) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val TYPE_HEADER = 0
    val TYPE_ITEM = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }

    override fun getItemCount(): Int = if (hasHeader) homeItems.size + 1 else homeItems.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                HeaderHolder(HomeBanner(context))
            }
            TYPE_ITEM -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_home_mian_layout, parent, false)
                ItemHolder(view)
            }
            else -> {
                HeaderHolder(HomeBanner(context))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (hasHeader && position == 0) {
            return TYPE_HEADER
        }
        return TYPE_ITEM
    }

    inner class HeaderHolder(v: View) : RecyclerView.ViewHolder(v)

    inner class ItemHolder(v: View) : RecyclerView.ViewHolder(v)

}