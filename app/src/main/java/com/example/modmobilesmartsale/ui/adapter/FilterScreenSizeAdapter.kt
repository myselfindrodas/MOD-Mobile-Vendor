package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.FilterScreenSizeData

class FilterScreenSizeAdapter(val context: Context, var onItemClickListener: FilterScreenSizeClickListener)
    : RecyclerView.Adapter<FilterScreenSizeAdapter.classViewHolder>() {

    private val inflater: LayoutInflater
    var filter_screensize_list: ArrayList<FilterScreenSizeData> = arrayListOf()
    var selectedposition = -1


    init {
        inflater = LayoutInflater.from(context)
    }

    interface FilterScreenSizeClickListener {
        fun FilterScreenOnClick(position: Int, list: ArrayList<FilterScreenSizeData>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterScreenSizeAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.filter_screen_size_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterScreenSizeAdapter.classViewHolder, position: Int) {
        with(holder){
            if (filter_screensize_list[position].tvFilter.isNullOrEmpty()) {
                tvFilter.text = " "
            } else {
                tvFilter.text = filter_screensize_list[position].tvFilter
            }

            checkFilterScreenSize.isChecked = selectedposition == position

            checkFilterScreenSize.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked) {
                    try {
                        onItemClickListener.FilterScreenOnClick(position, filter_screensize_list, buttonView)
                        selectedposition = adapterPosition
                        notifyDataSetChanged()
                        onItemClickListener.FilterScreenOnClick(adapterPosition, filter_screensize_list, buttonView)


                    }catch (e : Exception){
                        Log.e("Exception", e.toString())
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return filter_screensize_list.size
    }

    fun updateData(mFilter_screensize_list: List<FilterScreenSizeData>) {
        filter_screensize_list = mFilter_screensize_list as ArrayList<FilterScreenSizeData>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvFilter: TextView
        var checkFilterScreenSize: RadioButton

        init {
            tvFilter = itemView.findViewById(R.id.tvFilter)
            checkFilterScreenSize = itemView.findViewById(R.id.checkFilterScreenSize)
        }
    }
}