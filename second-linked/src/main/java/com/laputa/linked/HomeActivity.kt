package com.laputa.linked

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.laputa.linked.R.layout.activity_home
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item.view.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {

    companion object {
        val TABS = listOf<String>("主页", "商城", "我的")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_home)

        view_pager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = TABS.size

            override fun createFragment(position: Int): Fragment {
                return TabFragment.newInstance(TABS[position])
            }
        }
        val tabLayoutMediator = TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = TABS[position]
        }
        tabLayoutMediator.attach()

        initData()

        swipe.setOnRefreshListener {
            MainScope().launch {
                delay(3000)
                initData()
                swipe.isRefreshing = false
            }
        }


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }


    private fun initData() {
        val list = (1..3 + Random.nextInt(2)).map {
            "item -> $it-${System.currentTimeMillis()}"
        }
        fix_recycler.layoutManager = LinearLayoutManager(this)
        fix_recycler.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                return object : RecyclerView.ViewHolder(
                    LayoutInflater.from(this@HomeActivity).inflate(R.layout.item, parent, false)
                ) {}
            }

            override fun getItemCount(): Int = list.size

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                holder.itemView.title_fragment.text = list[position]
            }

        }
    }
}