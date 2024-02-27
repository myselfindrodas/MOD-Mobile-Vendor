package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.PlacedOrderListData

class PlacedOrderAdapter(val context: Context, var onItemClickListener: PlacedOrderItemClickListener)
    :RecyclerView.Adapter<PlacedOrderAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var placedOrderItems: ArrayList<PlacedOrderListData> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface PlacedOrderItemClickListener {
        fun placedOrderItemOnClick(position: Int, list: ArrayList<PlacedOrderListData>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlacedOrderAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.placed_order_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlacedOrderAdapter.classViewHolder, position: Int) {
        with(holder) {
            if (placedOrderItems[position].ivMobileImageInPlaceOrder==null) {
                ivMobileImageInPlaceOrder.setImageResource(R.drawable.budget)
            } else {
                ivMobileImageInPlaceOrder.setImageResource(placedOrderItems[position].ivMobileImageInPlaceOrder)
            }
            if (placedOrderItems[position].tvMobileNameInPlaceOrder.isNullOrEmpty()) {
                tvMobileNameInPlaceOrder.text = " "
            } else {
                tvMobileNameInPlaceOrder.text = placedOrderItems[position].tvMobileNameInPlaceOrder
            }
            if (placedOrderItems[position].tvMobileQualityInPlaceOrder.isNullOrEmpty()) {
                tvMobileQualityInPlaceOrder.text = " "
            } else {
                tvMobileQualityInPlaceOrder.text = placedOrderItems[position].tvMobileQualityInPlaceOrder
            }
            if (placedOrderItems[position].tvMobileStorageInPlaceOrder.isNullOrEmpty()) {
                tvMobileStorageInPlaceOrder.text = " "
            } else {
                tvMobileStorageInPlaceOrder.text = placedOrderItems[position].tvMobileStorageInPlaceOrder
            }
            if (placedOrderItems[position].tvMobileOffPriceInPlaceOrder.isNullOrEmpty()) {
                tvMobileOffPriceInPlaceOrder.text = " "
            } else {
                tvMobileOffPriceInPlaceOrder.text = placedOrderItems[position].tvMobileOffPriceInPlaceOrder
            }
            if (placedOrderItems[position].tvMobileRealPriceInPlaceOrder.isNullOrEmpty()) {
                tvMobileRealPriceInPlaceOrder.text = " "
            } else {
                tvMobileRealPriceInPlaceOrder.text = placedOrderItems[position].tvMobileRealPriceInPlaceOrder
            }
            if (placedOrderItems[position].tvSavePriceOnMobileInPlaceOrder.isNullOrEmpty()) {
                tvSavePriceOnMobileInPlaceOrder.text = " "
            } else {
                tvSavePriceOnMobileInPlaceOrder.text = placedOrderItems[position].tvSavePriceOnMobileInPlaceOrder
            }
            if (placedOrderItems[position].tvMobileQtyInPlaceOrder.isNullOrEmpty()) {
                tvMobileQtyInPlaceOrder.text = " "
            } else {
                tvMobileQtyInPlaceOrder.text = placedOrderItems[position].tvMobileQtyInPlaceOrder
            }
        }
    }

    override fun getItemCount(): Int {
        return placedOrderItems.size
    }

    fun updateData( mPlacedOrderItems: List<PlacedOrderListData>) {
        placedOrderItems = mPlacedOrderItems as ArrayList<PlacedOrderListData>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivMobileImageInPlaceOrder: ImageView
        var tvMobileNameInPlaceOrder: TextView
        var tvMobileQualityInPlaceOrder: TextView
        var tvMobileStorageInPlaceOrder: TextView
        var tvMobileOffPriceInPlaceOrder: TextView
        var tvMobileRealPriceInPlaceOrder: TextView
        var tvSavePriceOnMobileInPlaceOrder: TextView
        var tvMobileQtyInPlaceOrder: TextView

        init {
            ivMobileImageInPlaceOrder = itemView.findViewById(R.id.ivMobileImageInPlaceOrder)
            tvMobileNameInPlaceOrder = itemView.findViewById(R.id.tvMobileNameInPlaceOrder)
            tvMobileQualityInPlaceOrder = itemView.findViewById(R.id.tvMobileQualityInPlaceOrder)
            tvMobileStorageInPlaceOrder = itemView.findViewById(R.id.tvMobileStorageInPlaceOrder)
            tvMobileOffPriceInPlaceOrder = itemView.findViewById(R.id.tvMobileOffPriceInPlaceOrder)
            tvMobileRealPriceInPlaceOrder = itemView.findViewById(R.id.tvMobileRealPriceInPlaceOrder)
            tvSavePriceOnMobileInPlaceOrder = itemView.findViewById(R.id.tvSavePriceOnMobileInPlaceOrder)
            tvMobileQtyInPlaceOrder = itemView.findViewById(R.id.tvMobileQtyInPlaceOrder)
        }
    }

}