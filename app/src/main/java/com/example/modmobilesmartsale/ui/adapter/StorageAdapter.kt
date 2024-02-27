package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.storagecolormodel.Data

class StorageAdapter(val context: Context, var onItemClickListener: StorageItemClickListener)
    :RecyclerView.Adapter<StorageAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var storageList: ArrayList<Data> = ArrayList()


    init {
        inflater = LayoutInflater.from(context)
    }

    interface StorageItemClickListener {
        fun storageListOnClick(position: Int, list: ArrayList<Data>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StorageAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.storage_list_item, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: StorageAdapter.classViewHolder, position: Int) {
        with(holder){
            if (storageList[position].memory.isNullOrEmpty()) {
                tvMobileStorage.text = " "
            } else {
                tvMobileStorage.text = storageList[position].memory
            }

            if (storageList[position].isSelected) {
                tvMobileStorage.setBackgroundResource(R.drawable.white_box_blue_border)
            } else {
                tvMobileStorage.setBackgroundResource(R.drawable.white_box_grey_border_radious_8)
            }

            itemView.setOnClickListener {view->
                storageList.forEach {
                    it.isSelected=false
                }
                storageList[position].isSelected=true
                onItemClickListener.storageListOnClick(adapterPosition,storageList,view)
            }

        }
    }

    override fun getItemCount(): Int {
        return storageList.size
    }

    fun updateData( mStorageList: List<Data>) {
        storageList = mStorageList as ArrayList<Data>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMobileStorage: TextView
        init {
            tvMobileStorage = itemView.findViewById(R.id.tvMobileStorage)
        }
    }
}