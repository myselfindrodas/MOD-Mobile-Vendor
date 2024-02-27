package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.dashboardmodel.ShopByBudgetImg
import com.squareup.picasso.Picasso

class VerticalBudgetListAdapter(
    val context: Context,
    var onItemClickListener: VerticalBudgetListOnItemClickListener
):RecyclerView.Adapter<VerticalBudgetListAdapter.classViewHolder>() {

    private val inflater: LayoutInflater
    var verticalBudgetList: ArrayList<ShopByBudgetImg> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface VerticalBudgetListOnItemClickListener {
        fun verticalBudgetListOnClick(position: Int, list: ArrayList<ShopByBudgetImg>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VerticalBudgetListAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.vertical_buget_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: VerticalBudgetListAdapter.classViewHolder,
        position: Int
    ) {
        with(holder) {
            Picasso.get()
                .load(verticalBudgetList[position].shopBudgetUrl)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .placeholder(R.drawable.slider_image)
                .into(imgVerticalBudget)

            itemView.setOnClickListener {

                onItemClickListener.verticalBudgetListOnClick(position, verticalBudgetList, it)

            }
        }
    }

    fun updateData(mVerticalBudgetList: List<ShopByBudgetImg>) {
        verticalBudgetList = mVerticalBudgetList as ArrayList<ShopByBudgetImg>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return verticalBudgetList.size
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgVerticalBudget: ImageView

        init {
            imgVerticalBudget = itemView.findViewById(R.id.imgVerticalBudget)
        }
    }
}