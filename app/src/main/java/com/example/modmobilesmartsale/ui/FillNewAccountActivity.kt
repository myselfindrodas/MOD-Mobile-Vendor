package com.example.modmobilesmartsale.ui

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.base.BaseActivity
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.registrationsuccessmodel.Recipient
import com.example.modmobilesmartsale.data.model.registrationsuccessmodel.RegistrationSuccessRequest
import com.example.modmobilesmartsale.data.model.signupmodel.SignupRequest
import com.example.modmobilesmartsale.data.model.statemodel.StateListRequest
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory2
import com.example.modmobilesmartsale.databinding.ActivityFillNewAccountBinding
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import com.example.modmobilesmartsale.viewmodel.CommonViewModel2
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class FillNewAccountActivity : BaseActivity() {

    private lateinit var binding: ActivityFillNewAccountBinding
    private lateinit var viewModel: CommonViewModel
    var spinnerZoneNameArray = ArrayList<String>()
    var spinnerZoneIdArray = ArrayList<String>()
    var selectedZoneName = ""
    var selectedZoneId = ""
    var currentDate = ""

    override fun resourceLayout(): Int {
        return R.layout.activity_fill_new_account
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityFillNewAccountBinding
    }

    override fun setFunction() {

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm



        currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        Log.d(TAG, "date--->"+currentDate)


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

        binding.topBarCreateNewAccount.tvTopBar.setText("Back")

        binding.topBarCreateNewAccount.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.etMobileNo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (binding.etMobileNo.text.toString().matches(Regex("^0"))) {
                    // Not allowed
//                    Toast.makeText(mainActivity, "not allowed", Toast.LENGTH_LONG).show()
                    binding.etMobileNo.setText("")
                }
            }

            override fun afterTextChanged(s: Editable?) {





            }
        })

        binding.btnNext.setOnClickListener {
            if (binding.etName.text.toString().isNullOrEmpty()){
                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show()
            }else if (binding.etMobileNo.text.toString().trim().isEmpty() || binding.etMobileNo.text.toString().trim().length < 10){
                Toast.makeText(this,"Enter Valid Credential", Toast.LENGTH_SHORT).show()
            }else if(binding.etEmailId.text.toString().isEmpty() || !Utilities.isEmail(binding.etEmailId.text.toString().trim())){
                Toast.makeText(this,"Enter your Email",Toast.LENGTH_SHORT).show()
            } else if (binding.zoneSpinner.selectedItem.toString().isEmpty()) {
                Toast.makeText(this, "Select Area Zone", Toast.LENGTH_SHORT).show()
            }  else{

                Shared_Preferences.setMobileRegistrationNo(binding.etMobileNo.text.toString())
                Shared_Preferences.setNameRegistration(binding.etName.text.toString())
                Shared_Preferences.setEmailRegistration(binding.etEmailId.text.toString())
                Shared_Preferences.setZoneRegistration(binding.zoneSpinner.selectedItem.toString())

                val intent = Intent(this, LocationActivity::class.java)
                startActivity(intent)

//                Signup()
//
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
            }
        }
//
//        zoneList = arrayOf("Retailer", "Distributor")
//        val zoneAdapter =
//            ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, zoneList)
//        binding.zoneSpinner.adapter = zoneAdapter
    }



    private fun zonelist(){

        if (Utilities.isNetworkAvailable(this)) {

            viewModel.statemaster(StateListRequest(state = "", token = Shared_Preferences.getToken().toString())).observe(this) {
                it?.let { resource ->
                    if (resource.data?.response?.status.equals("true")) {

                        spinnerZoneNameArray = ArrayList<String>()
                        spinnerZoneIdArray = ArrayList<String>()
                        spinnerZoneNameArray.add("Select Zone")
                        spinnerZoneIdArray.add("")


                        resource.data?.response?.data?.forEach { i ->

                            spinnerZoneNameArray.add(i.state)
                            spinnerZoneIdArray.add(i.stateCode)

                        }

                        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            this,
                            android.R.layout.simple_spinner_item,
                            spinnerZoneNameArray
                        )
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.zoneSpinner.adapter = spinnerArrayAdapter


                    }


                }
            }

        } else {

            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()

        }
    }


//    private fun Signup(){
//
//
//        if (Utilities.isNetworkAvailable(this)) {
//
//            viewModel.signup(SignupRequest(
//                address1 = "",
//                address2 = "",
//                city =  selectedZoneName,
//                createDate = currentDate,
//                customerGroupId = "Retailer",
//                email = binding.etEmailId.text.toString(),
//                name = binding.etName.text.toString(),
//                pancard = "N / A",
//                pincode = "",
//                state = selectedZoneName,
//                telephone = binding.etMobileNo.text.toString(),
//                token = Shared_Preferences.getToken().toString()
//
//            )).observe(this) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            hideProgressDialog()
//
//                            if (resource.data?.response?.data?.get(0)?.msg.equals("Customer Created")){
//
//                                Toast.makeText(this, resource.data?.response?.data?.get(0)?.msg ?:"" , Toast.LENGTH_SHORT).show()
//                                registrationsuccess()
//
//
//
//                            }else{
//
//                                val builder = AlertDialog.Builder(this)
//                                builder.setMessage(resource.data?.response?.data?.get(0)?.msg)
//                                builder.setPositiveButton(
//                                    "Ok"
//                                ) { dialog, which ->
//
//                                    dialog.cancel()
//
//                                }
//                                val alert = builder.create()
//                                alert.setOnShowListener { arg0 ->
//                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
//                                        .setTextColor(resources.getColor(R.color.yellow))
//                                }
//                                alert.show()
//
//
//                            }
//
//                        }
//
//                        Status.ERROR -> {
//                            hideProgressDialog()
//                            //Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
//                            if (it.message!!.contains("401", true)) {
//                                val builder = AlertDialog.Builder(this)
//                                builder.setMessage("Invalid Employee Id / Password")
//                                builder.setPositiveButton(
//                                    "Ok"
//                                ) { dialog, which ->
//
//                                    dialog.cancel()
//
//                                }
//                                val alert = builder.create()
//                                alert.setOnShowListener { arg0 ->
//                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
//                                        .setTextColor(resources.getColor(R.color.yellow))
//                                }
//                                alert.show()
//                            } else
//                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//
//                        }
//
//                        Status.LOADING -> {
//                            showProgressDialog()
//                        }
//
//                    }
//
//                }
//            }
//
//
//        } else {
//
//            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
//
//        }
//
//    }






    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE
    }
}