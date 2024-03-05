package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.squareup.picasso.Picasso

class SlidingPhonesImageAdapter(val context: Context, var onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<SlidingPhonesImageAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var images: ArrayList<String> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SlidingPhonesImageAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.sliding_phones, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlidingPhonesImageAdapter.classViewHolder, position: Int) {
        with(holder) {

            Picasso.get()
                .load(images[position])
                .error(R.drawable.modmobileph)
                .placeholder(R.drawable.loader)
                .into(sliding_phones_image)

        }    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun updateImageData(mImages: ArrayList<String>) {
        images = mImages
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var sliding_phones_image: ImageView

        init {
            sliding_phones_image = itemView.findViewById(R.id.sliding_phones_image)
        }
    }
}