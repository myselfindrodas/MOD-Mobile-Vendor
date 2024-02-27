package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.SavedAddressData

class SavedAddressAdapter(val context: Context, var onItemClickListener: SavedAddressItemClickListener)
    :RecyclerView.Adapter<SavedAddressAdapter.classViewHolder>() {

    private val inflater : LayoutInflater
    var savedAddressList: ArrayList<SavedAddressData> = arrayListOf()
    var selectedposition = -1

    init {
        inflater = LayoutInflater.from(context)
    }

    interface SavedAddressItemClickListener {
        fun savedAddressOnClick(position: Int, list: ArrayList<SavedAddressData>, view: View)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedAddressAdapter.classViewHolder {
        val view = inflater.inflate(R.layout.saved_address_list, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedAddressAdapter.classViewHolder, position: Int) {
        with(holder){
            if (savedAddressList[position].tvAddressTypeInSavedAddress.isNullOrEmpty()) {
                tvAddressTypeInSavedAddress.text = " "
            } else {
                tvAddressTypeInSavedAddress.text = savedAddressList[position].tvAddressTypeInSavedAddress
            }
            if (savedAddressList[position].addressInSavedAddress.isNullOrEmpty()) {
                addressInSavedAddress.text = " "
            } else {
                addressInSavedAddress.text = savedAddressList[position].addressInSavedAddress
            }

            checkSavedAddress.isChecked = selectedposition == position

            checkSavedAddress.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    onItemClickListener.savedAddressOnClick(position, savedAddressList, buttonView)
                    selectedposition = adapterPosition
                    notifyDataSetChanged()
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return savedAddressList.size
    }

    fun updateData( mSavedAddressList: List<SavedAddressData>) {
        savedAddressList = mSavedAddressList as ArrayList<SavedAddressData>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvAddressTypeInSavedAddress: TextView
        var addressInSavedAddress: TextView
        var checkSavedAddress: RadioButton
        init {
            tvAddressTypeInSavedAddress = itemView.findViewById(R.id.tvAddressTypeInSavedAddress)
            addressInSavedAddress = itemView.findViewById(R.id.addressInSavedAddress)
            checkSavedAddress = itemView.findViewById(R.id.checkSavedAddress)
        }
    }
}