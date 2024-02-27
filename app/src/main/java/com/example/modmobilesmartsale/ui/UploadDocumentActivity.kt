package com.example.modmobilesmartsale.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.base.BaseActivity
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.registrationsuccessmodel.Recipient
import com.example.modmobilesmartsale.data.model.registrationsuccessmodel.RegistrationSuccessRequest
import com.example.modmobilesmartsale.data.model.signupmodel.SignupRequest
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory2
import com.example.modmobilesmartsale.databinding.ActivityUploadDocumentBinding
import com.example.modmobilesmartsale.utils.GetRealPathFromUri
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import com.example.modmobilesmartsale.viewmodel.CommonViewModel2
import com.github.dhaval2404.imagepicker.ImagePicker
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UploadDocumentActivity : BaseActivity() {

    private lateinit var binding: ActivityUploadDocumentBinding
    private lateinit var viewModel: CommonViewModel
    private var request_code1: Int = 0
    var pathFromUri = ""
    var pathFromUri1 = ""
    var pathFromUri2 = ""
    var currentDate = ""
    private lateinit var viewModel2: CommonViewModel2


    override fun resourceLayout(): Int {
        return R.layout.activity_upload_document
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityUploadDocumentBinding
    }

    override fun setFunction() {
        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm



        val vm2: CommonViewModel2 by viewModels {
            CommonModelFactory2(ApiHelper(ApiClient.apiService2))
        }

        viewModel2 = vm2

        binding.topBarUploadDocument.tvTopBar.setText("Back")

        binding.topBarUploadDocument.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.rlFrontImage.setOnClickListener {
            openGallery(1)
        }
        binding.rlBackImage.setOnClickListener {
            openGallery(2)
        }
        binding.rlGstImage.setOnClickListener {
            openGallery(3)
        }

        binding.btnSubmit.setOnClickListener {
            if (pathFromUri.isEmpty() || pathFromUri1.isEmpty() || pathFromUri2.isEmpty()){
                Toast.makeText(this, "Upload all the documents", Toast.LENGTH_SHORT).show()
            }else if(binding.etPanCardNo.text.toString().isEmpty()){

                Toast.makeText(this, "Enter Pan Card Number", Toast.LENGTH_SHORT).show()

            }else if(binding.etGstNo.text.toString().isEmpty()){

                Toast.makeText(this, "Enter GST no Number", Toast.LENGTH_SHORT).show()

            }else{
                Signup()
            }
        }

        currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        Log.d(ContentValues.TAG, "date--->"+currentDate)
    }

    private fun openGallery(i: Int) {
        request_code1 = i
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
            )
        } else {
            ImagePicker.Companion.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }
    }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted) {
            ImagePicker.Companion.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()

        } else {
            // PERMISSION NOT GRANTED
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2404 && resultCode == Activity.RESULT_OK) {
            val fileUri = data!!.data

            if (request_code1 == 1){
                try {
                    Picasso.get()
                        .load(fileUri)
                        .into(binding.image1)
                    binding.tvFrontPhoto.visibility = View.GONE
                    pathFromUri = GetRealPathFromUri.getPathFromUri(this, fileUri!!)!!

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            else if (request_code1 == 2){
                try {
                    Picasso.get()
                        .load(fileUri)
                        .into(binding.image2)
                    binding.tvBackPhoto.visibility = View.GONE
                    pathFromUri1 = GetRealPathFromUri.getPathFromUri(this, fileUri!!)!!

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            else if (request_code1 == 3){
                try {
                    Picasso.get()
                        .load(fileUri)
                        .into(binding.imageGst)
                    binding.tvGstPhoto.visibility = View.GONE
                    pathFromUri2 = GetRealPathFromUri.getPathFromUri(this, fileUri!!)!!

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            else{}
        }

        else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.RESULT_ERROR, Toast.LENGTH_SHORT).show()
        }

        else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }



    private fun Signup(){


        if (Utilities.isNetworkAvailable(this)) {

            viewModel.signup(
                SignupRequest(
                address1 = Shared_Preferences.getAddress1().toString(),
                address2 = "",
                city =  Shared_Preferences.getZoneRegistration().toString(),
                createDate = currentDate,
                customerGroupId = Shared_Preferences.getRegistrationType().toString(),
                email = Shared_Preferences.getEmailRegistration().toString(),
                name = Shared_Preferences.getNameRegistration().toString(),
                pancard = binding.etPanCardNo.text.toString(),
                pincode = Shared_Preferences.getPin().toString(),
                state = Shared_Preferences.getZoneRegistration().toString(),
                telephone = Shared_Preferences.getMobileRegistrationNo().toString(),
                token = Shared_Preferences.getToken().toString()

            )).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()

                            if (resource.data?.response?.data?.get(0)?.msg.equals("Customer Created")){

                                if (Shared_Preferences.getUserType().equals("Distributer")){
                                    Toast.makeText(this, "Distributer Registration Success" , Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(this, "Retailer Registration Success" , Toast.LENGTH_SHORT).show()
                                }
                                registrationsuccess()



                            }else{

                                val builder = AlertDialog.Builder(this)
//                                builder.setMessage(resource.data?.response?.data?.get(0)?.msg)
                                if (Shared_Preferences.getUserType().equals("Distributer")){
                                    builder.setMessage("Already Distributer Created")

                                }else{
                                    builder.setMessage("Already Retailer Created")

                                }
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


                            }

                        }

                        Status.ERROR -> {
                            hideProgressDialog()
                            //Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
                            if (it.message!!.contains("401", true)) {
                                val builder = AlertDialog.Builder(this)
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
                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                        }

                        Status.LOADING -> {
                            showProgressDialog()
                        }

                    }

                }
            }


        } else {

            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()

        }

    }






    private fun registrationsuccess(){

        if (Utilities.isNetworkAvailable(this)) {

            val recipientslist = ArrayList<Recipient>()
            recipientslist.add(
                Recipient(
                    "91"+Shared_Preferences.getMobileRegistrationNo().toString(),
                    "VALUE1",
                    "VALUE2"
                )
            )
            viewModel2.successmessage(
                RegistrationSuccessRequest(
                    recipientslist,
                    shortUrl = "1",
                    templateId = "653f5fadd6fc05577c630282")
            ).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()

                            if (resource.data?.type.equals("success")){

                                Toast.makeText(this, "Registration Successfully Done" , Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, SplashActivity::class.java)
                                startActivity(intent)



                            }else{

                                val builder = AlertDialog.Builder(this)
                                builder.setMessage(resource.data?.type)
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


                            }

                        }

                        Status.ERROR -> {
                            hideProgressDialog()
                            //Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
                            if (it.message!!.contains("401", true)) {
                                val builder = AlertDialog.Builder(this)
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
                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                        }

                        Status.LOADING -> {
                            showProgressDialog()
                        }

                    }

                }
            }


        } else {

            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()

        }
    }



    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE
    }
}