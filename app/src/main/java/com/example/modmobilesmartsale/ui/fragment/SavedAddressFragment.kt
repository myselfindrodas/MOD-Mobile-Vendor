package com.example.modmobilesmartsale.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.defaultaddressmodel.DefaultAddressRequest
import com.example.modmobilesmartsale.data.model.manageaddressmodel.ManageAddressRequest
import com.example.modmobilesmartsale.data.model.viewaddressmodel.Addressdata
import com.example.modmobilesmartsale.data.model.viewaddressmodel.ViewAddressRequest
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentSavedAddressBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.adapter.AddressAdapter
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class SavedAddressFragment : Fragment(), AddressAdapter.AddressOnItemClickListener {

    private lateinit var binding: FragmentSavedAddressBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var mainActivity: MainActivity
    var addressAdapter: AddressAdapter?= null
    var dialog: BottomSheetDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_saved_address, container, false)
        mainActivity = activity as MainActivity
        val root = binding.root
        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }
        viewModel = vm
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        binding.topBarSavedAddress.tvTopBar.setText("Saved Address")

        binding.topBarSavedAddress.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        mainActivity.setBottomNavigationVisibility(false)


        binding.btnAddNewAddressToSavedAddress.setOnClickListener {

            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.nav_addaddress)
        }

        addressAdapter = AddressAdapter(mainActivity, this@SavedAddressFragment)
        binding.rvSavedAddressList.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvSavedAddressList.adapter = addressAdapter
        addresslist()

    }



    private fun addresslist(){
        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.viewaddress(
                ViewAddressRequest(
                    token = Shared_Preferences.getToken().toString(),
                    userId = Shared_Preferences.getUserId()
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {

                                if (resource.data?.response?.data?.addressdata!!.isEmpty()){

                                    binding.tvNoaddressfound.visibility = View.VISIBLE
                                    binding.rvSavedAddressList.visibility = View.GONE

                                }else{

                                    binding.tvNoaddressfound.visibility = View.GONE
                                    binding.rvSavedAddressList.visibility = View.VISIBLE
                                    addressAdapter?.updateData(resource.data.response.data.addressdata)
                                    for (i in 0 until resource.data.response.data.addressdata.size) {
                                        if (resource.data.response.data.addressdata[i].default_type.equals("Y")) {
                                            Shared_Preferences.setShippingAddress(resource.data.response.data.addressdata[i].locality+","+
                                                    resource.data.response.data.addressdata[i].city+","+ resource.data.response.data.addressdata[i].state)
                                            Shared_Preferences.setShippingState(resource.data.response.data.addressdata[i].state)
                                            Shared_Preferences.setShippingCity(resource.data.response.data.addressdata[i].city)
                                            Shared_Preferences.setShippingZip(resource.data.response.data.addressdata[i].pincode)


                                        }else{

                                            Shared_Preferences.setShippingAddress("")
                                            Shared_Preferences.setShippingState("")
                                            Shared_Preferences.setShippingCity("")
                                            Shared_Preferences.setShippingZip("")

                                        }
                                    }
                                }
                            }



                        }

                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            //Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
                            if (it.message!!.contains("401", true)) {
                                val builder = AlertDialog.Builder(mainActivity)
                                builder.setMessage("Invalid Employee Id / Password")
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.yellow))
                                }
                                alert.show()
                            } else
                                Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()

                        }

                        Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }

                    }

                }
            }


        } else {

            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()

        }

    }





    private fun primaryaddress(addressid:String){

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.defaultaddress(
                DefaultAddressRequest(
                    id = addressid,
                    userid = Shared_Preferences.getUserId(),
                    token = Shared_Preferences.getToken().toString()
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {
//                                Toast.makeText(mainActivity, resource.data?.response?.data?.get(0)!!.response, Toast.LENGTH_SHORT).show()
                                Toast.makeText(mainActivity, "Default Address Changed Successfully", Toast.LENGTH_SHORT).show()

                                addresslist()
                            }



                        }

                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            //Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
                            if (it.message!!.contains("401", true)) {
                                val builder = AlertDialog.Builder(mainActivity)
                                builder.setMessage("Invalid Employee Id / Password")
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.yellow))
                                }
                                alert.show()
                            } else
                                Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()

                        }

                        Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }

                    }

                }
            }


        } else {

            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()

        }
    }

    override fun addressListOnClick(
        position: Int,
        list: ArrayList<Addressdata>,
        view: View,
        type: String
    ) {
        if (type.equals("setdefault")) {
            primaryaddress(list[position].addressid)
        }else if(type.equals("edit")){

            val bundle = Bundle()
            bundle.putString("viewtype", "edit")
            bundle.putSerializable("data", list[position])
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.nav_addaddress, bundle)
            dialog!!.dismiss()

        }  else {

            val builder = androidx.appcompat.app.AlertDialog.Builder(mainActivity)
            builder.setTitle("Delete ${list[position].addrsType}")
            builder.setMessage("Are you sure you delete ${list[position].addrsType} from the list?")
            builder.setPositiveButton(
                "Yes"
            ) { dialog, which ->

                deleteAddress(
                    list[position].addressid,
                    list[position].locality,
                    list[position].city,
                    list[position].state,
                    list[position].pincode,
                    list[position].landmark,
                    list[position].contactName,
                    list[position].contactNo,
                    list[position].alternateNo,
                    list[position].addrsType,
                    list[position].default_type
                )
                dialog.dismiss()
            }
            builder.setNegativeButton(
                "Cancel"
            ) { dialog, which ->

                dialog.dismiss()
            }
            val alert = builder.create()
            alert.setOnShowListener { arg0 ->
                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.blue))
                alert.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.blue))
            }
            alert.show()

        }
    }



    private fun deleteAddress(
        addressid: String,
        locality: String,
        city: String,
        state: String,
        pincode: String,
        landmark: String,
        contactname: String,
        contactno: String,
        alternateno: String,
        addresstype: String,
        defaulttype: String
    ) {


        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.manageaddress(
                ManageAddressRequest(
                    addressid = addressid,
                    addrsType = addresstype,
                    alternateNo = alternateno,
                    city = city,
                    contactName = contactname,
                    contactNo = contactno,
                    landmark = landmark,
                    locality = locality,
                    pincode = pincode,
                    reqtype = "D",
                    state = state,
                    userid = Shared_Preferences.getUserId(),
                    token = Shared_Preferences.getToken().toString()
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {

                                Toast.makeText(
                                    mainActivity,
                                    resource.data?.response?.data?.get(0)!!.response,
                                    Toast.LENGTH_SHORT
                                ).show()

                                addresslist()


                            }


                        }

                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            //Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
                            if (it.message!!.contains("401", true)) {
                                val builder = AlertDialog.Builder(mainActivity)
                                builder.setMessage("Invalid Employee Id / Password")
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.yellow))
                                }
                                alert.show()
                            } else
                                Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()

                        }

                        Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }

                    }

                }
            }

        } else {

            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()

        }
    }

}