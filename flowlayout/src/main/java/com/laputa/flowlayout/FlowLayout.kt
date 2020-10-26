package com.laputa.flowlayout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup

class FlowLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val mVerticalSpace = 10
    private val mHorizontalSpace = 10
    private val mAllLineViews: MutableList<List<View>> = mutableListOf()
    private val mLineHeights = mutableListOf<Int>()

    private fun clearLines() {
        mAllLineViews.clear()
        mLineHeights.clear()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        clearLines()
        var lineViews = mutableListOf<View>()
        var lineWidthUsed = 0
        var lineHeight = 0
        val mWidth = MeasureSpec.getSize(widthMeasureSpec)
        val mHeight = MeasureSpec.getSize(heightMeasureSpec)
        var ownerWidth = 0
        var ownerHeight = 0
        (0 until childCount).forEach { index ->
            val childView = getChildAt(index)
            Log.i(TAG, "onMeasure view id = ${childView.hashCode()}")
            if (childView.visibility != View.GONE) {
                val layoutParams = childView.layoutParams
                val childWidthMeasureSpec = getChildMeasureSpec(
                    widthMeasureSpec,
                    paddingLeft + paddingRight,
                    layoutParams.width
                )
                val childHeightMeasureSpec = getChildMeasureSpec(
                    heightMeasureSpec,
                    paddingTop + paddingBottom,
                    layoutParams.height
                )
                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec)
                val childMeasuredHeight = childView.measuredHeight
                val childMeasureWidth = childView.measuredWidth
                val temp = lineWidthUsed + childMeasureWidth + mHorizontalSpace
                // 是否要换行
                if (temp > mWidth) {
                    // 一旦换行，记录行和行高，用于layout
                    mAllLineViews.add(lineViews)
                    mLineHeights.add(lineHeight)
                    Log.i(TAG, "onMeasure 换行= ${lineViews.size} 行数=${mAllLineViews.size}")
                    //
                    ownerHeight += lineHeight + mVerticalSpace
                    ownerWidth = Math.max(ownerWidth, lineWidthUsed + mHorizontalSpace)
                    lineViews = mutableListOf() // 不能用clear（）
                    lineWidthUsed = 0
                    lineHeight = 0

                }
                lineViews.add(childView)
                lineWidthUsed += childMeasureWidth + mHorizontalSpace
                lineHeight = Math.max(lineHeight, childMeasuredHeight)
                // 处理最后一行数据
                if (index == childCount - 1) {
                    mAllLineViews.add(lineViews)
                    mLineHeights.add(lineHeight)
                    ownerHeight += lineHeight + mVerticalSpace
                    ownerWidth = Math.max(ownerWidth, lineWidthUsed + mHorizontalSpace)
                    Log.i(TAG, "onMeasure 换行= ${lineViews.size} 行数=${mAllLineViews.size}")
                }
            }
        }
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val finalWidth = when (widthMode) {
            MeasureSpec.EXACTLY -> mWidth
            else -> ownerWidth
        }
        val finalHeight = when (heightMode) {
            MeasureSpec.EXACTLY -> mHeight
            else -> ownerHeight
        }
        setMeasuredDimension(finalWidth, finalHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val lineCount = mAllLineViews.size
        var currentLeft = 0
        var currentTop = 0
        (0 until lineCount).forEach { index ->
            val list = mAllLineViews[index]
            val lineHeight = mLineHeights[index]
            Log.i(TAG, "line index = $index")
            list.forEach {
                Log.i(TAG, "line index view = ${it.hashCode()}")
                val left = currentLeft
                val top = currentTop
                val right = left + it.measuredWidth
                val bottom = top + it.measuredHeight
                it.layout(left, top, right, bottom)
                currentLeft = right + mHorizontalSpace
            }
            currentLeft = paddingLeft
            currentTop += lineHeight + mVerticalSpace
        }
    }

    companion object {
        private const val TAG = "FlowLayout_"
    }
}