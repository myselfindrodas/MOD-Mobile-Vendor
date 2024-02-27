package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.storagecolormodel.Data

class ColorListAdapter(val context: Context, var onItemClickListener: ColorItemClickListener)
    :RecyclerView.Adapter<ColorListAdapter.classViewHolder>()  {

    private val inflater : LayoutInflater
    var colorList: ArrayList<Data> = ArrayList()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface ColorItemClickListener {
        fun colorListOnClick(position: Int, list: ArrayList<Data>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ColorListAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.color_list_item, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorListAdapter.classViewHolder, position: Int) {
        with(holder){
//            if (colorList[position].color == null) {
//                ivPhoneImage.setImageResource(R.drawable.phone_image)
//            } else {
//                ivPhoneImage.setImageResource(R.drawable.phone_image)
//            }

            if (colorList[position].color_code==null){

                ivPhoneImage.setBackgroundColor(Color.parseColor("#FFFFF"))


            }else{
                ivPhoneImage.setBackgroundColor(Color.parseColor(colorList[position].color_code))

            }

            if (colorList[position].color.isNullOrEmpty()) {
                tvPhoneColor.text = " "
            } else {
                tvPhoneColor.text = colorList[position].color
            }
//            if (colorList[position].stockLeft.isNullOrEmpty()) {
//                stockLeft.text = " "
//            } else {
//                stockLeft.text = colorList[position].stockLeft
//            }

//            if (colorList[position].isSelected) {
//                ivPhoneImage.setBackgroundResource(R.drawable.white_box_blue_border_radious_10)
//            } else {
//                ivPhoneImage.setBackgroundResource(R.drawable.white_box_radious_10)
//            }

            itemView.setOnClickListener {view->
                colorList.forEach {
                    it.isSelected=false
                }
                colorList[position].isSelected=true
                onItemClickListener.colorListOnClick(adapterPosition,colorList,view)
            }
        }
    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    fun updateData( mColorList: List<Data>) {
        colorList = mColorList as ArrayList<Data>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivPhoneImage: LinearLayout
        var tvPhoneColor: TextView
        var stockLeft: TextView
        init {
            ivPhoneImage = itemView.findViewById(R.id.ivPhoneImage)
            tvPhoneColor = itemView.findViewById(R.id.tvPhoneColor)
            stockLeft = itemView.findViewById(R.id.stockLeft)
        }
    }
}