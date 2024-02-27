package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.dashboardmodel.CategoryInfo
import com.squareup.picasso.Picasso

class CategoryListAdapter(val context: Context, var onItemClickListener: CategoryListOnItemClickListener)
    :RecyclerView.Adapter<CategoryListAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var categoryList: ArrayList<CategoryInfo> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface CategoryListOnItemClickListener {
        fun categoryListOnClick(position: Int, list: ArrayList<CategoryInfo>, view: View, type:String)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryListAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.category_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryListAdapter.classViewHolder, position: Int) {
        with(holder){
            if (categoryList[position].stockName.isNullOrEmpty()) {
                tvItemName.text = " "
            } else {
                tvItemName.text = categoryList[position].stockName
            }

            if (categoryList[position].image.isNullOrEmpty()){
                ivItemImage.setImageResource(R.drawable.icon_star)
            }else{
                Picasso.get()
                    .load(categoryList[position].image)
                    .error(R.drawable.icon_star)
                    .placeholder(R.drawable.icon_star)
                    .into(ivItemImage)
            }



            infoCategory.setOnClickListener {
                onItemClickListener.categoryListOnClick(position, categoryList, it, "info")
            }


            itemView.setOnClickListener {
                onItemClickListener.categoryListOnClick(position, categoryList, it, "details")
            }
        }
    }

    fun updateData( mCategoryList: List<CategoryInfo>) {
        categoryList = mCategoryList as ArrayList<CategoryInfo>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItemName: TextView
        var infoCategory: ImageView
        var ivItemImage: ImageView

        init {
            tvItemName = itemView.findViewById(R.id.tvItemName)
            infoCategory = itemView.findViewById(R.id.infoCategory)
            ivItemImage = itemView.findViewById(R.id.ivItemImage)
        }
    }

}