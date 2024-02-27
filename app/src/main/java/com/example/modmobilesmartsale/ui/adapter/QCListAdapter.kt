package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.qclistmodel.Data

class QCListAdapter(val context: Context, var onItemClickListener: QCItemClickListener)
    : RecyclerView.Adapter<QCListAdapter.classViewHolder>(){

    private val inflater : LayoutInflater
    var qcList: ArrayList<Data> = arrayListOf()

    init {
        inflater = LayoutInflater.from(context)
    }

    interface QCItemClickListener {
        fun QCItemOnClick(position: Int, list: ArrayList<Data>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QCListAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.rv_qclist, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: QCListAdapter.classViewHolder, position: Int) {
        with(holder){
            if (qcList[position].name==null) {
                tvQCname.text = ""
            } else {
                tvQCname.text = qcList[position].name
            }


            if (qcList[position].status.isNullOrEmpty()) {
                tvQCstatus.text = ""
            } else {
                tvQCstatus.text = qcList[position].status
            }

        }
    }

    override fun getItemCount(): Int {
        return qcList.size
    }

    fun updateData(mQCList: List<Data>) {
        qcList = mQCList as ArrayList<Data>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvQCname: TextView
        var tvQCstatus: TextView

        init {
            tvQCname = itemView.findViewById(R.id.tvQCname)
            tvQCstatus = itemView.findViewById(R.id.tvQCstatus)
        }
    }

}