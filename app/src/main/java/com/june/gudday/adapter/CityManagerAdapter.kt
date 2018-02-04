package com.june.gudday.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.june.gudday.R
import com.june.gudday.city.CityInfo
import com.june.gudday.inflateLayout

/**
 * Created by Administrator on 2018/1/10.
 */
class CityManagerAdapter(private var mContext: Context,
                         private var mCities: ArrayList<CityInfo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        const val STATE_NONE = 0
        const val STATE_EDIT = 1

    }

    private var mDragTouchListener: OnDragTouchListener? = null

    private var mCurState = STATE_NONE

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == STATE_NONE) {
            val view = mContext.inflateLayout(R.layout.item_city_manager_layout, parent)
            Hold(view)
        } else {
            val view = mContext.inflateLayout(R.layout.item_city_manager_edit_layout, parent)
            EditHold(view)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {

        if (holder is Hold) {

            with (mCities[position]) {

                holder.cityName.text = mCityName
                holder.provinceName.text = mProvinceName
                holder.isLocation.visibility = if (mIsLocation) View.VISIBLE else View.GONE
                holder.tmp.text = getTmpSpannableString(mCityTmp)

            }
        }

        if (holder is EditHold) {

            holder.deleteIcon.tag = position
            holder.dragIcon.tag = position

            holder.deleteIcon.setOnClickListener {
                if (it.tag == position) {
                    mDragTouchListener?.onDelete(holder)
                }
            }

            holder.dragIcon.setOnTouchListener { v, event ->

                if (v.tag == position) {
                    mDragTouchListener?.onTouch(holder)
                }

                false
            }

            holder.cityName.text = mCities[position].mCityName
            holder.provinceName.text = mCities[position].mProvinceName

        }

    }

    override fun getItemCount(): Int {
        return mCities.size
    }

    override fun getItemViewType(position: Int): Int {
        return mCurState

    }

    fun setDragListener(drag: (RecyclerView.ViewHolder) -> Unit, delete: (RecyclerView.ViewHolder) -> Unit) {

        mDragTouchListener = object : OnDragTouchListener {
            override fun onDelete(viewHolder: RecyclerView.ViewHolder) {
                delete(viewHolder)
            }

            override fun onTouch(viewHolder: RecyclerView.ViewHolder) {
                drag(viewHolder)
            }
        }

    }

    fun changeState() {
        mCurState = if (mCurState == STATE_EDIT) {
            STATE_NONE
        } else {
            STATE_EDIT
        }

        notifyDataSetChanged()
    }

    private fun getTmpSpannableString(tmp: String) : SpannableString {

        val temp = SpannableString("$tmp°C")
        val sizeSpan = RelativeSizeSpan(0.4f)
        val superScriSpan = SuperscriptSpan()
        temp.setSpan(sizeSpan, tmp.length, temp.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
//        temp.setSpan(superScriSpan, tmp.length, temp.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        return temp

    }

    inner class Hold(view: View) : RecyclerView.ViewHolder(view) {

        val cityName: TextView = view.findViewById(R.id.city_manager_city_name)
        val provinceName: TextView = view.findViewById(R.id.city_manager_province_name)
        val isLocation: View = view.findViewById(R.id.city_manager_city_isLocation)
        val indicator: ImageView = view.findViewById(R.id.city_manager_city_weather_indicator)
        val tmp: TextView = view.findViewById(R.id.city_manager_city_temp)
        val root: View = view.findViewById(R.id.city_manager_city_layout)
    }

    inner class EditHold(view: View) : RecyclerView.ViewHolder(view) {

        val deleteIcon: View = view.findViewById(R.id.city_manager_city_edit_delete)
        val cityName: TextView = view.findViewById(R.id.city_manager_city_edit_name)
        val provinceName: TextView = view.findViewById(R.id.city_manager_province_edit_name)
        val dragIcon: View = view.findViewById(R.id.city_manager_city_weather_edit_drag)

    }

    interface OnDragTouchListener {

        fun onTouch(viewHolder: RecyclerView.ViewHolder)

        fun onDelete(viewHolder: RecyclerView.ViewHolder)
    }
}
