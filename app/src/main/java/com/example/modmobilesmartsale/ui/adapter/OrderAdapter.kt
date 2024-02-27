package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.myoderlistmodel.Data
import com.squareup.picasso.Picasso

class OrderAdapter(val context: Context, var onItemClickListener: OrderItemClickListener)
    :RecyclerView.Adapter<OrderAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var orderItems: ArrayList<Data> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface OrderItemClickListener {
        fun orderItemOnClick(position: Int, list: ArrayList<Data>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.orders_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderAdapter.classViewHolder, position: Int) {
        with(holder) {
            Picasso.get()
                .load(orderItems[position].image1)
                .error(R.drawable.phone_image)
                .placeholder(R.drawable.phone_image)
                .into(ivMobile)

//            tvMobileName.text = orderItems[position].modelName
//            tvQuality.text = orderItems[position].itemTotal
//            tvStorage.text = orderItems[position].memory
//            tvDeliveryDate.text = orderItems[position].deliveryTime
//            tvOrderStatus.text = orderItems[position].status


            if (orderItems[position].modelName.isNullOrEmpty()) {
                tvMobileName.text = " "
            } else {
                tvMobileName.text = orderItems[position].modelName
            }
            if (orderItems[position].stockType.isNullOrEmpty()) {
                tvQuality.text = ""
            } else {
                tvQuality.text = orderItems[position].stockType
            }
            if (orderItems[position].memory.isNullOrEmpty()) {
                tvStorage.text = " "
            } else {
                tvStorage.text = orderItems[position].memory
            }
            if (orderItems[position].deliveryTime.isNullOrEmpty()) {
                tvDeliveryDate.text = " "
            } else {
                tvDeliveryDate.text = orderItems[position].deliveryTime
            }
            if (orderItems[position].status.isNullOrEmpty()) {
                tvOrderStatus.visibility = View.GONE
            } else {
                tvOrderStatus.text = orderItems[position].status
            }
            if (orderItems[position].status.equals("Cancel")) {
                tvOrderStatus.setBackgroundResource(R.drawable.red_box_radious_20)
            }else{
                tvOrderStatus.setBackgroundResource(R.drawable.green_box_radious_20)
            }
            itemView.setOnClickListener {
                if (orderItems[position].status.equals("Cancel")){
                    Toast.makeText(context, "This Order already cancelled", Toast.LENGTH_LONG).show()
                }else{
                    onItemClickListener.orderItemOnClick(position, orderItems, it)

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return orderItems.size
    }

    fun updateData( mOrderItems: List<Data>) {
        orderItems = mOrderItems as ArrayList<Data>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivMobile: ImageView
        var tvMobileName: TextView
        var tvQuality: TextView
        var tvStorage: TextView
        var tvDeliveryDate: TextView
        var tvOrderStatus: TextView
        init {
            ivMobile = itemView.findViewById(R.id.ivMobile)
            tvMobileName = itemView.findViewById(R.id.tvMobileName)
            tvQuality = itemView.findViewById(R.id.tvQuality)
            tvStorage = itemView.findViewById(R.id.tvStorage)
            tvDeliveryDate = itemView.findViewById(R.id.tvDeliveryDate)
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus)
        }
    }
}