package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.dashboardmodel.LatestEditionImg
import com.squareup.picasso.Picasso

class LatestEditionAdapter(
    val context: Context,
    var onItemClickListener: LatestEditionListOnItemClickListener
) : RecyclerView.Adapter<LatestEditionAdapter.classViewHolder>() {
    private val inflater: LayoutInflater
    var latesteditionList: ArrayList<LatestEditionImg> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface LatestEditionListOnItemClickListener {
        fun latesteditionListOnClick(position: Int, mLatesteditionList: ArrayList<LatestEditionImg>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LatestEditionAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.budget_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: LatestEditionAdapter.classViewHolder, position: Int) {
        with(holder) {
            Picasso.get()
                .load(latesteditionList[position].banneroffre)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .placeholder(R.drawable.slider_image)
                .into(imgBudget)


            itemView.setOnClickListener {

                onItemClickListener.latesteditionListOnClick(position, latesteditionList, it)

            }
        }
    }

    fun updateData(mLatesteditionList: List<LatestEditionImg>) {
        latesteditionList = mLatesteditionList as ArrayList<LatestEditionImg>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return latesteditionList.size
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgBudget: ImageView

        init {
            imgBudget = itemView.findViewById(R.id.imgBudget)
        }
    }

}