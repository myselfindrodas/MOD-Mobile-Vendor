package com.example.modmobilesmartsale.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.model.viewaddressmodel.Addressdata

class AddressAdapter(val context: Context, var onItemClickListener: AddressOnItemClickListener) :
    RecyclerView.Adapter<AddressAdapter.classViewHolder>() {

    private val inflater: LayoutInflater
    var addressList: ArrayList<Addressdata> = ArrayList()
    var selectedposition = -1

    init {
        inflater = LayoutInflater.from(context)
    }

    interface AddressOnItemClickListener {
        fun addressListOnClick(position: Int, list: ArrayList<Addressdata>, view: View, type:String)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): classViewHolder {
        val view = inflater.inflate(R.layout.address_list_items, parent, false)
        return classViewHolder(view)
    }

    override fun onBindViewHolder(holder: classViewHolder, position: Int) {
        with(holder) {

            if (addressList[position].addrsType.isNullOrEmpty()) {
                tvAddressType.text = " "
            } else {
                tvAddressType.text = addressList[position].addrsType
            }

            if (addressList[position].addrsType.equals("Home")){

                imageOfAddress.setImageResource(R.drawable.icon_address_home)

            }else if (addressList[position].addrsType.equals("Office")){

                imageOfAddress.setImageResource(R.drawable.icon_office)

            }else{

                imageOfAddress.setImageResource(R.drawable.ic_city)

            }


            address.text = addressList[position].city + ", " + addressList[position].state + ", " + addressList[position].pincode

            if (addressList[position].default_type.equals("Y")) {
                checkAddress.isChecked = true
                btnDelete.visibility = View.GONE

            } else {
                checkAddress.isChecked = false
                btnDelete.visibility = View.VISIBLE

            }

//            checkAddress.isChecked = selectedposition == position

//            checkAddress.setOnCheckedChangeListener { buttonView, isChecked ->
//                if (isChecked) {
//                    onItemClickListener.addressListOnClick(position, addressList, buttonView)
//                    selectedposition = adapterPosition
//                    notifyDataSetChanged()
//                }
//            }

            checkAddress.setOnClickListener {
                onItemClickListener.addressListOnClick(position, addressList, it, "setdefault")
            }


            btnDelete.setOnClickListener {

                onItemClickListener.addressListOnClick(position, addressList, it, "delete")

            }



            btnEdit.setOnClickListener {


                onItemClickListener.addressListOnClick(position, addressList, it, "edit")


            }


        }
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    fun updateData(mAddressList: List<Addressdata>) {
        addressList = mAddressList as ArrayList<Addressdata>
        notifyDataSetChanged()
    }

    inner class classViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageOfAddress: ImageView
        var btnDelete: ImageView
        var tvAddressType: TextView
        var address: TextView
        var checkAddress: RadioButton
        var btnEdit: ImageView


        init {
            imageOfAddress = itemView.findViewById(R.id.imageOfAddress)
            btnDelete = itemView.findViewById(R.id.btnDelete)
            tvAddressType = itemView.findViewById(R.id.tvAddressType)
            address = itemView.findViewById(R.id.address)
            checkAddress = itemView.findViewById(R.id.checkAddress)
            btnEdit = itemView.findViewById(R.id.btnEdit)
        }
    }
}