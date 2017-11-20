package com.june.gudday.view.widget.swipelayout

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.OverScroller
import com.june.gudday.R

/**
 * Created by June on 2017/11/20.
 * Email:upupupgoing@126.com
 */
class SwipeLayout : ViewGroup {
    val TAG = "SwipeLayout"

    private val mScoller: OverScroller by lazy { OverScroller(context) }
    private var mVelocityTracker: VelocityTracker? = null
    private var yVelocity: Float = 0f

    private var isNeedInterept = false

    private var listener: OnRefreshListener? = null

    private var mLastX = 0f
    private var mLastY = 0f
    private var deltaX = 0f
    private var deltaY = 0f

    private var mHeaderLoadPosition = 0
    private var mFooterLoadPosition = 0
    private var mHeaderHoldPosition = 0
    private var mFooterHoldPosition = 0

    private var isPullDownEnable = true
    private var isPullUpEnable = true

    private val IDLE = 0
    private val PULL_DOWN = 1
    private val PULL_UP = 2

    private val SCROLLER_DURATION = 400

    private var mStatus = IDLE

    private var mActivePointId = MotionEvent.INVALID_POINTER_ID

    //headerView
    private lateinit var mHeaderView: View
    private var mHeaderIndictor: BaseIndictor? = null
    private lateinit var mHeadClassName: String /*不用layout而用View类来实现的原因在于方便控制头尾View自己的交互逻辑*/

    //footerView
    private lateinit var mFooterView: View
    private var mFooterIndictor: BaseIndictor? = null
    private lateinit var mFootClassName: String /*不用layout而用View类来实现的原因在于方便控制头尾View自己的交互逻辑*/

    private val mInflate: LayoutInflater by lazy { LayoutInflater.from(context) }

    //contentView
    private var mContentView: View? = null

    private var isLoading = false

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.swipeLayout)

        if (typeArray.hasValue(R.styleable.swipeLayout_header_indicator)) {
            mHeadClassName = typeArray.getString(R.styleable.swipeLayout_header_indicator)
        }

        if (typeArray.hasValue(R.styleable.swipeLayout_footer_indicator)) {
            mFootClassName = typeArray.getString(R.styleable.swipeLayout_footer_indicator)
        }

        typeArray.recycle()

    }

    override fun onFinishInflate() {

        if (childCount > 1) {
            Throwable("SwipeLayout can only has one child")
        }

        mContentView = getChildAt(0)

        /*create HeaderView*/
        mHeaderIndictor = getIndicator(mHeadClassName)
        if (mHeaderIndictor == null) {
            mHeaderIndictor = DefaultHeadIndicator()
        }
        mHeaderView = mHeaderIndictor!!.createView(mInflate, this)

        /*create FooterView*/
        mFooterIndictor = getIndicator(mFootClassName)
        if (mFooterIndictor == null) {
            mFooterIndictor = DefaultFootIndicator()
        }
        mFooterView = mFooterIndictor!!.createView(mInflate, this)

        mContentView?.bringToFront()

        super.onFinishInflate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        (0..(if (childCount > 0) childCount else 0))
                .map { getChildAt(it) }
                .forEach { measureChild(it, widthMeasureSpec, heightMeasureSpec) }

        mHeaderLoadPosition = mHeaderView.measuredHeight + (0.3 * mHeaderView.measuredHeight).toInt()
        mFooterLoadPosition = mHeaderView.measuredHeight + (0.3 * mHeaderView.measuredHeight).toInt()
        mHeaderHoldPosition = mHeaderView.measuredHeight
        mFooterHoldPosition = mHeaderView.measuredHeight

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (mContentView != null) {
            mHeaderView.layout(0, -mHeaderView.measuredHeight, width, 0)
            mFooterView.layout(0, height, width, height + mFooterView.measuredHeight)
            mContentView!!.layout(0, 0, mContentView!!.measuredWidth, mContentView!!.measuredHeight)
        }
    }

    private fun getIndicator(className: String) : BaseIndictor?{
        if (!className.isEmpty()) {
            val clazz = Class.forName(className)
            return clazz.newInstance() as BaseIndictor
        }
        return null
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        dealEvent(ev)
        val action = ev?.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                /*在动画没有停止的情况下重新开始一个触摸*/
                if (!mScoller.isFinished) {
                    mScoller.abortAnimation()
                }
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain()
                } else {
                    mVelocityTracker!!.clear()
                }
                mVelocityTracker?.addMovement(ev)
            }
            MotionEvent.ACTION_MOVE -> {
                mVelocityTracker?.addMovement(ev)
                mVelocityTracker?.computeCurrentVelocity(500)
                yVelocity = mVelocityTracker?.yVelocity ?: 0f
                isNeedInterept = isNeedIntercept()
            }
            MotionEvent.ACTION_UP -> {
                autoBackToPosition()
                isNeedInterept = false
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun dealEvent(ev: MotionEvent?) {

        val action = ev?.actionMasked
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                val pointerIndex = ev.actionIndex
                mLastX = ev.getX(pointerIndex)
                mLastY = ev.getY(pointerIndex)
                mActivePointId = ev.getPointerId(0)
            }

            MotionEvent.ACTION_MOVE -> {
                val pointerIndex = ev.findPointerIndex(mActivePointId)
                val x = ev.getX(pointerIndex)
                val y = ev.getY(pointerIndex)
                deltaX = x - mLastX
                deltaY = y - mLastY
                mLastX = x
                mLastY = y
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> mActivePointId = MotionEvent.INVALID_POINTER_ID

            MotionEvent.ACTION_POINTER_DOWN -> {
                val pointerIndex = ev.actionIndex
                val pointerId = ev.getPointerId(pointerIndex)
                if (mActivePointId != pointerId) {
                    mLastX = ev.getX(pointerIndex)
                    mLastY = ev.getY(pointerIndex)
                    mActivePointId = pointerId
                }
            }

            MotionEvent.ACTION_POINTER_UP -> {
                val pointIndex = ev.actionIndex
                val pointId = ev.getPointerId(pointIndex)
                if (pointId == mActivePointId) {
                    val newPointIndex = if (pointIndex == 0) 1 else 0
                    mLastX = ev.getX(newPointIndex)
                    mLastY = ev.getY(newPointIndex)
                    mActivePointId = ev.getPointerId(newPointIndex)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (!isPullDownEnable || !isPullUpEnable) {
                    return true
                }

                if (isNeedInterept) {
                    if (mStatus == PULL_DOWN && scrollY > 0) {
                        return true
                    }
                    if (mStatus == PULL_UP && scrollY < 0) {
                        return true
                    }

                    scrollBy(0, -getMoveFloat(yVelocity, deltaY).toInt())
                } else {
                    event.action = MotionEvent.ACTION_DOWN
                    dispatchTouchEvent(event)
                    return true
                }
            }

            MotionEvent.ACTION_UP -> {
                autoBackToPosition()
            }
        }

        return true
    }

    /**
     * 速度控制函数
     * 当手指移动速度越接近10000px每500毫秒时获得的控件移动距离越小
     * 1.5f为权重，越大速度控制越明显，表现为用户越不容易拉动本控件，即拉动越吃力
     *
     *
     * 若不进行速度控制，将可能导致一系列问题，其中包括
     * 用户下拉一段距离，突然很快加速上拉控件，这时footer将被拖出，但此时内部控件并未到达它的底部
     * 这样显示不符合上拉加载的逻辑
     */
    private fun getMoveFloat(velocity: Float, org: Float): Float {
        return (10000f - Math.abs(velocity)) / 10000f * org / 1.5f
    }

    private fun isNeedIntercept() : Boolean {
        if (deltaY > 0 && isScrollOnTop() || scrollY < -10) {
            mStatus = PULL_DOWN
            return true
        }

        if (deltaY < 0&& isScrollOnBottom() || scrollY > 10) {
            mStatus = PULL_UP
            return true
        }
        return false
    }

    private fun isScrollOnTop() : Boolean {
        /*1 as up -1 as dwon*, if return false is to top or bottom*/
        return !(mContentView?.canScrollVertically(-1) ?: true)
    }

    private fun isScrollOnBottom() : Boolean {
        /*1 as up -1 as dwon*, if return false is to top or bottom*/
        return !(mContentView?.canScrollVertically(1) ?: true)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = isNeedInterept

    private fun autoBackToPosition() {
        if (mStatus == PULL_DOWN && Math.abs(scrollY) < mHeaderLoadPosition) {
            autoBackToHoldPosition()
        } else if (mStatus == PULL_DOWN && Math.abs(scrollY) > mHeaderLoadPosition){
            autoBackToLoadPosition()
        } else if (mStatus == PULL_UP && Math.abs(scrollY) < mFooterLoadPosition) {
            autoBackToHoldPosition()
        } else if (mStatus == PULL_UP && Math.abs(scrollY) > mFooterLoadPosition) {
            autoBackToLoadPosition()
        }
    }

    private fun autoBackToHoldPosition() {
        mScoller.startScroll(0, scrollY, 0, -scrollY, SCROLLER_DURATION)
        postDelayed({
            restoreIndicator()
            mStatus = IDLE
        }, SCROLLER_DURATION.toLong())
    }

    private fun autoBackToLoadPosition() {
        when (mStatus) {
            PULL_DOWN -> {
                mScoller.startScroll(0, scrollY, 0, -scrollY - mHeaderLoadPosition, SCROLLER_DURATION)
                if (!isLoading) {
                    isLoading = true
                    listener?.onRefresh()
                }
            }

            PULL_UP -> {
                mScoller.startScroll(0, scrollY, 0, -scrollY + mFooterLoadPosition, SCROLLER_DURATION)
                if (!isLoading) {
                    isLoading = true
                    listener?.onLoadMore()
                }
            }
        }

    }

    override fun computeScroll() {
        if (!mScoller.computeScrollOffset()) {
            scrollTo(0, mScoller.currY)
            invalidate()
        }
    }

    private fun restoreIndicator() {

    }

    interface OnRefreshListener {
        fun onRefresh()
        fun onLoadMore()
    }

}