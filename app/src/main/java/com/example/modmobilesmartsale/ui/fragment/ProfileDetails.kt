package com.example.modmobilesmartsale.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentProfileDetailsBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.viewmodel.CommonViewModel

class ProfileDetails : Fragment() {

    lateinit var binding: FragmentProfileDetailsBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var mainActivity: MainActivity
  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_details, container, false)
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

        binding.topBarAccount.tvTopBar.text = "Profile Details"


        binding.topBarAccount.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        binding.etName.setText(Shared_Preferences.getName())
        binding.etAddress.setText(Shared_Preferences.getShippingAddress())
        binding.etPhone.setText(Shared_Preferences.getPhoneNo())
        binding.etState.setText(Shared_Preferences.getShippingState())

        mainActivity.setBottomNavigationVisibility(false)

        binding.etName.isEnabled = false
        binding.etName.isCursorVisible = false

        binding.etAddress.isEnabled = false
        binding.etAddress.isCursorVisible = false

        binding.etPhone.isEnabled = false
        binding.etPhone.isCursorVisible = false

        binding.etState.isEnabled = false
        binding.etState.isCursorVisible = false
//
//
//        binding.etAddress.setOnClickListener {
//
//            val navController = Navigation.findNavController(it)
//            navController.navigate(R.id.nav_saved_address)
//        }




    }


    override fun onPause() {
        super.onPause()

        mainActivity.homeBottomIcon()
    }

}