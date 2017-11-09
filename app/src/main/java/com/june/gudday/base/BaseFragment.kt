package com.june.gudday.base

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.june.gudday.R

/**
 * Created by Administrator on 2017/11/9.
 */
abstract class BaseFragment : Fragment() {

    private lateinit var mContainer: RelativeLayout
    private var mIsVisible: Boolean = false

    val mProgressBar: View by lazy { view?.findViewById<View>(R.id.ll_progress_bar) as View}
    val mProgressImg: ImageView by lazy { view?.findViewById<ImageView>(R.id.img_progress) as ImageView}
    val mDrawableAniama: AnimationDrawable by lazy { mProgressImg.drawable as AnimationDrawable }
    val mErrRefresh: View by lazy {view?.findViewById<View>(R.id.img_err) as View}
    lateinit var contentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val ll = inflater.inflate(R.layout.fragment_base, container, false)
        contentView = inflater.inflate(setContent(), container, false)
        mContainer = ll.findViewById(R.id.container)
        mContainer.addView(contentView)
        return ll
    }

    abstract fun setContent() : Int

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            mIsVisible = true
            onVisible()
        } else {
            mIsVisible = false
            onUnVisible()
        }
    }

    fun onVisible() {
        loadData()
    }

    fun onUnVisible() {}

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    fun loadData() {}

    fun startRefresh() {}

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 加载动画
        if (!mDrawableAniama.isRunning) {
            mDrawableAniama.start()
        }

        mErrRefresh.setOnClickListener {
            showLoading()
            startRefresh()
        }

        contentView.visibility = View.GONE
    }

    /*
    * 显示加载中界面
    * */
    protected fun showLoading() {
        if (mProgressBar.visibility != View.VISIBLE) {
            mProgressBar.visibility = View.VISIBLE
        }

        if (!mDrawableAniama.isRunning) {
            mDrawableAniama.start()
        }

        if (mErrRefresh.visibility != View.GONE) {
            mErrRefresh.visibility = View.GONE
        }

        if (contentView.visibility != View.GONE) {
            contentView.visibility = View.GONE
        }
    }

    /*
    * 显示加载完成
    * */
    protected fun showContentView(){
        if (mProgressBar.visibility != View.GONE) {
            mProgressBar.visibility = View.GONE
        }

        if (mDrawableAniama.isRunning) {
            mDrawableAniama.stop()
        }

        if (mErrRefresh.visibility != View.GONE) {
            mErrRefresh.visibility = View.GONE
        }

        if (contentView.visibility != View.VISIBLE) {
            contentView.visibility = View.VISIBLE
        }
    }

    /*
    * 显示加载错误
    * */
    protected fun showErr() {
        if (mProgressBar.visibility != View.VISIBLE) {
            mProgressBar.visibility = View.VISIBLE
        }

        if (mDrawableAniama.isRunning) {
            mDrawableAniama.stop()
        }

        if (mErrRefresh.visibility != View.GONE) {
            mErrRefresh.visibility = View.GONE
        }

        if (contentView.visibility != View.GONE) {
            contentView.visibility = View.GONE
        }
    }
}