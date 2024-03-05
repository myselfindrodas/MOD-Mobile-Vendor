package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.stockmodel.Data
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.squareup.picasso.Picasso

class CategoryPhoneAdapter(
    val context: Context,
    var onItemClickListener: CategoryPhoneOnItemClickListener
) : RecyclerView.Adapter<CategoryPhoneAdapter.classViewHolder>() {

    private val inflater: LayoutInflater
    var dayDealsList: ArrayList<Data> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface CategoryPhoneOnItemClickListener {
        fun CategoryOnClick(position: Int, list: ArrayList<Data>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryPhoneAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.day_deals_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryPhoneAdapter.classViewHolder, position: Int) {
        with(holder) {
            if (dayDealsList[position].originalImage1 == null) {
                imgPhone.setImageResource(R.drawable.phone_image)
            } else {

                Picasso.get()
                    .load(dayDealsList[position].originalImage1)
                    .error(R.drawable.modmobileph)
                    .placeholder(R.drawable.phone_image)
                    .into(imgPhone)

            }

            if (Shared_Preferences.getUserType().equals("Retailer")){

                if (dayDealsList[position].retPrice.toString().isNullOrEmpty()) {
                    tvOff.text = "0 %"
                } else {
                    val pricedifference = dayDealsList[position].mRP.toFloat()
                        .minus(dayDealsList[position].retPrice.toFloat())
                    val offpercent = (pricedifference.div(dayDealsList[position].mRP.toFloat()) * 100)
                    tvOff.text = String.format("%.2f", offpercent) + " %"
                }


                if (dayDealsList[position].retPrice.toString().isNullOrEmpty()) {
                    tvOffPrice.text = "₹ 0"
                } else {
                    tvOffPrice.text = "₹ " + dayDealsList[position].retPrice.toString()
                }

                tvSavePrice.text = "Save ₹ " + dayDealsList[position].mRP.toFloat().minus(dayDealsList[position].retPrice.toFloat())



            }else if (Shared_Preferences.getUserType().equals("Distributer")){

                if (dayDealsList[position].distPrice.toString().isNullOrEmpty()) {
                    tvOff.text = "0 %"
                } else {
                    val pricedifference = dayDealsList[position].mRP.toFloat()
                        .minus(dayDealsList[position].distPrice.toFloat())
                    val offpercent = (pricedifference.div(dayDealsList[position].mRP.toFloat()) * 100)
                    tvOff.text = String.format("%.2f", offpercent) + " %"
                }


                if (dayDealsList[position].distPrice.toString().isNullOrEmpty()) {
                    tvOffPrice.text = "₹ 0"
                } else {
                    tvOffPrice.text = "₹ " + dayDealsList[position].distPrice.toString()
                }


                tvSavePrice.text = "Save ₹ " + dayDealsList[position].mRP.toFloat().minus(dayDealsList[position].distPrice.toFloat())


            }





            if (dayDealsList[position].model.isNullOrEmpty()) {
                tvPhoneName.text = " "
            } else {
                tvPhoneName.text = dayDealsList[position].model
            }



            if (dayDealsList[position].mRP.isNullOrEmpty()) {
                tvRealPrice.text = "₹ 0 "
            } else {
                tvRealPrice.text = "₹ " + dayDealsList[position].mRP
            }



            llDayDealsInfo.setBackgroundResource(R.drawable.grey_lower_round_box)
//            if (dayDealsList[position].listName.contains("Deals Of The Day") || dayDealsList[position].listName.contains("Android Phones")) {
//                llDayDealsInfo.setBackgroundResource(R.drawable.grey_lower_round_box)
//            }else if (dayDealsList[position].listName.contains("I Phones")) {
//            llDayDealsInfo.setBackgroundResource(R.drawable.blue_lower_round_box)
//        }
            itemView.setOnClickListener {
                onItemClickListener.CategoryOnClick(position, dayDealsList, it)

            }
        }
    }

    fun updateData(mDayDealsList: List<Data>) {
        dayDealsList = mDayDealsList as ArrayList<Data>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dayDealsList.size
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhone: ImageView
        var tvOff: TextView
        var tvPhoneName: TextView
        var tvOffPrice: TextView
        var tvRealPrice: TextView
        var tvSavePrice: TextView
        var llDayDealsInfo: LinearLayout

        init {
            imgPhone = itemView.findViewById(R.id.imgPhone)
            tvOff = itemView.findViewById(R.id.tvOff)
            tvPhoneName = itemView.findViewById(R.id.tvPhoneName)
            tvOffPrice = itemView.findViewById(R.id.tvOffPrice)
            tvRealPrice = itemView.findViewById(R.id.tvRealPrice)
            tvSavePrice = itemView.findViewById(R.id.tvSavePrice)
            llDayDealsInfo = itemView.findViewById(R.id.llDayDealsInfo)
        }
    }

}