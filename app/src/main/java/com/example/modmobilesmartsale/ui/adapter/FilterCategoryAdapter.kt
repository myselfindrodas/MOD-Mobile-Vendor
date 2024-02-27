package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.FilterCategoryData

class FilterCategoryAdapter(val context: Context, var onItemClickListener: OnItemClickListener)
    :RecyclerView.Adapter<FilterCategoryAdapter.classViewHolder>() {

    private val inflater: LayoutInflater
    var filter_category_list: ArrayList<FilterCategoryData> = arrayListOf()
    var selectedposition = -1

    init {
        inflater = LayoutInflater.from(context)
    }

    interface OnItemClickListener {
        fun OnClick(position: Int, list: ArrayList<FilterCategoryData>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilterCategoryAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.filter_category_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterCategoryAdapter.classViewHolder, position: Int) {
        with(holder) {
            if (filter_category_list[position].imgCategory == null) {
                imgCategory.setImageResource(R.drawable.budget)
            } else {
                imgCategory.setImageResource(filter_category_list[position].imgCategory)
            }

            checkFilter.isChecked = selectedposition == position

            checkFilter.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked) {
                    try {
                        onItemClickListener.OnClick(position, filter_category_list, buttonView)
                        selectedposition = adapterPosition
                        notifyDataSetChanged()
                        onItemClickListener.OnClick(adapterPosition, filter_category_list, buttonView)
                    }catch (e : Exception){
                        Log.e("Exception", e.toString())
                    }
                }
            }
        }
    }

        override fun getItemCount(): Int {
            return filter_category_list.size
        }

    fun updateData(mFilter_category_list: List<FilterCategoryData>) {
        filter_category_list = mFilter_category_list as ArrayList<FilterCategoryData>
        notifyDataSetChanged()
    }


    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var imgCategory: ImageView
            var checkFilter: RadioButton

            init {
                imgCategory = itemView.findViewById(R.id.imgCategory)
                checkFilter = itemView.findViewById(R.id.checkFilter)
        }
    }
}