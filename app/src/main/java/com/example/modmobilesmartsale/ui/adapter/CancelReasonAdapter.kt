package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.CancelReasonData

class CancelReasonAdapter(val context: Context, var onItemClickListener: cancelReasonClickListener)
    :RecyclerView.Adapter<CancelReasonAdapter.classViewHolder>() {

    private val inflater: LayoutInflater
    var cancel_reason_list: ArrayList<CancelReasonData> = arrayListOf()
    var selectedposition = -1

    init {
        inflater = LayoutInflater.from(context)
    }

    interface cancelReasonClickListener {
        fun CancelReasonOnClick(position: Int, list: ArrayList<CancelReasonData>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CancelReasonAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.reasons_for_cancel_order, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: CancelReasonAdapter.classViewHolder, position: Int) {
        with(holder) {
            if (cancel_reason_list[position].tvReason.isNullOrEmpty()) {
                tvReason.text = " "
            } else {
                tvReason.text = cancel_reason_list[position].tvReason
            }

            checkReason.isChecked = selectedposition == position

            checkReason.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    onItemClickListener.CancelReasonOnClick(position, cancel_reason_list, buttonView)
                    selectedposition = adapterPosition
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cancel_reason_list.size
    }

    fun updateData(mCancel_reason_list: List<CancelReasonData>) {
        cancel_reason_list = mCancel_reason_list as ArrayList<CancelReasonData>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvReason: TextView
        var checkReason: RadioButton
        init {
            tvReason = itemView.findViewById(R.id.tvReason)
            checkReason = itemView.findViewById(R.id.checkReason)
        }
    }
}