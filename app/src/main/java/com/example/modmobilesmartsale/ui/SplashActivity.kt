package com.example.modmobilesmartsale.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.base.BaseActivity
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.login_model.LoginRequestModel
import com.example.modmobilesmartsale.data.model.tokenmodel.TokenRequest
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.ActivitySplashBinding
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: CommonViewModel

    override fun resourceLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivitySplashBinding


        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

    }

    override fun setFunction() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val animation = AnimationUtils.loadAnimation(this, R.anim.zoom_out)
            binding.background.startAnimation(animation)
            binding.background.visibility = View.GONE
        }, 1000)

        val handler1 = Handler(Looper.getMainLooper())
        handler1.postDelayed({
            val animation1 = AnimationUtils.loadAnimation(this, R.anim.slide_up)
            binding.text.startAnimation(animation1)
            binding.text.visibility = View.GONE
        }, 2000)

        if (Shared_Preferences.getLoginStatus()==true){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }else{

            val handler2 = Handler(Looper.getMainLooper())
            handler2.postDelayed({
                val animation2 = AnimationUtils.loadAnimation(this, R.anim.fade_in)
                binding.llLogin.startAnimation(animation2)
                binding.textLogin.visibility = View.VISIBLE
                binding.llLogin.visibility = View.VISIBLE
            }, 2500)

        }


        binding.tvCreateNewAccount.setOnClickListener {
            val intent = Intent(this, CreateNewAccountActivity::class.java)
            startActivity(intent)
        }

        binding.etMobileNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (binding.etMobileNumber.text.toString().matches(Regex("^0"))) {
                    // Not allowed
//                    Toast.makeText(mainActivity, "not allowed", Toast.LENGTH_LONG).show()
                    binding.etMobileNumber.setText("")
                }
            }

            override fun afterTextChanged(s: Editable?) {





            }
        })

        binding.btnGetOtp.setOnClickListener {
            if (binding.etMobileNumber.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Enter your phone number", Toast.LENGTH_SHORT).show()
            } else if (binding.etMobileNumber.text.toString().trim().length < 10) {
                Toast.makeText(this, "Enter Valid Phone Number", Toast.LENGTH_SHORT).show()
            } else {
                getLogin(binding.etMobileNumber.text.toString())
            }
        }

        getToken()
    }


    private fun getToken() {

        if (Utilities.isNetworkAvailable(this)) {

            viewModel.generatetoken(requestBody = TokenRequest(user = "admin", pass = "mod@321"))
                .observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideProgressDialog()
                                if (resource.data!!.response.status.equals("true")) {


                                    Shared_Preferences.setUserToken(resource.data.response.data.token)

                                    //startActivity(Intent(this@OTPActivity, RegistrationActivity::class.java))
                                } else {

                                    val builder = AlertDialog.Builder(this)
                                    builder.setMessage("Invalid")
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

    private fun getLogin(phoneNO: String) {

        if (Utilities.isNetworkAvailable(this)) {

            viewModel.login(
                LoginRequestModel(
                    user_id = phoneNO,
                    token = Shared_Preferences.getToken().toString()
                )
            ).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {

                                if (resource.data?.response?.data?.userType == "Retailer" || resource.data?.response?.data?.userType == "Distributer") {
                                    Toast.makeText(this, resource.data.response.message, Toast.LENGTH_SHORT).show()
                                    Shared_Preferences.setUserType(resource.data.response.data.userType)
                                    Shared_Preferences.setLoginStatus(true)
                                    Shared_Preferences.setName(resource.data.response.data.userName)
                                    Shared_Preferences.setPhoneNo(resource.data.response.data.userContact)
                                    Shared_Preferences.setUserId(resource.data.response.data.uid)
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(this, "No customer will be logged in", Toast.LENGTH_SHORT).show()
                                }


                            } else {

                                if (resource.data?.response?.message.equals("No Record Found")) {
                                    val intent = Intent(this, VerifyOTPActivity::class.java)
                                        .putExtra("mobile", binding.etMobileNumber.text.toString())
                                    startActivity(intent)
                                }

                            }

//                            if (!resource.data.isNullOrEmpty()) {
//
//                                Toast.makeText(this, resource.data[0].success, Toast.LENGTH_SHORT)
//                                    .show()
//                                val intent = Intent(this, VerifyOTPActivity::class.java)
//                                    .putExtra("otp", resource.data[0].otp)
//                                    .putExtra("phoneNo", resource.data[0].userid)
//                                startActivity(intent)
//
//                                //startActivity(Intent(this@OTPActivity, RegistrationActivity::class.java))
//                            } else {
//
//                                val builder = AlertDialog.Builder(this)
//                                builder.setMessage("Invalid")
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
//                            }


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