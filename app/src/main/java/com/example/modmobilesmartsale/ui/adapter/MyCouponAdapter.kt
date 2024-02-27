package com.example.modmobilesmartsale.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.couponmastermodel.Data


class MyCouponAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MyCouponAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var mycouponModelArrayList: ArrayList<Data> = ArrayList()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onClick(
            position: Int,
            view: View,
            mMycouponModelArrayList: ArrayList<Data>
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_couponsitem, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {


            tvTitle.text = "Get ₹" + mycouponModelArrayList[position].couponAmt + " Off on order"
//            tvDescription.text = mycouponModelArrayList[position].description
            tvCode.text = mycouponModelArrayList[position].couponCode

            tvCode.setOnClickListener {

//                val clipboard: ClipboardManager? = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
//                val clip = ClipData.newPlainText(label.toString(), mycouponModelArrayList[position].couponCode)
//                clipboard?.setPrimaryClip(clip)

                onItemClickListener.onClick(position, it, mycouponModelArrayList)

                Toast.makeText(ctx, "Copy Coupon "+mycouponModelArrayList[position].couponCode, Toast.LENGTH_SHORT).show()
            }


        }
    }

    fun updateData(mMycouponModelArrayList: List<Data>) {
        mycouponModelArrayList = mMycouponModelArrayList as ArrayList<Data>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mycouponModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvTitle: TextView
        var tvDescription: TextView
        var tvCode: TextView


        init {

            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDescription = itemView.findViewById(R.id.tvDescription)
            tvCode = itemView.findViewById(R.id.tvCode)

        }
    }
}