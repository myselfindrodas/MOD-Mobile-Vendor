package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.stockmodel.Data
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.squareup.picasso.Picasso

class RelatedProductListAdapter(val context: Context, var onItemClickListener: RelatedProductItemClickListener)
    : RecyclerView.Adapter<RelatedProductListAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var relatedProductList: ArrayList<Data> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface RelatedProductItemClickListener {
        fun relatedProductOnClick(position: Int, list: ArrayList<Data>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RelatedProductListAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.related_product_list_item, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RelatedProductListAdapter.classViewHolder,
        position: Int
    ) {
        with(holder){
            if (relatedProductList[position].originalImage1==null) {
                imageOfPhone.setImageResource(R.drawable.phone_image)
            } else {
                Picasso.get()
                    .load(relatedProductList[position].originalImage1)
                    .error(R.drawable.modmobileph)
                    .placeholder(R.drawable.phone_image)
                    .into(imageOfPhone)
            }

            if (Shared_Preferences.getUserType().equals("Retailer")){

                if (relatedProductList[position].retPrice.toString().isNullOrEmpty()) {
                    tvOffOnMobile.text = "0 %"
                } else {
                    val pricedifference = relatedProductList[position].mRP.toFloat()
                        .minus(relatedProductList[position].retPrice.toFloat())
                    val offpercent = (pricedifference.div(relatedProductList[position].mRP.toFloat()) * 100)
                    tvOffOnMobile.text = String.format("%.2f", offpercent) + " %"
                }


                if (relatedProductList[position].retPrice.toString().isNullOrEmpty()) {
                    tvOffPriceOfItem.text = "₹ 0"
                } else {
                    tvOffPriceOfItem.text = "₹ " + relatedProductList[position].retPrice.toString()
                }


                tvSavePriceOfItem.text = "Save ₹ " + relatedProductList[position].mRP.toFloat()
                    .minus(relatedProductList[position].retPrice.toFloat())

            }else if (Shared_Preferences.getUserType().equals("Distributer")){

                if (relatedProductList[position].retPrice.toString().isNullOrEmpty()) {
                    tvOffOnMobile.text = "0 %"
                } else {
                    val pricedifference = relatedProductList[position].mRP.toFloat()
                        .minus(relatedProductList[position].retPrice.toFloat())
                    val offpercent = (pricedifference.div(relatedProductList[position].mRP.toFloat()) * 100)
                    tvOffOnMobile.text = String.format("%.2f", offpercent) + " %"
                }


                if (relatedProductList[position].distPrice.toString().isNullOrEmpty()) {
                    tvOffPriceOfItem.text = "₹ 0"
                } else {
                    tvOffPriceOfItem.text = "₹ " + relatedProductList[position].distPrice.toString()
                }



                tvSavePriceOfItem.text = "Save ₹ " + relatedProductList[position].mRP.toFloat()
                    .minus(relatedProductList[position].distPrice.toFloat())

            }





            if (relatedProductList[position].model.isNullOrEmpty()) {
                itemName.text = " "
            } else {
                itemName.text = relatedProductList[position].model
            }





            if (relatedProductList[position].mRP.isNullOrEmpty()) {
                tvRealPriceOfItem.text = "₹ 0 "
            } else {
                tvRealPriceOfItem.text = "₹ " + relatedProductList[position].mRP
            }




            itemView.setOnClickListener {
                onItemClickListener.relatedProductOnClick(position, relatedProductList, it)

            }

        }
    }

    override fun getItemCount(): Int {
        return relatedProductList.size
    }

    fun updateData( mRelatedProductList: List<Data>) {
        relatedProductList = mRelatedProductList as ArrayList<Data>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageOfPhone: ImageView
        var tvOffOnMobile: TextView
        var itemName: TextView
        var tvOffPriceOfItem: TextView
        var tvRealPriceOfItem: TextView
        var tvSavePriceOfItem: TextView

        init {
            imageOfPhone = itemView.findViewById(R.id.imageOfPhone)
            tvOffOnMobile = itemView.findViewById(R.id.tvOffOnMobile)
            itemName = itemView.findViewById(R.id.itemName)
            tvOffPriceOfItem = itemView.findViewById(R.id.tvOffPriceOfItem)
            tvRealPriceOfItem = itemView.findViewById(R.id.tvRealPriceOfItem)
            tvSavePriceOfItem = itemView.findViewById(R.id.tvSavePriceOfItem)
        }
    }

}