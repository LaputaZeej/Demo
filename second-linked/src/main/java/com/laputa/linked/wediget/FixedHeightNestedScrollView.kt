package com.laputa.linked.wediget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.core.widget.NestedScrollView.OnScrollChangeListener
import androidx.recyclerview.widget.RecyclerView
import com.laputa.linked.util.FlingHelper

/**
 * 嵌套滑动
 *
 * NestedScrollingChild                     NestedScrollingParent
 * child                                    parent
 *
 * down
 * setNestedScrollingEnabled
 * startNestedScroll                        onStartNestedScroll
 *                                          onNestedScrollAccepted
 * move
 * dispatchNestedPreScroll                  onNestedPreScroll
 * dispatchNestedScroll                     onNestedScroll
 *
 * up
 * stopNestedScroll                         onStopNestedScroll
 */
class FixedHeightNestedScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {
    private var contentView: ViewGroup? = null
    private var headView: ViewGroup? = null
    private var mFilingHelper: FlingHelper = FlingHelper(context)
    private var totalDy = 0
    private var isStartFling = false // 判断RecyclerView是否在fling
    private var velocityY = 0 //当前滑动的y轴加速度

    init {
        // 2.根据fling时记录的velocityY，算出要滑动的距离
        // 3.需要滑动的距离-自己滑动的距离 = 子View即RecyclerView要滑动的距离
        // 4.根据RecyclerView要滑动的距离算出速度Y
        // 5.RecyclerView.fling(速度Y）
        setOnScrollChangeListener(OnScrollChangeListener { v, _, scrollY, oldScrollX, oldScrollY ->
            if (isStartFling) {
                totalDy = 0
                isStartFling = false
            }
            // TOP SCROLL
            if (scrollY == 0) {
                // refreshLayout.setEnabled(true);
            }
            // BOTTOM SCROLL
            if (scrollY == (getChildAt(0).measuredHeight - v.measuredHeight)) {
//            if (scrollY ==headView!!.measuredHeight) {
                // 当滑动了headView的高度时，就重置
                Log.e("ZEEJ", "DE=${getChildAt(0).measuredHeight - v.measuredHeight}")
                Log.e("ZEEJ", "DE=${headView!!.measuredHeight}")
                dispatchChildFling()
            }
            //在RecyclerView fling情况下，记录当前RecyclerView在y轴的偏移
            totalDy += scrollY - oldScrollY
        })
    }

    private fun dispatchChildFling() {
        if (velocityY != 0) {
            val splineFlingDistance = mFilingHelper.getSplineFlingDistance(velocityY)
            if (splineFlingDistance > totalDy) {
                flingChild(mFilingHelper.getVelocityByDistance(splineFlingDistance - totalDy))
            }
        }
        totalDy = 0
        velocityY = 0
    }

    private fun flingChild(velocityByDistance: Int) {
        val childRecyclerView = contentView?.run { getChildRecyclerView(this) }
        childRecyclerView?.fling(0, velocityByDistance)
    }

    private fun getChildRecyclerView(viewGroup: ViewGroup): RecyclerView? {
        for (i in 0 until viewGroup.childCount) {
            val view = viewGroup.getChildAt(i)
            if (view is RecyclerView && view.javaClass == NestRecyclerView::class.java) {
                return viewGroup.getChildAt(i) as RecyclerView
            } else if (viewGroup.getChildAt(i) is ViewGroup) {
                val childRecyclerView: ViewGroup? =
                    getChildRecyclerView(viewGroup.getChildAt(i) as ViewGroup)
                if (childRecyclerView is RecyclerView) {
                    return childRecyclerView
                }
            }
            continue
        }
        return null
    }


    // 1.fling时，记录速度velocityY 和状态isStartFling
    override fun fling(velocityY: Int) {
        super.fling(velocityY)
        if (velocityY <= 0) {
            this.velocityY = 0
        } else {
            isStartFling = true
            this.velocityY = velocityY
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        contentView = getChildAt(0).run {
            this as? ViewGroup
        }?.getChildAt(1).run {
            this as? ViewGroup
        }

        headView = getChildAt(0).run {
            this as? ViewGroup
        }?.getChildAt(0).run {
            this as? ViewGroup
        }

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e("xxxxxxxxxxxx", "contentView= ${contentView.hashCode()}")

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (changed) {
            post { resize() }
        }
    }

    private fun resize() {
        contentView?.let {
            // 调整contentView高度为夫容器高度，使之填充布局，避免父容器滚动出现空白
            val layoutParams = it.layoutParams
            layoutParams.height = measuredHeight
            Log.e("xxxxxxxxxxxx", "measuredHeight= ${measuredHeight}")
            it.layoutParams = layoutParams
        }

    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        //super.onNestedPreScroll(target, dx, dy, consumed, type)
        val hideTop = dy > 0 && scrollY < headView!!.measuredHeight
        if (hideTop) {
            scrollBy(0, dy)
            consumed[1] = dy // 可以做视察效果
//            consumed[1] = dy * 0.2f.toInt()
        }


    }

}

fun fun003() {
    var a = 1
    var b = 2
    println("前：a = $a b= $b")
    a = b.also { b = a }
    println("后：a = $a b=$b")
}