package com.example.modmobilesmartsale.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.base.BaseActivity
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.ActivityCreateNewAccountBinding
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.viewmodel.CommonViewModel

class CreateNewAccountActivity : BaseActivity() {

    private lateinit var binding: ActivityCreateNewAccountBinding
    private lateinit var viewModel: CommonViewModel
    var type = 0

    override fun resourceLayout(): Int {
        return R.layout.activity_create_new_account
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityCreateNewAccountBinding
    }

    override fun setFunction() {
        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        binding.topBarCreateNewAccount.tvTopBar.setText("Back")

        binding.topBarCreateNewAccount.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.rlDistributor.setOnClickListener {
            resetButton()
            binding.rlDistributor.setBackgroundResource(R.drawable.blue_button)
            binding.tvDistributor.setTextColor(Color.WHITE)
            binding.icDistributor.setImageResource(R.drawable.ic_distributor_white)
            type = 1
            Shared_Preferences.setRegistrationType("Distributer")
        }

        binding.rlRetailer.setOnClickListener {
            resetButton()
            binding.rlRetailer.setBackgroundResource(R.drawable.blue_button)
            binding.tvRetailer.setTextColor(Color.WHITE)
            binding.icRetailer.setImageResource(R.drawable.ic_retailer)
            type = 2
            Shared_Preferences.setRegistrationType("Retailer")

        }

        if (type==1){
            resetButton()
            binding.rlDistributor.setBackgroundResource(R.drawable.blue_button)
            binding.tvDistributor.setTextColor(Color.WHITE)
        }else if (type == 2){
            resetButton()
            binding.rlRetailer.setBackgroundResource(R.drawable.blue_button)
            binding.tvRetailer.setTextColor(Color.WHITE)
        }else{
            resetButton()
        }

        binding.buttonNext.setOnClickListener {
            if (type==0){
                Toast.makeText(this, "Please select one type", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, FillNewAccountActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun resetButton(){
        binding.rlDistributor.setBackgroundResource(R.drawable.white_box_grey_border)
        binding.tvDistributor.setTextColor(ContextCompat.getColor(this,R.color.grey2))
        binding.icDistributor.setImageResource(R.drawable.ic_distributor)

        binding.rlRetailer.setBackgroundResource(R.drawable.white_box_grey_border)
        binding.tvRetailer.setTextColor(ContextCompat.getColor(this,R.color.grey2))
        binding.icRetailer.setImageResource(R.drawable.ic_retailer_grey)

    }
}