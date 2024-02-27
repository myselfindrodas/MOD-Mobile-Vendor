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

class BudgetListAdapter(
    val context: Context,
    var onItemClickListener: BudgetListOnItemClickListener
) : RecyclerView.Adapter<BudgetListAdapter.classViewHolder>() {
    private val inflater: LayoutInflater
    var budgetList: ArrayList<ShopByBudgetImg> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface BudgetListOnItemClickListener {
        fun budgetListOnClick(position: Int, list: ArrayList<ShopByBudgetImg>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BudgetListAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.budget_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: BudgetListAdapter.classViewHolder, position: Int) {
        with(holder) {
            Picasso.get()
                .load(budgetList[position].shopBudgetUrl)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .placeholder(R.drawable.slider_image)
                .into(imgBudget)


            itemView.setOnClickListener {

                onItemClickListener.budgetListOnClick(position, budgetList, it)

            }
        }
    }

    fun updateData(mBudgetList: List<ShopByBudgetImg>) {
        budgetList = mBudgetList as ArrayList<ShopByBudgetImg>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return budgetList.size
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgBudget: ImageView

        init {
            imgBudget = itemView.findViewById(R.id.imgBudget)
        }
    }

}