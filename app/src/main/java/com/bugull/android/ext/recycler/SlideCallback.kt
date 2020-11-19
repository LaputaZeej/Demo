package com.bugull.android.ext.recycler

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SlideCallback(
    private val adapter: SlideAdapter,
    dragDirs: Int = 0,
    swipeDirs: Int = 0b1111
) :
    ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    private val list: MutableList<SlideCard<Girl>> = adapter.list

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val card = list.removeAt(viewHolder.layoutPosition)
        list.add(0, card)
        adapter.notifyDataSetChanged()
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val maxDistance = recyclerView.width * 0.5f.toDouble()
        val distance = Math.sqrt(dX * dX + dY * dY.toDouble())
        var fraction = distance / maxDistance

        if (fraction > 1) {
            fraction = 1.0
        }

        // 显示的个数  4个

        // 显示的个数  4个
        val itemCount = recyclerView.childCount

        for (i in 0 until itemCount) {
            val view = recyclerView.getChildAt(i)
            val level = itemCount - i - 1
            if (level > 0) {
                if (level < SlideConfig.MAX_SHOW_COUNT - 1) {
                    view.translationY =
                        ((SlideConfig.TRANS_Y_GAP * level - fraction * SlideConfig.TRANS_Y_GAP).toFloat())
                    view.scaleX =
                        ((1 - SlideConfig.SCALE_GAP * level + fraction * SlideConfig.SCALE_GAP).toFloat())
                    view.scaleY =
                        ((1 - SlideConfig.SCALE_GAP * level + fraction * SlideConfig.SCALE_GAP).toFloat())
                }
            }
        }
    }

    override fun getAnimationDuration(
        recyclerView: RecyclerView,
        animationType: Int,
        animateDx: Float,
        animateDy: Float
    ): Long {
        return 100
    }
}