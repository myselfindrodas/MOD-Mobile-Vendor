package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.DrawerData

class DrawerLayoutAdapter(val context: Context)
    : RecyclerView.Adapter<DrawerLayoutAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var items: ArrayList<DrawerData> = arrayListOf()

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DrawerLayoutAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.row_nav_drawer, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrawerLayoutAdapter.classViewHolder, position: Int) {
        with(holder) {
            if (items[position].icon == null) {
                navigation_icon.setImageResource(R.drawable.ic_home)
            } else {
                navigation_icon.setImageResource(items[position].icon)
            }
            if (items[position].title.isNullOrEmpty()) {
                navigation_title.text = " "
            } else {
                navigation_title.text = items[position].title
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData( mItems: List<DrawerData>) {
        items = mItems as ArrayList<DrawerData>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var navigation_icon: ImageView
        var navigation_title: TextView

        init {
            navigation_icon = itemView.findViewById(R.id.navigation_icon)
            navigation_title = itemView.findViewById(R.id.navigation_title)
        }
    }

}