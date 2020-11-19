package com.bugull.android.ext.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bugull.android.ext.R
import kotlinx.android.synthetic.main.item_swipe_card.view.*

data class SlideCard<T>(val position: Int, val data: T)

data class Girl(val name: String, val url: String)

fun slideCards(count: Int) = (0 until count).map {
    SlideCard(
        it, Girl(
            "girl-$it",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595434963213&di=5d07d9de35f42c16238c3076119a6e98&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fmobile%2F2018-12-13%2F5c120783eba2b.jpg"
        )
    )
}

class SlideAdapter(val list: MutableList<SlideCard<Girl>>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_swipe_card, parent, false)
        return object : RecyclerView.ViewHolder(view) {}
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val girl = list[position].data
        holder.itemView.tvName.text = girl.name
        holder.itemView.iv.setImageResource(R.mipmap.ic_launcher)

    }

}