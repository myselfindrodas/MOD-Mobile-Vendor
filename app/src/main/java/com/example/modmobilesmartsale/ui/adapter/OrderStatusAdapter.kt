package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.OrderStatusData

class OrderStatusAdapter(val context: Context, var onItemClickListener: OrderStatusClickListener)
    : RecyclerView.Adapter<OrderStatusAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var orderStatusList: ArrayList<OrderStatusData> = arrayListOf()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface OrderStatusClickListener {
        fun orderStatusOnClick(position: Int, list: ArrayList<OrderStatusData>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderStatusAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.order_status, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderStatusAdapter.classViewHolder, position: Int) {
        with(holder) {
            if (orderStatusList[position].dateOfPlaced.isNullOrEmpty()) {
                dateOfPlaced.text = " "
            } else {
                dateOfPlaced.text = orderStatusList[position].dateOfPlaced
            }
            if (orderStatusList[position].orderStatus.isNullOrEmpty()) {
                orderStatus.text = " "
            } else {
                orderStatus.text = orderStatusList[position].orderStatus
            }

            if (position==orderStatusList.size-1){
                statusLine.visibility = View.GONE
            }else{
                statusLine.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return orderStatusList.size
    }

    fun updateData( mOrderStatusList: List<OrderStatusData>) {
        orderStatusList = mOrderStatusList as ArrayList<OrderStatusData>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dateOfPlaced: TextView
        var statusLine: View
        var orderStatus: TextView

        init {
            dateOfPlaced = itemView.findViewById(R.id.dateOfPlaced)
            statusLine = itemView.findViewById(R.id.statusLine)
            orderStatus = itemView.findViewById(R.id.orderStatus)

        }
    }
}