package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.dashboardmodel.LatestEditionImg

class VerticalLatestEditionAdapter(
    val context: Context,
    var onItemClickListener: VerticalLatestEditionListOnItemClickListener
): RecyclerView.Adapter<VerticalLatestEditionAdapter.classViewHolder>() {

    private val inflater: LayoutInflater
    var verticallatesteditionList: ArrayList<LatestEditionImg> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface VerticalLatestEditionListOnItemClickListener {
        fun latesteditionListOnClick(position: Int, mVerticalLatesteditionList: ArrayList<LatestEditionImg>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VerticalLatestEditionAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.vertical_buget_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: VerticalLatestEditionAdapter.classViewHolder,
        position: Int
    ) {
        with(holder) {
            com.squareup.picasso.Picasso.get()
                .load(verticallatesteditionList[position].banneroffre)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .placeholder(R.drawable.slider_image)
                .into(imgVerticalBudget)

            itemView.setOnClickListener {

                onItemClickListener.latesteditionListOnClick(position, verticallatesteditionList, it)

            }
        }
    }

    override fun getItemCount(): Int {
        return verticallatesteditionList.size
    }

    fun updateData(mVerticalLatesteditionList: List<LatestEditionImg>) {
        verticallatesteditionList = mVerticalLatesteditionList as ArrayList<LatestEditionImg>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgVerticalBudget: ImageView

        init {
            imgVerticalBudget = itemView.findViewById(R.id.imgVerticalBudget)
        }
    }
}