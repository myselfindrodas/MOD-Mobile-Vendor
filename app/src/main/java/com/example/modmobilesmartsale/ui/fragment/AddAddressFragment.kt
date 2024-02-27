package com.example.modmobilesmartsale.ui.fragment

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.manageaddressmodel.ManageAddressRequest
import com.example.modmobilesmartsale.data.model.statemodel.StateListRequest
import com.example.modmobilesmartsale.data.model.viewaddressmodel.Addressdata
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentAddAddressBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import java.io.Serializable


class AddAddressFragment : Fragment() {


    lateinit var binding: FragmentAddAddressBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var mainActivity: MainActivity
    var spinnerZoneNameArray = ArrayList<String>()
    var spinnerZoneIdArray = ArrayList<String>()
    var selectedZoneName = ""
    var selectedZoneId = ""
    var selectedstate = ""

    var addressData: Addressdata? = null
    var isEdited = false
    var addressid = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_address, container, false)
        mainActivity = activity as MainActivity
        val root = binding.root
        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }
        viewModel = vm
        val intent = arguments
        if (intent != null && intent.containsKey("viewtype")) {
            addressData = getDataSerializable(intent, "data", Addressdata::class.java)
            println(addressData!!.addressid)
            isEdited = true
        }


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        binding.topBarSearch.tvTopBar.text = "Add New Address"

        binding.topBarSearch.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }



        if (addressData != null) {
            addressData?.let { itAddressData ->

                selectedstate = itAddressData.state
                addressid = itAddressData.addressid
                binding.etCity.setText(itAddressData.city)
                binding.etLocality.setText(itAddressData.locality)
                binding.etLandmark.setText(itAddressData.landmark)
                binding.etContactnumber.setText(itAddressData.contactNo)
                binding.etAlternatenumber.setText(itAddressData.alternateNo)
                binding.etPincode.setText(itAddressData.pincode)
                binding.btnSubmit.text = "Edit Address"
                binding.topBarSearch.tvTopBar.text = "Edit address"
                binding.tvCreateNewAccount.text = "Edit Address"
                binding.spAddresstype.setEnabled(false)
                binding.spAddresstype.setClickable(false)
                when (itAddressData.addrsType) {
                    "Home" -> {
                        binding.zoneSpinner.setSelection(0)
                    }
                    "Office" -> {
                        binding.zoneSpinner.setSelection(1)

                    }
                    else -> {
                        binding.zoneSpinner.setSelection(2)

                    }
                }

            }
        }


        binding.etPincode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (binding.etPincode.text.toString().matches(Regex("^0"))) {
                    // Not allowed
//                    Toast.makeText(mainActivity, "not allowed", Toast.LENGTH_LONG).show()
                    binding.etPincode.setText("")
                }
            }

            override fun afterTextChanged(s: Editable?) {





            }
        })


        binding.zoneSpinner.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {


                if (position > 0) {
                    selectedZoneId = spinnerZoneIdArray[binding.zoneSpinner.selectedItemPosition]
                    selectedZoneName = spinnerZoneNameArray[binding.zoneSpinner.selectedItemPosition]


                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {


            }
        })

        zonelist()
        spAddressType()

        binding.btnSubmit.setOnClickListener {

            if (binding.zoneSpinner.selectedItem.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Select State", Toast.LENGTH_SHORT).show()
            } else if (binding.etCity.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter City", Toast.LENGTH_SHORT).show()
            } else if (binding.etLocality.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter Locality", Toast.LENGTH_SHORT).show()
            } else if (binding.etLandmark.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter Landmark", Toast.LENGTH_SHORT).show()
            } else if (binding.etContactnumber.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter Contact Numnber", Toast.LENGTH_SHORT).show()
            } else if (binding.etContactnumber.text.length<10) {
                Toast.makeText(mainActivity, "Enter Valid Contact Numnber", Toast.LENGTH_SHORT).show()
            }else if (binding.etAlternatenumber.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter Alternate Numnber", Toast.LENGTH_SHORT).show()
            }else if (binding.etAlternatenumber.text.length<10) {
                Toast.makeText(mainActivity, "Enter Valid Alternate Numnber", Toast.LENGTH_SHORT).show()
            } else if (binding.etPincode.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter Pincode", Toast.LENGTH_SHORT).show()
            } else if (binding.etPincode.text.length<6) {
                Toast.makeText(mainActivity, "Enter Valid Pincode", Toast.LENGTH_SHORT).show()
            }else if (binding.spAddresstype.selectedItem.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter Address Type", Toast.LENGTH_SHORT).show()
            } else {

                if (isEdited) {
                    addAddress("E", addressid)
                } else {
                    addAddress("A", "")
                }

            }
        }

    }


    private fun spAddressType() {
        val type: MutableList<String> = ArrayList()
        type.add("Home")
        type.add("Office")
        type.add("Other")
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, type)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spAddresstype.adapter = arrayAdapter
    }

    private fun zonelist() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.statemaster(
                StateListRequest(
                    state = "",
                    token = Shared_Preferences.getToken().toString()
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    if (resource.data?.response?.status.equals("true")) {

                        spinnerZoneNameArray = ArrayList<String>()
                        spinnerZoneIdArray = ArrayList<String>()
                        spinnerZoneNameArray.add("Choose Area")
                        spinnerZoneIdArray.add("")


                        resource.data?.response?.data?.forEach { i ->

                            spinnerZoneNameArray.add(i.state)
                            spinnerZoneIdArray.add(i.stateCode)

                        }

                        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            mainActivity,
                            android.R.layout.simple_spinner_item,
                            spinnerZoneNameArray
                        )
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.zoneSpinner.adapter = spinnerArrayAdapter


                        if (isEdited){
                            for (i in 0..resource.data?.response!!.data.size -1){
                                if (resource.data.response.data[i].state==selectedstate){
                                    binding.zoneSpinner.setSelection(i+1)
                                }
                            }

                        }



                    }


                }
            }

        } else {

            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()

        }
    }

    private fun addAddress(requestType:String, addressid:String) {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.manageaddress(
                ManageAddressRequest(
                    addressid = addressid,
                    addrsType = binding.spAddresstype.getSelectedItem().toString(),
                    alternateNo = binding.etAlternatenumber.text.toString(),
                    city = binding.etCity.text.toString(),
                    contactName = Shared_Preferences.getName().toString(),
                    contactNo = Shared_Preferences.getPhoneNo().toString(),
                    landmark = binding.etLandmark.text.toString(),
                    locality = binding.etLocality.text.toString(),
                    pincode = binding.etPincode.text.toString(),
                    reqtype = requestType,
                    state = binding.zoneSpinner.selectedItem.toString(),
                    userid = Shared_Preferences.getUserId(),
                    token = Shared_Preferences.getToken().toString()
                )
            ).observe(mainActivity) {it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        mainActivity.hideProgressDialog()

                        if (resource.data?.response?.status.equals("true")) {

                            Toast.makeText(mainActivity, resource.data?.response?.data?.get(0)!!.response, Toast.LENGTH_SHORT).show()
                            val navController = Navigation.findNavController(binding.root)
                            navController.navigate(R.id.nav_home)


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



    private fun <T : Serializable?> getDataSerializable(
        @Nullable bundle: Bundle?,
        @Nullable key: String?,
        clazz: Class<T>
    ): T? {
        if (bundle != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                return bundle.getSerializable(key, clazz)
            } else {
                try {
                    return bundle.getSerializable(key) as T?
                } catch (ignored: Throwable) {
                }
            }
        }
        return null
    }


}