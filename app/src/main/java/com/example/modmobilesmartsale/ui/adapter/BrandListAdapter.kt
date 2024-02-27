package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.dashboardmodel.TopBrand
import com.squareup.picasso.Picasso

class BrandListAdapter(val context: Context, var onItemClickListener: BrandListOnItemClickListener)
    :RecyclerView.Adapter<BrandListAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var brandList: ArrayList<TopBrand> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface BrandListOnItemClickListener {
        fun brandListOnClick(position: Int, list: ArrayList<TopBrand>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BrandListAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.brand_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandListAdapter.classViewHolder, position: Int) {
        with(holder){

            Glide.with(context)
                .load(brandList[position].imgUrl)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .placeholder(R.drawable.budget)
                .into(imgBrand)

            itemView.setOnClickListener {

                onItemClickListener.brandListOnClick(position, brandList, it)

            }
        }
    }

    fun updateData( mBrandList: List<TopBrand>) {
        brandList = mBrandList as ArrayList<TopBrand>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return brandList.size
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgBrand: ImageView

        init {
            imgBrand = itemView.findViewById(R.id.imgBrand)
        }
    }
}