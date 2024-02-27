package com.example.modmobilesmartsale.ui.fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentAccountBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.SplashActivity
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.viewmodel.CommonViewModel

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
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

        binding.topBarAccount.tvTopBar.setText("My Account")

        binding.tvProfileDetails.setOnClickListener {
            Toast.makeText(mainActivity, "Page not given", Toast.LENGTH_SHORT).show()
        }

        binding.tvSettings.setOnClickListener {
            Toast.makeText(mainActivity, "Page not given", Toast.LENGTH_SHORT).show()
        }

        binding.tvUserNameInProfile.text = Shared_Preferences.getName()
        binding.tvUserNoInProfile.text = Shared_Preferences.getPhoneNo()


        binding.tvLogout.setOnClickListener {

            val builder = AlertDialog.Builder(mainActivity)
            builder.setMessage("Do you want to logout?")
            builder.setPositiveButton(
                "Ok"
            ) { dialog, which ->
                Shared_Preferences.setLoginStatus(false)
                Shared_Preferences.clearPref()
                val intent = Intent(mainActivity, SplashActivity::class.java)
                startActivity(intent)
                mainActivity.finish()
                dialog.cancel()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
            val alert = builder.create()
            alert.setOnShowListener { arg0: DialogInterface? ->
                alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
                alert.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
            }
            alert.show()
        }

        binding.topBarAccount.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        mainActivity.setBottomNavigationVisibility(false)

        binding.tvSavedAddress.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_saved_address)
        }
    }


    override fun onPause() {
        super.onPause()

        mainActivity.homeBottomIcon()
    }
}