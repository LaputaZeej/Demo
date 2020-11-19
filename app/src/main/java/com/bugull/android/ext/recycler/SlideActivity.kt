package com.bugull.android.ext.recycler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.bugull.android.ext.R
import kotlinx.android.synthetic.main.activity_slide.*

class SlideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        // 初始化数据
        SlideConfig.initConfig(this)
        slide_recycle.layoutManager = SlideLayoutManager()
        val slideAdapter = SlideAdapter(slideCards(10).toMutableList())
        slide_recycle.adapter = slideAdapter
        ItemTouchHelper(SlideCallback(slideAdapter)).attachToRecyclerView(slide_recycle)
    }
}