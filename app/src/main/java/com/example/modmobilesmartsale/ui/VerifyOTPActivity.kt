package com.example.modmobilesmartsale.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.base.BaseActivity
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory2
import com.example.modmobilesmartsale.databinding.ActivityVerifyOtpactivityBinding
import com.example.modmobilesmartsale.utils.Constants.TAG
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import com.example.modmobilesmartsale.viewmodel.CommonViewModel2
import java.util.Locale

class VerifyOTPActivity : BaseActivity() {

    private lateinit var binding: ActivityVerifyOtpactivityBinding
    private lateinit var viewModel: CommonViewModel
    var countDownTimer: CountDownTimer? = null
    var validotp: String? = ""
    var mobile: String? = ""
    var otp: String? = ""
    private lateinit var viewModel2: CommonViewModel2


    override fun resourceLayout(): Int {
        return R.layout.activity_verify_otpactivity
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityVerifyOtpactivityBinding


    }

    override fun setFunction() {
        val intent = intent
        mobile = intent.getStringExtra("mobile")

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm




        val vm2: CommonViewModel2 by viewModels {
            CommonModelFactory2(ApiHelper(ApiClient.apiService2))
        }

        viewModel2 = vm2

        sentotp2()
//        sentotp()
        otpTimer(binding, this@VerifyOTPActivity)

        with(binding) {



            val otptext = ArrayList<EditText>()
            otptext.add(otp1)
            otptext.add(otp2)
            otptext.add(otp3)
            otptext.add(otp4)
            setOtpEditTextHandler(otptext)


            btnVerify.setOnClickListener {
                otp = otp1.text.toString() +
                        otp2.text.toString() +
                        otp3.text.toString() +
                        otp4.text.toString()

                if (otp!!.length > 3) {

                    verifyOtp(otp!!)
                } else {
                    Toast.makeText(this@VerifyOTPActivity, "Enter valid OTP", Toast.LENGTH_SHORT)
                        .show()
                }
            }


            tvResend.setOnClickListener {
                binding.otp1.setText("")
                binding.otp2.setText("")
                binding.otp3.setText("")
                binding.otp4.setText("")
//                sentotp()
//                sentotp2()
                resendotp()
                otpTimer(binding, this@VerifyOTPActivity)
            }

        }

    }


    private fun otpTimer(binding: ActivityVerifyOtpactivityBinding, context: Context) {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
        }
        countDownTimer = object : CountDownTimer(60000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = "00:" + String.format(
                    Locale.ENGLISH, "%02d", millisUntilFinished / 1000
                )
                binding.tvResend.isEnabled = false
                binding.tvResend.isClickable = false
                binding.tvResend.setTextColor(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        context.resources.getColor(
                            R.color.grey,
                            context.resources.newTheme()
                        )
                    } else {
                        context.resources.getColor(
                            R.color.grey
                        )
                    }
                )
            }

            override fun onFinish() {
                binding.tvTimer.text = "00:00"
                binding.tvResend.isEnabled = true
                binding.tvResend.isClickable = true
                binding.tvResend.setTextColor(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        context.resources.getColor(
                            R.color.yellow,
                            context.resources.newTheme()
                        )
                    } else {
                        context.resources.getColor(
                            R.color.yellow
                        )
                    }
                )

            }
        }.start()
    }


    private fun setOtpEditTextHandler(otpEt: java.util.ArrayList<EditText>) { //This is the function to be called
        for (i in 0..3) { //Its designed for 6 digit OTP
            otpEt.get(i).addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (i == 3 && otpEt[i].text.toString().isNotEmpty()) {

                    } else if (otpEt[i].text.toString().isNotEmpty()) {
                        otpEt[i + 1]
                            .requestFocus()
                    }
                }
            })
            otpEt[i].setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (event.action != KeyEvent.ACTION_DOWN) {
                    return@OnKeyListener false
                }
                if (keyCode == KeyEvent.KEYCODE_DEL &&
                    otpEt[i].text.toString().isEmpty() && i != 0
                ) {
                    otpEt[i - 1].setText("")
                    otpEt[i - 1].requestFocus()
                }
                false
            })
        }
    }


//    private fun sentotp() {
//
//        if (Utilities.isNetworkAvailable(this)) {
//
//            viewModel.sentotp(
//                SentOtpRequest(
//                    mobile_no = mobile.toString(),
//                    token = Shared_Preferences.getToken().toString()
//                )
//            ).observe(this) {
//                it?.let { resource ->
//                    when (resource.status) {
//                        Status.SUCCESS -> {
//                            hideProgressDialog()
//                            validotp = resource.data?.response?.data?.get(0)?.otp
//                            val index0 = 0
//                            val index1 = 1
//                            val index2 = 2
//                            val index3 = 3
//                            val otp1st: String = validotp?.get(index0).toString()
//                            val otp2nd: String = validotp?.get(index1).toString()
//                            val otp3rd: String = validotp?.get(index2).toString()
//                            val otp4th: String = validotp?.get(index3).toString()
//
//                            binding.otp1.setText(otp1st)
//                            binding.otp2.setText(otp2nd)
//                            binding.otp3.setText(otp3rd)
//                            binding.otp4.setText(otp4th)
//
//
//                        }
//
//                        Status.ERROR -> {
//                            hideProgressDialog()
//                            Log.d(ContentValues.TAG, "print-->" + resource.data?.response?.status)
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


    private fun sentotp2(){


        try{
            if (Utilities.isNetworkAvailable(this)) {

                viewModel2.sentotp(template_id = "65375947d6fc050bbd480bb2", mobile = "91"+mobile.toString()).observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideProgressDialog()
                                if (resource.data?.type.equals("success")){
                                    binding.otp1.setText("")
                                    binding.otp2.setText("")
                                    binding.otp3.setText("")
                                    binding.otp4.setText("")
                                }
//                            validotp = resource.data?.response?.data?.get(0)?.otp
//                            val index0 = 0
//                            val index1 = 1
//                            val index2 = 2
//                            val index3 = 3
//                            val otp1st: String = validotp?.get(index0).toString()
//                            val otp2nd: String = validotp?.get(index1).toString()
//                            val otp3rd: String = validotp?.get(index2).toString()
//                            val otp4th: String = validotp?.get(index3).toString()
//
//                            binding.otp1.setText(otp1st)
//                            binding.otp2.setText(otp2nd)
//                            binding.otp3.setText(otp3rd)
//                            binding.otp4.setText(otp4th)


                            }

                            Status.ERROR -> {
                                hideProgressDialog()
                                Log.d(ContentValues.TAG, "print-->" + resource.data?.type)

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
        }catch (e:Exception){
            Log.d(TAG, "exception-->"+e)
        }



    }




    private fun resendotp(){

        try{
            if (Utilities.isNetworkAvailable(this)) {

                viewModel2.resendotp(retrytype = "text", mobile = "91"+mobile.toString()).observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideProgressDialog()
                                if (resource.data?.type.equals("success")){
                                    binding.otp1.setText("")
                                    binding.otp2.setText("")
                                    binding.otp3.setText("")
                                    binding.otp4.setText("")
                                }
//                            validotp = resource.data?.response?.data?.get(0)?.otp
//                            val index0 = 0
//                            val index1 = 1
//                            val index2 = 2
//                            val index3 = 3
//                            val otp1st: String = validotp?.get(index0).toString()
//                            val otp2nd: String = validotp?.get(index1).toString()
//                            val otp3rd: String = validotp?.get(index2).toString()
//                            val otp4th: String = validotp?.get(index3).toString()
//
//                            binding.otp1.setText(otp1st)
//                            binding.otp2.setText(otp2nd)
//                            binding.otp3.setText(otp3rd)
//                            binding.otp4.setText(otp4th)


                            }

                            Status.ERROR -> {
                                hideProgressDialog()
                                Log.d(ContentValues.TAG, "print-->" + resource.data?.type)

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
        }catch (e:Exception){
            Log.d(TAG, "exception-->"+e)
        }



    }


    private fun verifyOtp(validotp:String) {

        if (Utilities.isNetworkAvailable(this)) {

            viewModel2.otpvalidate(
                    otp = validotp,
                    mobile = "91"+mobile.toString()).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()
                            if (resource.data?.type.equals("success")){

                                Toast.makeText(this, resource.data?.message, Toast.LENGTH_SHORT).show()

                                val intent = Intent(this, FillNewAccountActivity::class.java)
                                startActivity(intent)
                            }else{

                                val builder = AlertDialog.Builder(this)
                                builder.setMessage("Invalid OTP")
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
                            Log.d(ContentValues.TAG, "print-->" + resource.data?.type)
//                            val builder = AlertDialog.Builder(this)
//                            builder.setMessage("Mobile number not registered. Please Create an account.")
//                            builder.setPositiveButton(
//                                "Ok"
//                            ) { dialog, which ->
//
//                                dialog.cancel()
//                                finish()
//                                val intent = Intent(this, FillNewAccountActivity::class.java)
//                                startActivity(intent)
//                            }
//                            val alert = builder.create()
//                            alert.setOnShowListener { arg0 ->
//                                alert.getButton(AlertDialog.BUTTON_POSITIVE)
//                                    .setTextColor(resources.getColor(R.color.yellow))
//                            }
//                            alert.show()
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