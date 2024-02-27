package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.dashboardmodel.BannerData
import com.squareup.picasso.Picasso


class ProductImageSlidingAdapter(var context: Context, var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ProductImageSlidingAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater
    var imageslist: ArrayList<BannerData> = ArrayList()


    interface OnItemClickListener {
        fun onClick(position: Int, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductImageSlidingAdapter.MyViewHolder {
        val view = inflater.inflate(R.layout.fragment_sliding, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductImageSlidingAdapter.MyViewHolder, position: Int) {

        with(holder) {

            Picasso.get()
                .load(imageslist[position].bannerUrl)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .placeholder(R.drawable.slider_image)
                .into(sliding_image)

        }
    }

    override fun getItemCount(): Int {
        return imageslist.size
    }

//
//    override fun getItemCount() = imageslist.size

    fun updateImageData(mImagesList: List<BannerData>) {
        imageslist = mImagesList as ArrayList<BannerData>
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sliding_image: ImageView

        init {
            sliding_image = itemView.findViewById(R.id.sliding_image)
        }
    }


    init {
        inflater = LayoutInflater.from(context)
    }


}