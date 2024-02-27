package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.dashboardmodel.BannerData
import com.example.modmobilesmartsale.data.model.dashboardmodel.TopBrand
import com.squareup.picasso.Picasso


class SlidingImagesURLAdapter(var context: Context, var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<SlidingImagesURLAdapter.MyViewHolder>() {

    private val inflater: LayoutInflater
    var imageslist: ArrayList<BannerData> = ArrayList()


    interface OnItemClickListener {
        fun onClick(position: Int, list: ArrayList<BannerData>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SlidingImagesURLAdapter.MyViewHolder {
        val view = inflater.inflate(R.layout.fragment_sliding, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlidingImagesURLAdapter.MyViewHolder, position: Int) {

        with(holder) {

            Picasso.get()
                .load(imageslist[position].bannerUrl)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .placeholder(R.drawable.slider_image)
                .into(sliding_image)

            itemView.setOnClickListener {

                onItemClickListener.onClick(position, imageslist, it)

            }

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