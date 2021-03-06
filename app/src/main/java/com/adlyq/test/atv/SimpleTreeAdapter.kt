package com.adlyq.test.atv

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SimpleTreeAdapter: TreeAdapter<String, SimpleTreeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : TreeAdapter.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = ViewHolder(TextView(parent.context))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder.itemView as TextView).apply{
            text = line(nodes[position].level) + nodes[position].src
            textSize = 24f
            setOnClickListener {
                nodes[position].explained = !nodes[position].explained
                flash()
            }
        }
    }

    private fun line(i: Int): String{
        var str = ""
        for (m in 0 until i)
            str += '.'
        return str
    }
}