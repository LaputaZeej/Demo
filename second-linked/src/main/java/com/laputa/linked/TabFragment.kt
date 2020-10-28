package com.laputa.linked

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_tab.*
import kotlinx.android.synthetic.main.item.view.*
import kotlin.random.Random

class TabFragment : Fragment() {

    companion object {
        private const val TITLE = "title"
        fun newInstance(title: String): TabFragment {
            val args = Bundle()
            args.putString(TITLE, title)
            val fragment = TabFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_tab, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    private fun initData() {
        val list = (1..20 + Random.nextInt(50)).map {
            "item -> $it"
        }
        recycle.layoutManager = LinearLayoutManager(requireContext())
        recycle.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                return object : RecyclerView.ViewHolder(
                    LayoutInflater.from(requireContext()).inflate(R.layout.item, parent, false)
                ) {}
            }

            override fun getItemCount(): Int = list.size

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                holder.itemView.title_fragment.text = list[position]
            }

        }
    }
}