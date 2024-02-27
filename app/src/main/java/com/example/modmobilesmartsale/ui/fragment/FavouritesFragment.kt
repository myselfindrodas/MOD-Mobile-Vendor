package com.example.modmobilesmartsale.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.example.modmobilesmartsale.data.model.favouritemodel.FavAddRemoveRequest
import com.example.modmobilesmartsale.data.model.getfavouritemodel.Data
import com.example.modmobilesmartsale.data.model.getfavouritemodel.FavListRequest
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentFavouritesBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.adapter.FavouritesAdapter
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel

class FavouritesFragment : Fragment(), FavouritesAdapter.FavouritesItemClickListener {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var mainActivity: MainActivity
    var favouritesAdapter: FavouritesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false)
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

        binding.topBarFavorities.tvTopBar.setText("Favourite")

        binding.topBarFavorities.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        mainActivity.setBottomNavigationVisibility(true)

        favouritesAdapter = FavouritesAdapter(mainActivity, this@FavouritesFragment)
//        val favourites_list = ArrayList<FavouritesData>()
//        for (i in 1..4){
//            favourites_list.add(FavouritesData(R.drawable.phone_image, "20% Off", "Apple I Phone 13 Pro",
//            "(20 Reviews)", "15 In Stock", "GOOD", "4GB / 256GB",
//            "₹ 17120", "₹ 22000", "save ₹ 3120"))
//        }
        binding.rvFavoritiesList.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvFavoritiesList.adapter = favouritesAdapter
        favouriteList()
//        favouritesAdapter!!.updateData(favourites_list)

    }




    override fun onPause() {
        super.onPause()
        mainActivity.homeBottomIcon()
    }



    override fun onResume() {
        super.onResume()

        mainActivity.favouriteBottomIcon()
    }


    private fun favouriteList(){

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.getfavouriteitems(FavListRequest(userid = Shared_Preferences.getUserId(), token = Shared_Preferences.getToken().toString()))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()

                                if (resource.data?.response?.status.equals("true")) {


//                                    favouritesAdapter?.updateData(resource.data?.response?.data!!)
                                    var list: ArrayList<Data> = ArrayList()
                                    for (i in 0 until resource.data!!.response.data.size) {
                                        if (resource.data.response.data[i].imei1 != null) {
                                            list.add(resource.data.response.data[i])
                                        }
                                    }
                                    favouritesAdapter?.updateData(list)

                                    if (list.size == 0){

                                        binding.noProductsAvailable.visibility = View.VISIBLE

                                    }else{

                                        binding.noProductsAvailable.visibility = View.GONE

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




    private fun addremovefavourite(imei1:String){

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.addfav(
                FavAddRemoveRequest(
                    userid = Shared_Preferences.getUserId(),
                    productid = imei1,
                    token = Shared_Preferences.getToken().toString()
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()

                                if (resource.data?.response?.status.equals("true")) {
                                    favouriteList()

//                                    val builder = AlertDialog.Builder(mainActivity)
//                                    builder.setMessage(resource.data?.response?.data!![0].response)
//                                    builder.setPositiveButton(
//                                        "Ok"
//                                    ) { dialog, which ->
//
//                                        dialog.cancel()
//                                        favouriteList()
//
//                                    }
//                                    val alert = builder.create()
//                                    alert.setOnShowListener { arg0 ->
//                                        alert.getButton(AlertDialog.BUTTON_POSITIVE)
//                                            .setTextColor(resources.getColor(R.color.yellow))
//                                    }
//                                    alert.show()


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

    override fun favouritesremoveItemOnClick(
        position: Int,
        list: ArrayList<Data>,
        view: View,
        type: String
    ) {

        if (type.equals("remove")){

            val builder = androidx.appcompat.app.AlertDialog.Builder(mainActivity)
            builder.setMessage("Do you want to remove this from favourite?")
            builder.setPositiveButton(
                "Ok"
            ) { dialog, which ->
                addremovefavourite(list[position].imei1)
                dialog.cancel()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
            val alert = builder.create()
            alert.setOnShowListener { arg0: DialogInterface? ->
                alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
                alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
            }
            alert.show()

        }else{

//            val bundle = Bundle()
//            bundle.putSerializable("itemdetails", list[position])
//            val navController = Navigation.findNavController(binding.root)
//            navController.navigate(R.id.nav_product_details, bundle)
        }

    }

}