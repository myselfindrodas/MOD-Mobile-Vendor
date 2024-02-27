package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.CategoryListItemData

class CategoryListItemAdapter(val context: Context, var onItemClickListener: CategoryListItemOnItemClickListener)
    : RecyclerView.Adapter<CategoryListItemAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var categoryItems: ArrayList<CategoryListItemData> = arrayListOf()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface CategoryListItemOnItemClickListener {
        fun categoryListItemOnClick(position: Int, list: ArrayList<CategoryListItemData>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryListItemAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.category_list_items, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryListItemAdapter.classViewHolder, position: Int) {
        with(holder) {
            if (categoryItems[position].tvInfoCategory.isNullOrEmpty()) {
                tvInfoCategory.text = " "
            } else {
                tvInfoCategory.text = categoryItems[position].tvInfoCategory
            }
            if (categoryItems[position].listName.contains("FeaturesList")) {
                ivDot.setBackgroundResource(R.drawable.black_round)
            } else {
                ivDot.setBackgroundResource(R.drawable.blue_round)
            }
        }
    }

    fun updateData( mCategoryItems: List<CategoryListItemData>) {
        categoryItems = mCategoryItems as ArrayList<CategoryListItemData>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categoryItems.size
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvInfoCategory: TextView
        var ivDot: ImageView

        init {
            tvInfoCategory = itemView.findViewById(R.id.tvInfoCategory)
            ivDot = itemView.findViewById(R.id.ivDot)
        }
    }
}