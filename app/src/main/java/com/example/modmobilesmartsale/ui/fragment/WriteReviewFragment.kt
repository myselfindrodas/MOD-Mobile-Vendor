package com.example.modmobilesmartsale.ui.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentWriteReviewBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.utils.GetRealPathFromUri
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.squareup.picasso.Picasso
import com.example.modmobilesmartsale.data.model.myoderlistmodel.Data
import com.example.modmobilesmartsale.data.model.postreviewmodel.PostReviewRequest
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import java.io.Serializable


class WriteReviewFragment : Fragment() {

    private lateinit var binding: FragmentWriteReviewBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: CommonViewModel
    private var request_code1: Int = 0
    var pathFromUri = ""
    var data: Data? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_review, container, false)
        mainActivity = activity as MainActivity
        val root = binding.root
        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }
        viewModel = vm

        val intent = arguments
        if (intent != null && intent.containsKey("orderdetails")) {
            data = getDataSerializable(intent, "orderdetails", Data::class.java) as Data?
            Log.d(ContentValues.TAG, "details-->" + data)
        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        binding.topBarWriteReview.tvTopBar.setText("Write Review")

        binding.tvMobileNameInReviewPage.text = data?.modelName
        binding.tvOrderIdInReviewPage.text = data?.invoiceNo
        binding.tvMobileQualityInReviewPage.text = data?.stockType
        binding.tvMobileStorageInReviewPage.text = data?.memory
        binding.tvMobileOffPriceInReviewPage.text = "₹ " + data?.itemTotal
        binding.tvMobileRealPriceInReviewPage.text = "₹ " + data?.mrp
        binding.tvSavePriceOnMobileInReviewPage.text = "Save ₹ " + data?.itemTotal?.toFloat()?.minus(data?.purchasePrice?.toFloat()!!)



        Picasso.get()
            .load(data?.image1)
            .error(R.drawable.phone_image)
            .placeholder(R.drawable.phone_image)
            .into(binding.ivMobileImageInReviewPage)

        binding.topBarWriteReview.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        mainActivity.setBottomNavigationVisibility(false)

//        binding.rlUploadImage.setOnClickListener {
//            openGallery(1)
//        }

        binding.btnSubmitReview.setOnClickListener {
            if (binding.givenrate.rating.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Add Rating", Toast.LENGTH_SHORT).show()
            } else if (binding.etReview.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Write some review", Toast.LENGTH_SHORT).show()
            } else {

                postReview()
//                val navController = Navigation.findNavController(it)
//                navController.navigateUp()
//                navController.navigate(R.id.nav_reviews)
            }

        }
    }



    private fun postReview() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.postreview(
                PostReviewRequest(
                    name = Shared_Preferences.getName().toString(),
                    productid = data?.imei1.toString(),
                    rate = binding.givenrate.rating.toString(),
                    review = binding.etReview.text.toString(),
                    token = Shared_Preferences.getToken().toString(),
                    userid = Shared_Preferences.getUserId()
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()

                                if (resource.data?.response?.status.equals("true")) {

//                                    val navController = Navigation.findNavController(binding.root)
//                                    navController.navigateUp()
//                                    navController.navigate(R.id.nav_home)
                                    val intent = Intent(context,MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)

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
                                    Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT)
                                        .show()

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


//    private fun openGallery(i: Int) {
//        request_code1 = i
//        if (ContextCompat.checkSelfPermission(
//                mainActivity,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
//            ContextCompat.checkSelfPermission(mainActivity,
//                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
//            ContextCompat.checkSelfPermission(mainActivity,
//                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissionLauncher.launch(
//                arrayOf(
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.CAMERA
//                )
//            )
//        } else {
//            ImagePicker.Companion.with(this)
//                .crop()
//                .compress(1024)
//                .maxResultSize(1080, 1080)
//                .start()
//        }
//    }
//
//    val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        val granted = permissions.entries.all {
//            it.value
//        }
//        if (granted) {
//            ImagePicker.Companion.with(this)
//                .crop()
//                .compress(1024)
//                .maxResultSize(1080, 1080)
//                .start()
//
//        } else {
//            // PERMISSION NOT GRANTED
//            Toast.makeText(mainActivity, "Permission denied", Toast.LENGTH_SHORT).show()
//
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        for (fragment in childFragmentManager.fragments) {
//            fragment.onActivityResult(requestCode, resultCode, data)
//        }
//
//        if (requestCode == 2404 && resultCode == Activity.RESULT_OK) {
//            val fileUri = data!!.data
//            if (request_code1 == 1){
//                try {
//                    Picasso.get()
//                        .load(fileUri)
//                        .into(binding.image1)
//                    binding.tvUploadPhoto.visibility = View.GONE
//                    pathFromUri = GetRealPathFromUri.getPathFromUri(mainActivity, fileUri!!)!!
//
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            } else{}
//        } else if (resultCode == ImagePicker.RESULT_ERROR) {
//            Toast.makeText(mainActivity, ImagePicker.RESULT_ERROR, Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(mainActivity, "Task Cancelled", Toast.LENGTH_SHORT).show()
//        }
//    }


    private fun <T : Serializable?> getDataSerializable(
        @Nullable bundle: Bundle?,
        @Nullable key: String?,
        clazz: Class<T>
    ): Serializable? {
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