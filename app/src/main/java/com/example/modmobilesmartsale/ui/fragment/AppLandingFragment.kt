package com.example.modmobilesmartsale.ui.fragment

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.CategoryListItemData
import com.example.modmobilesmartsale.data.model.FeedBackData
import com.example.modmobilesmartsale.data.model.addtocartmodel.AddtoCartItem
import com.example.modmobilesmartsale.data.model.addtocartmodel.AddtoCartRequest
import com.example.modmobilesmartsale.data.model.dashboardmodel.BannerData
import com.example.modmobilesmartsale.data.model.dashboardmodel.CategoryInfo
import com.example.modmobilesmartsale.data.model.dashboardmodel.DashboardRequest
import com.example.modmobilesmartsale.data.model.dashboardmodel.LatestEditionImg
import com.example.modmobilesmartsale.data.model.dashboardmodel.ShopByBudgetImg
import com.example.modmobilesmartsale.data.model.dashboardmodel.TopBrand
import com.example.modmobilesmartsale.data.model.defaultaddressmodel.DefaultAddressRequest
import com.example.modmobilesmartsale.data.model.invoicecreatemodel.BillJSON
import com.example.modmobilesmartsale.data.model.invoicecreatemodel.InvoiceDetail
import com.example.modmobilesmartsale.data.model.invoicecreatemodel.InvoicecreateRequest
import com.example.modmobilesmartsale.data.model.manageaddressmodel.ManageAddressRequest
import com.example.modmobilesmartsale.data.model.pocreatemodel.OrderDetail
import com.example.modmobilesmartsale.data.model.pocreatemodel.POJSON
import com.example.modmobilesmartsale.data.model.pocreatemodel.POcreateRequest
import com.example.modmobilesmartsale.data.model.registrationsuccessmodel.Recipient
import com.example.modmobilesmartsale.data.model.registrationsuccessmodel.RegistrationSuccessRequest
import com.example.modmobilesmartsale.data.model.stockmodel.Data
import com.example.modmobilesmartsale.data.model.stockmodel.StockRequest
import com.example.modmobilesmartsale.data.model.viewaddressmodel.Addressdata
import com.example.modmobilesmartsale.data.model.viewaddressmodel.ViewAddressRequest
import com.example.modmobilesmartsale.data.model.viewcartmodel.ViewCartRequest
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory2
import com.example.modmobilesmartsale.databinding.FragmentAppLandingBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.SplashActivity
import com.example.modmobilesmartsale.ui.adapter.AddressAdapter
import com.example.modmobilesmartsale.ui.adapter.AndroidPhoneAdapter
import com.example.modmobilesmartsale.ui.adapter.BrandListAdapter
import com.example.modmobilesmartsale.ui.adapter.BudgetListAdapter
import com.example.modmobilesmartsale.ui.adapter.CategoryListAdapter
import com.example.modmobilesmartsale.ui.adapter.CategoryListItemAdapter
import com.example.modmobilesmartsale.ui.adapter.DayDealsAdapter
import com.example.modmobilesmartsale.ui.adapter.FeedBackAdapter
import com.example.modmobilesmartsale.ui.adapter.IPhoneAdapter
import com.example.modmobilesmartsale.ui.adapter.LatestEditionAdapter
import com.example.modmobilesmartsale.ui.adapter.SlidingImagesURLAdapter
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import com.example.modmobilesmartsale.viewmodel.CommonViewModel2
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random

class AppLandingFragment : Fragment(), SlidingImagesURLAdapter.OnItemClickListener,
    CategoryListAdapter.CategoryListOnItemClickListener,
    CategoryListItemAdapter.CategoryListItemOnItemClickListener,
    AndroidPhoneAdapter.AndroidListOnItemClickListener,
    BrandListAdapter.BrandListOnItemClickListener, DayDealsAdapter.DayDealsListOnItemClickListener,
    BudgetListAdapter.BudgetListOnItemClickListener,
    IPhoneAdapter.IphoneListOnItemClickListener,
    AddressAdapter.AddressOnItemClickListener,
    LatestEditionAdapter.LatestEditionListOnItemClickListener {

    private lateinit var binding: FragmentAppLandingBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var viewModel2: CommonViewModel2

    private lateinit var mainActivity: MainActivity
    var slidingImagesURLAdapter: SlidingImagesURLAdapter? = null
    var categoryListAdapter: CategoryListAdapter? = null
    var categoryListItemAdapter: CategoryListItemAdapter? = null
    var brandListAdapter: BrandListAdapter? = null
    var addressAdapter: AddressAdapter?= null
    var dayDealsAdapter: DayDealsAdapter? = null
    var budgetListAdapter: BudgetListAdapter? = null
    var latestEditionAdapter: LatestEditionAdapter? = null
    var androidPhoneAdapter:AndroidPhoneAdapter?=null
    var iPhoneAdapter:IPhoneAdapter?=null
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val pERMISSION_ID = 42
    private val headerHandler = Handler()
    var currentPage = 0
    var delay = 2000
    var runnable: Runnable? = null
    var dialog:BottomSheetDialog?=null

    var modelcode = ""
    var price = ""
    var productid = ""
    var purchaseprice = ""
    var stocktype = ""

    var shippingfromaddress = ""
    var parentstate = ""
    var parentcity = ""
    var shippingcity = ""
    var totalamount = ""
    var totalqty = ""
    var margintype = ""
    var cgstPer = ""
    var sgstPer = ""
    var igstPer = ""
    var hsncode = ""
    var taxablevalue = ""
    var type = ""
    var orderdetailsItem = ArrayList<OrderDetail>()
    var PHONENAME = ""
    var invoicedetailsItem = ArrayList<InvoiceDetail>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_app_landing, container, false)
        mainActivity = activity as MainActivity
        val root = binding.root
        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }
        viewModel = vm

        val vm2: CommonViewModel2 by viewModels {
            CommonModelFactory2(ApiHelper(ApiClient.apiService2))
        }

        viewModel2 = vm2
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!Places.isInitialized()) {
            Places.initialize(mainActivity, getString(R.string.google_maps_key))
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mainActivity)

//        getCurrentLocation()


        slidingImagesURLAdapter = SlidingImagesURLAdapter(mainActivity, this@AppLandingFragment)
        binding.slidingViewPager.setAdapter(slidingImagesURLAdapter)
        binding.dotsIndicatorTop.attachTo(binding.slidingViewPager)

        binding.btnSearchLocation.setOnClickListener {
//            openSearchBar()
            showBottomDialog()
        }

        binding.topNavBar.icMenu.setOnClickListener {
            mainActivity.openDrawer()
        }

        binding.topNavBar.ivLogout.setOnClickListener {

            val builder = androidx.appcompat.app.AlertDialog.Builder(mainActivity)
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
                alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
                alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
            }
            alert.show()
        }

        binding.topNavBar.ivSearch.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_search)
        }

        binding.viewTopBrands.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("productTitle", "TopBrands")
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_product_list, bundle)
        }

        binding.viewBudget.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("productTitle", "Budget")
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_product_list, bundle)
        }

        binding.viewLatestEditions.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("productTitle", "LatestEdition")
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_product_list, bundle)
        }

        binding.viewDealsOfDay.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("productTitle", "dealsoftheday")
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_product_list, bundle)
        }


        binding.viewAndroidPhones.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("productTitle", "android")
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_product_list, bundle)
        }


        binding.viewIPhones.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("productTitle", "ios")
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_product_list, bundle)
        }



        categoryListAdapter = CategoryListAdapter(mainActivity, this@AppLandingFragment)
        binding.categoryList.layoutManager =
            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryList.adapter = categoryListAdapter

        brandListAdapter = BrandListAdapter(mainActivity, this@AppLandingFragment)
        binding.topBrandList.layoutManager = GridLayoutManager(mainActivity, 4)
        binding.topBrandList.adapter = brandListAdapter

        dayDealsAdapter = DayDealsAdapter(mainActivity, this@AppLandingFragment,)
        binding.dealsOfDayList.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.dealsOfDayList.adapter = dayDealsAdapter

        budgetListAdapter = BudgetListAdapter(mainActivity, this@AppLandingFragment)
        binding.budgetList.layoutManager =
            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.budgetList.adapter = budgetListAdapter

        androidPhoneAdapter = AndroidPhoneAdapter(mainActivity, this@AppLandingFragment)
        binding.androidPhonesList.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.androidPhonesList.adapter = androidPhoneAdapter

        iPhoneAdapter = IPhoneAdapter(mainActivity, this@AppLandingFragment)
        binding.iPhonesList.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.iPhonesList.adapter = iPhoneAdapter

        latestEditionAdapter = LatestEditionAdapter(mainActivity, this@AppLandingFragment)
        binding.latestEditionsList.layoutManager =
            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.latestEditionsList.adapter = latestEditionAdapter








        DashboardList()
        addresslist()

    }


//    private fun openSearchBar() {
//        val fields = listOf(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG)
//        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
//            .build(mainActivity)
//        resolutionForPlaceResult.launch(intent)
//    }

//    private val resolutionForPlaceResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
//            if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
//
//                val place = Autocomplete.getPlaceFromIntent(activityResult.data!!)
//                val address = place.address
//
//                binding.tvCurrentLocation.text = address
//
//            } else if (activityResult.resultCode == AutocompleteActivity.RESULT_ERROR) {
//                mainActivity.showProgressDialog()
//            } else if (activityResult.resultCode == AppCompatActivity.RESULT_CANCELED) {
//            }
//        }

//    private fun getCurrentLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                mainActivity,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(
//                mainActivity,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            if (isLocationEnabled()) {
//                mFusedLocationClient.lastLocation.addOnCompleteListener(mainActivity) { task ->
//                    val location: Location? = task.result
//                    if (location != null) {
//                        val latitude = location.latitude.toString()
//                        val longitude = location.longitude.toString()
//                        reverseGeocoding(latitude, longitude)
//                    }
//                }
//            } else {
//                Toast.makeText(mainActivity, "Please turn on location", Toast.LENGTH_LONG).show()
//            }
//        } else {
//            requestPermissions()
//        }
//    }

//    private fun isLocationEnabled(): Boolean {
//        val locationManager = mainActivity?.getSystemService(
//            Context.LOCATION_SERVICE
//        ) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//    }

//    private fun requestPermissions() {
//        ActivityCompat.requestPermissions(
//            mainActivity,
//            arrayOf(
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ), pERMISSION_ID
//        )
//    }

//    private fun reverseGeocoding(lat: String, long: String) {
//        val geocoder: Geocoder
//        val addresses: List<Address>?
//        geocoder = Geocoder(mainActivity, Locale.getDefault())
//        try {
//            addresses = geocoder.getFromLocation(
//                lat.toDouble(),
//                long.toDouble(),
//                1
//            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//            println("Address$addresses")
//            if (addresses!!.size > 0) {
//                val sourceAddress =
//                    addresses!![0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                val sourceCity = addresses!![0].locality
//                val sourceState = addresses!![0].adminArea
//                val sourceCountry = addresses!![0].countryName
//                val sourcePinCode = addresses!![0].postalCode
//                val sourceKnownName = addresses!![0].featureName
//                binding.tvCurrentLocation.setText(sourceAddress)
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }


    private fun DashboardList() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            var userType=""
            if (Shared_Preferences.getUserType().equals("Retailer")){
                userType = "Ret"
            }else{
                userType = "Dist"
            }
            viewModel.dashboard(DashboardRequest(token = Shared_Preferences.getToken().toString(),
                user_id = Shared_Preferences.getUserId(),
                user_type_app = userType))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()

                                if (resource.data?.response?.status.equals("true")) {

                                    slidingImagesURLAdapter?.updateImageData(resource.data?.response?.data?.bannerData!!)
                                    budgetListAdapter?.updateData(resource.data?.response?.data?.shopByBudgetImg!!)
                                    latestEditionAdapter?.updateData(resource.data?.response?.data?.latestEditionImg!!)
                                    brandListAdapter?.updateData(resource.data?.response?.data?.topBrands!!)
                                    categoryListAdapter?.updateData(resource.data?.response?.data?.categoryInfo!!)
                                    getstocklist()

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


    private fun getstocklist() {

        var userType=""
        if (Shared_Preferences.getUserType().equals("Retailer")){
            userType = "Ret"
        }else{
            userType = "Dist"
        }

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.getstock(
                StockRequest(
                    ascCode = "MMWHDL002",
                    brandId = "",
                    hotDeal = "",
                    search = "",
                    imei = "",
                    modelCode = "",
                    color="",
                    memory = "",
                    modelName = "",
                    phoneVersion = "",
                    price = "",
                    stockType = "",
                    user_type_app = userType,
                    token = Shared_Preferences.getToken().toString()
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {
                                val dealsofthedaylist = ArrayList<Data>()
                                val androidlist = ArrayList<Data>()
                                val iphonelist = ArrayList<Data>()
                                for (i in 0 until resource.data?.response?.data!!.size) {
                                    if (resource.data.response.data[i].dealOfDays.equals("Y")){
                                        dealsofthedaylist.add(resource.data.response.data[i])
                                        dayDealsAdapter?.updateData(dealsofthedaylist)
                                    }
                                    if (resource.data.response.data[i].phoneVersion.equals("Android")){
                                        androidlist.add(resource.data.response.data[i])
                                        androidPhoneAdapter?.updateData(androidlist)
                                    }

                                    if (resource.data.response.data[i].phoneVersion.equals("Iphone")){
                                        iphonelist.add(resource.data.response.data[i])
                                        iPhoneAdapter?.updateData(iphonelist)
                                    }

                                }



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


    override fun onPause() {
        super.onPause()
        headerHandler.removeCallbacks(runnable!!)
    }


    override fun onStop() {
        super.onStop()
        headerHandler.removeCallbacks(runnable!!)
    }


    override fun onResume() {
        super.onResume()

        mainActivity.setBottomNavigationVisibility(true)
        mainActivity.dragDrawer()

        headerHandler.postDelayed(Runnable {
            headerHandler.postDelayed(runnable!!, delay.toLong())
            if (currentPage === slidingImagesURLAdapter!!.itemCount + 1 - 1) {
                currentPage = 0
            }
            binding.slidingViewPager.setCurrentItem(currentPage++, true)

        }.also { runnable = it }, delay.toLong())
        super.onResume()

        countCart()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mainActivity.finishAffinity()
                    if (activity != null) {
                        activity?.finish()
                    }

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }


    override fun onClick(position: Int, list: ArrayList<BannerData>, view: View) {

        val bundle = Bundle()
        bundle.putString("productTitle", "brand")
        bundle.putString("brandid", list[position].brandid)
        val navController = Navigation.findNavController(binding.root)
        navController.navigate(R.id.nav_product_list, bundle)

    }

    override fun brandListOnClick(position: Int, list: ArrayList<TopBrand>, view: View) {

//        val bundle = Bundle()
//        bundle.putString("brandname", list[position].name)
//        val navController = Navigation.findNavController(binding.root)
//        navController.navigate(R.id.nav_product_list, bundle)


        val bundle = Bundle()
        bundle.putString("productTitle", "brand")
        bundle.putString("brandid", list[position].brandId)
        val navController = Navigation.findNavController(binding.root)
        navController.navigate(R.id.nav_product_list, bundle)

    }

    override fun budgetListOnClick(position: Int, list: ArrayList<ShopByBudgetImg>, view: View) {


        val bundle = Bundle()
        bundle.putString("productTitle", "hotdeals")
        val navController = Navigation.findNavController(binding.root)
        navController.navigate(R.id.nav_product_list, bundle)

    }

    override fun categoryListOnClick(position: Int, list: ArrayList<CategoryInfo>, view: View, type:String) {

        if (type.equals("info")){
            showBottomDialog(list[position].gradeInfo, list[position].stockName)
        }else{

            val bundle = Bundle()
            bundle.putString("imeibox", list[position].imeiBox)
            bundle.putString("productTitle", "category")
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.nav_product_list, bundle)

        }
    }

    override fun dayDealsOnClick(position: Int, list: ArrayList<Data>, view: View, type:String, phoneName: String) {

        PHONENAME = phoneName
        modelcode = list[position].modelCode
        price = list[position].custPrice
        productid = list[position].imei1
        purchaseprice = list[position].purchasePrice
        stocktype = list[position].stockType

        if (type == "Abuy"){


            dialog = BottomSheetDialog(mainActivity,R.style.SheetDialog)
            dialog!!.setContentView(R.layout.bottom_sheet_buy_now)
            val addressListItems = dialog!!.findViewById<RecyclerView>(R.id.addressListItems)
            val btnAddNewAddress = dialog!!.findViewById<Button>(R.id.btnAddAddress)
            val btnContinue = dialog!!.findViewById<Button>(R.id.btnBuy)

            addressAdapter = AddressAdapter(mainActivity, this@AppLandingFragment)
            addressListItems!!.layoutManager = LinearLayoutManager(mainActivity)
            addressListItems.adapter = addressAdapter
            addresslist()
            dialog!!.show()
            btnAddNewAddress!!.setOnClickListener {
                val navController = Navigation.findNavController(binding.root)
                navController.navigate(R.id.nav_addaddress)
                dialog!!.dismiss()
            }
            btnContinue!!.setOnClickListener {
                if (Shared_Preferences.getShippingAddress()!!.isEmpty()) {
                    Toast.makeText(mainActivity, "Select Shipping Address First", Toast.LENGTH_SHORT)
                        .show()
                    val navController = Navigation.findNavController(binding.root)
                    navController.navigate(R.id.nav_saved_address)
                } else {

                    directbuy()

                }
                dialog!!.dismiss()

            }

        }else if(type == "Acart"){

            addCart()

        } else if(type == "Aclick"){

            val bundle = Bundle()
            bundle.putSerializable("itemdetails", list[position])
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.nav_product_details, bundle)

        }


    }




    private fun showBottomDialog() {
        dialog = BottomSheetDialog(mainActivity,R.style.SheetDialog)
        dialog!!.setContentView(R.layout.bottom_sheet_address)
        val addressListItems = dialog!!.findViewById<RecyclerView>(R.id.addressListItems)
        val btnAddNewAddress = dialog!!.findViewById<Button>(R.id.btnAddNewAddress)

        addressAdapter = AddressAdapter(mainActivity, this@AppLandingFragment)
        addressListItems!!.layoutManager = LinearLayoutManager(mainActivity)
        addressListItems.adapter = addressAdapter
        addresslist()
        dialog!!.show()
        btnAddNewAddress!!.setOnClickListener {
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.nav_addaddress)
            dialog!!.dismiss()
//            openSearchBar()
//            dialog.dismiss()
        }
    }


    private fun showBottomDialog(info: String, stockname:String) {
        val dialog = BottomSheetDialog(mainActivity, R.style.SheetDialog)
        dialog.setContentView(R.layout.bottom_sheet_cateegory)
        val tvInfoCategory = dialog.findViewById<TextView>(R.id.tvInfoCategory)
        val tvCategoryName = dialog.findViewById<TextView>(R.id.tvCategoryName)
        if (info.isNullOrEmpty()) {
            tvInfoCategory?.text = "No info provided"

        }else{
            tvInfoCategory?.text = info

        }
        tvCategoryName?.text = stockname
//        categoryListItemAdapter = CategoryListItemAdapter(mainActivity, this@AppLandingFragment)
//        val category_list_item = ArrayList<CategoryListItemData>()
//        for (i in 1..4) {
//            category_list_item.add(
//                CategoryListItemData(
//                    "Lorem Ipsum is simply dummy text of the",
//                    "CateGoryList"
//                )
//            )
//        }
//        categoryListItems!!.layoutManager = LinearLayoutManager(mainActivity)
//        categoryListItems!!.adapter = categoryListItemAdapter
//        categoryListItemAdapter!!.updateData(category_list_item)
        dialog.show()
    }


    private fun addresslist(){
        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.viewaddress(
                ViewAddressRequest(
                    token = Shared_Preferences.getToken().toString(),
                    userId = Shared_Preferences.getUserId()
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {

                                addressAdapter?.updateData(resource.data?.response?.data?.addressdata!!)
                                for (i in 0 until resource.data?.response?.data?.addressdata!!.size) {
                                    if (resource.data.response.data.addressdata[i].default_type.equals("Y")) {
                                        Shared_Preferences.setShippingAddress(resource.data.response.data.addressdata[i].locality+","+
                                                resource.data.response.data.addressdata[i].city+","+ resource.data.response.data.addressdata[i].state)
                                        Shared_Preferences.setShippingState(resource.data.response.data.addressdata[i].state)
                                        Shared_Preferences.setShippingCity(resource.data.response.data.addressdata[i].city)
                                        Shared_Preferences.setShippingZip(resource.data.response.data.addressdata[i].pincode)

                                        binding.tvSelectedLocation.text = resource.data.response.data.addressdata[i].locality+","+
                                                resource.data.response.data.addressdata[i].city+","+ resource.data.response.data.addressdata[i].state

                                    }
                                }
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

    override fun categoryListItemOnClick(
        position: Int,
        list: ArrayList<CategoryListItemData>,
        view: View
    ) {

    }

    override fun latesteditionListOnClick(
        position: Int,
        mLatesteditionList: ArrayList<LatestEditionImg>,
        view: View
    ) {
        val bundle = Bundle()
        bundle.putString("pricerange", mLatesteditionList[position].price_range)
        bundle.putString("productTitle", "range")
        val navController = Navigation.findNavController(binding.root)
        navController.navigate(R.id.nav_product_list, bundle)

    }

    override fun AndroidOnClick(position: Int, list: ArrayList<Data>, view: View, type:String, phoneName: String) {

        PHONENAME = phoneName
        modelcode = list[position].modelCode
        price = list[position].custPrice
        productid = list[position].imei1
        purchaseprice = list[position].purchasePrice
        stocktype = list[position].stockType

        if (type == "Abuy"){

            dialog = BottomSheetDialog(mainActivity,R.style.SheetDialog)
            dialog!!.setContentView(R.layout.bottom_sheet_buy_now)
            val addressListItems = dialog!!.findViewById<RecyclerView>(R.id.addressListItems)
            val btnAddNewAddress = dialog!!.findViewById<Button>(R.id.btnAddAddress)
            val btnContinue = dialog!!.findViewById<Button>(R.id.btnBuy)

            addressAdapter = AddressAdapter(mainActivity, this@AppLandingFragment)
            addressListItems!!.layoutManager = LinearLayoutManager(mainActivity)
            addressListItems.adapter = addressAdapter
            addresslist()
            dialog!!.show()
            btnAddNewAddress!!.setOnClickListener {
                val navController = Navigation.findNavController(binding.root)
                navController.navigate(R.id.nav_addaddress)
                dialog!!.dismiss()
            }
            btnContinue!!.setOnClickListener {
                if (Shared_Preferences.getShippingAddress()!!.isEmpty()) {
                    Toast.makeText(mainActivity, "Select Shipping Address First", Toast.LENGTH_SHORT)
                        .show()
                    val navController = Navigation.findNavController(binding.root)
                    navController.navigate(R.id.nav_saved_address)
                } else {

                    directbuy()

                }
                dialog!!.dismiss()

            }

        }else if(type == "Acart"){

            addCart()

        } else if(type == "Aclick"){

            val bundle = Bundle()
            bundle.putSerializable("itemdetails", list[position])
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.nav_product_details, bundle)

        }

    }

    private fun addCart() {

        val cartItem = ArrayList<AddtoCartItem>()
        cartItem.add(
            AddtoCartItem(
                "",
                modelcode,
                "",
                "",
                price,
                productid,
                purchaseprice,
                "1",
                "P",
                "MMWHDL002",
                Shared_Preferences.getUserId(),
                stocktype,
                Shared_Preferences.getUserId()


            )
        )

        addTocart(cartItem)

    }

    private fun addTocart(cartItem: ArrayList<AddtoCartItem>) {


        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.addtocart(
                AddtoCartRequest(
                    cartJSON = cartItem,
                    token = Shared_Preferences.getToken().toString()
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()

                                if (resource.data?.get(0)!!.status.equals("true")){


                                    AestheticDialog.Builder(mainActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                                        .setTitle("Item Added To Cart")
                                        .setMessage(resource.data.get(0).response)
                                        .setCancelable(false)
                                        .setDarkMode(false)
                                        .setGravity(Gravity.CENTER)
                                        .setAnimation(DialogAnimation.SHRINK)
                                        .setOnClickListener(object : OnDialogClickListener {
                                            override fun onClick(dialog: AestheticDialog.Builder) {
                                                dialog.dismiss()
                                                val navController = Navigation.findNavController(binding.root)
                                                navController.navigate(R.id.nav_cart)
                                            }
                                        })
                                        .show()




                                }


                            }

                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()
                                //Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
//                                if (it.message!!.contains("401", true)) {
//                                    val builder = AlertDialog.Builder(mainActivity)
//                                    builder.setMessage("Invalid Employee Id / Password")
//                                    builder.setPositiveButton(
//                                        "Ok"
//                                    ) { dialog, which ->
//
//                                        dialog.cancel()
//
//                                    }
//                                    val alert = builder.create()
//                                    alert.setOnShowListener { arg0 ->
//                                        alert.getButton(AlertDialog.BUTTON_POSITIVE)
//                                            .setTextColor(resources.getColor(R.color.yellow))
//                                    }
//                                    alert.show()
//                                } else
//                                    Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT)
//                                        .show()

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

    private fun directbuy() {

        val cartItem = ArrayList<AddtoCartItem>()
        cartItem.add(
            AddtoCartItem(
                "",
                modelcode,
                "",
                "",
                price,
                productid,
                purchaseprice,
                "1",
                "P",
                "MMWHDL002",
                Shared_Preferences.getUserId(),
                stocktype,
                Shared_Preferences.getUserId()


            )
        )

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.addtocart(
                AddtoCartRequest(
                    cartJSON = cartItem, token = Shared_Preferences.getToken().toString()
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.get(0)!!.status.equals("true")) {

                                viewCart()
                            }


                        }

                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()
                            //Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
//                                if (it.message!!.contains("401", true)) {
//                                    val builder = AlertDialog.Builder(mainActivity)
//                                    builder.setMessage("Invalid Employee Id / Password")
//                                    builder.setPositiveButton(
//                                        "Ok"
//                                    ) { dialog, which ->
//
//                                        dialog.cancel()
//
//                                    }
//                                    val alert = builder.create()
//                                    alert.setOnShowListener { arg0 ->
//                                        alert.getButton(AlertDialog.BUTTON_POSITIVE)
//                                            .setTextColor(resources.getColor(R.color.yellow))
//                                    }
//                                    alert.show()
//                                } else
//                                    Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT)
//                                        .show()

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

    private fun viewCart() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.viewcart(
                ViewCartRequest(
                    token = Shared_Preferences.getToken().toString(),
                    userid = Shared_Preferences.getUserId()
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {

                                shippingfromaddress = resource.data!!.response.data.sellerAddress
                                parentstate = resource.data.response.data.parentState
                                parentcity = resource.data.response.data.parentCity
                                shippingfromaddress = resource.data.response.data.sellerAddress
                                shippingcity = resource.data.response.data.parentCity

                                totalamount = resource.data.response.data.totalAmt
                                totalqty = resource.data.response.data.totQnty.toString()
                                parentstate = resource.data.response.data.parentState
                                margintype = resource.data.response.data.cartdata[0].tax_type
                                cgstPer = resource.data.response.data.cartdata[0].cgstPer.toString()
                                sgstPer = resource.data.response.data.cartdata[0].sgstPer.toString()
                                igstPer = resource.data.response.data.cartdata[0].igstPer.toString()
                                hsncode = resource.data.response.data.cartdata[0].hsnCode.toString()
                                taxablevalue = resource.data.response.data.cartdata[0].taxableValue.toString()
                                type = resource.data.response.data.cartdata[0].type

                                orderdetailsItem.add(
                                    OrderDetail(
                                        stocktype,
                                        PHONENAME,
                                        price,
                                        "1",
                                        modelcode
                                    )
                                )

                                pocreate()


                            }


                        }

                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
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

    private fun pocreate() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val orderDate = sdf.format(Date())
            viewModel.pocreate(
                POcreateRequest(
                    pOJSON = POJSON(
                        "",
                        Shared_Preferences.getUserId(),
                        orderDate.toString(),
                        orderdetailsItem,
                        generateUniqueNumber().toString(),
                        Shared_Preferences.getEmail().toString(),
                        Shared_Preferences.getPhoneNo(),
                        Shared_Preferences.getName().toString(),
                        "wallet",
                        "wallet",
                        Shared_Preferences.getShippingAddress().toString(),
                        Shared_Preferences.getShippingCity().toString(),
                        Shared_Preferences.getShippingState().toString(),
                        Shared_Preferences.getShippingZip().toString(),
                        price,
                        totalqty
                    ),
                    token = Shared_Preferences.getToken().toString()
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()

                                if (resource.data?.status.equals("true")) {
//
//                                    Toast.makeText(
//                                        mainActivity,
//                                        resource.data?.crmPoNo,
//                                        Toast.LENGTH_SHORT
//                                    ).show()

                                    try {

//                                        val cartList = cartListAdapter?.getcartItems()!!
                                        var sgstcost = ""
                                        var cgstcost = ""
                                        var igstcost = ""
                                        var marginprice = ""
                                        var totalsgst = 0
                                        var totalcgst = 0
                                        var totaligst = 0
                                        var totaltaxableamount = 0
//                                        var totalamount = 0

                                        if (parentstate.equals("Delhi")) {
                                            if (margintype.equals("margin")) {
                                                marginprice = (price.toDouble().minus(purchaseprice.toDouble())).toString()
                                                sgstcost = ((sgstPer.toDouble() / 100.00) * marginprice.toDouble()).toString()
                                                cgstcost = ((cgstPer.toDouble() / 100.00) * marginprice.toDouble()).toString()

                                            } else {
                                                sgstcost = ((sgstPer.toDouble() / 100.00) * price.toDouble()).toString()
                                                cgstcost = ((cgstPer.toDouble() / 100.00) * price.toDouble()).toString()

                                            }

                                            totalcgst += cgstcost.toDouble().toInt()
                                            totalsgst += sgstcost.toDouble().toInt()

                                            Log.d(ContentValues.TAG, "totalsgst-->" + totalsgst)
                                            Log.d(ContentValues.TAG, "totalcgst-->" + totalcgst)

                                        } else {
                                            if (margintype.equals("margin")) {
                                                marginprice = (price.toDouble().minus(purchaseprice.toDouble())).toString()
                                                igstcost = ((igstPer.toDouble() / 100.00) * marginprice.toDouble()).toString()
                                            } else {
                                                igstcost = ((igstPer.toDouble() / 100.00) * price.toDouble()).toString()
                                            }

                                            totaligst += igstcost.toDouble().toInt()
                                            Log.d(ContentValues.TAG, "totaligst-->" + totaligst)

                                        }

                                        invoicedetailsItem.add(
                                            InvoiceDetail(
                                                cgstcost,
                                                cgstPer,
                                                "0",
                                                hsncode,
                                                igstcost,
                                                igstPer,
                                                productid,
                                                productid,
                                                stocktype,
                                                price,
                                                modelcode,
                                                PHONENAME,
                                                price,
                                                purchaseprice,
                                                "1",
                                                sgstcost,
                                                sgstPer,
                                                taxablevalue.toDouble(),
                                                type
                                            )
                                        )
                                        totaltaxableamount += taxablevalue.toDouble().toInt()
//                                            totalamount += cartList[i].amount.toDouble().toInt()




                                        invoicecreate(
                                            resource.data?.appOrderNo.toString(),
                                            resource.data?.poNumber.toString(),
                                            orderDate,
                                            totalqty,
                                            "0",
                                            totaltaxableamount.toString(),
                                            totalcgst.toString(),
                                            totalsgst.toString(),
                                            price,
                                            totaligst.toString()

                                        )

                                    }catch (e:Exception){
                                        Log.d(ContentValues.TAG, "exception-->"+e)
                                    }


                                }


                            }

                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()
                                //Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
//                                if (it.message!!.contains("401", true)) {
//                                    val builder = AlertDialog.Builder(mainActivity)
//                                    builder.setMessage("Invalid Employee Id / Password")
//                                    builder.setPositiveButton(
//                                        "Ok"
//                                    ) { dialog, which ->
//
//                                        dialog.cancel()
//
//                                    }
//                                    val alert = builder.create()
//                                    alert.setOnShowListener { arg0 ->
//                                        alert.getButton(AlertDialog.BUTTON_POSITIVE)
//                                            .setTextColor(resources.getColor(R.color.yellow))
//                                    }
//                                    alert.show()
//                                } else
//                                    Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT)
//                                        .show()

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

    private fun invoicecreate(
        orderno: String,
        pono: String,
        orderdate: String,
        totalqty: String,
        totaldiscount: String,
        totaltaxableamt: String,
        totalcgst: String,
        totalsgst: String,
        totalamount: String,
        totaligst:String
    ) {


        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.invoicecreate(
                InvoicecreateRequest(
                    billJSON = BillJSON(
                        "",
                        Shared_Preferences.getUserId(),
                        "",
                        orderdate,
                        invoicedetailsItem,
                        pono,
                        orderno,
                        Shared_Preferences.getEmail().toString(),
                        Shared_Preferences.getPhoneNo(),
                        Shared_Preferences.getName().toString(),
                        "wallet",
                        shippingfromaddress,
                        parentstate,
                        Shared_Preferences.getShippingAddress().toString(),
                        Shared_Preferences.getShippingCity().toString(),
                        Shared_Preferences.getShippingState().toString(),
                        Shared_Preferences.getShippingZip().toString(),
                        Shared_Preferences.getToken().toString(),
                        totalamount,
                        totalcgst,
                        totaldiscount,
                        totaligst,
                        totalqty,
                        totalsgst,
                        totaltaxableamt
                    )
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()

                                if (resource.data?.status.equals("true")) {

                                    AestheticDialog.Builder(mainActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                                        .setTitle("Item Order Successfully")
                                        .setMessage("Invoice Created Successfully "+resource.data?.crmInvoiceNo)
                                        .setCancelable(false)
                                        .setDarkMode(false)
                                        .setGravity(Gravity.CENTER)
                                        .setAnimation(DialogAnimation.SHRINK)
                                        .setOnClickListener(object : OnDialogClickListener {
                                            override fun onClick(dialog: AestheticDialog.Builder) {
                                                dialog.dismiss()
                                                ordersuccessmsg()
                                            }
                                        })
                                        .show()


//                                    val builder = androidx.appcompat.app.AlertDialog.Builder(mainActivity)
//                                    builder.setMessage("Invoice Created Successfully "+resource.data?.crmInvoiceNo)
//                                    builder.setPositiveButton(
//                                        "Ok"
//                                    ) { dialog, which ->
//                                        ordersuccessmsg()
//                                        dialog.cancel()
//                                    }
//
//                                    val alert = builder.create()
//                                    alert.setOnShowListener { arg0: DialogInterface? ->
//                                        alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
//                                            .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
//                                    }
//                                    alert.show()


                                }


                            }

                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()
                                //Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
//                                if (it.message!!.contains("401", true)) {
//                                    val builder = AlertDialog.Builder(mainActivity)
//                                    builder.setMessage("Invalid Employee Id / Password")
//                                    builder.setPositiveButton(
//                                        "Ok"
//                                    ) { dialog, which ->
//
//                                        dialog.cancel()
//
//                                    }
//                                    val alert = builder.create()
//                                    alert.setOnShowListener { arg0 ->
//                                        alert.getButton(AlertDialog.BUTTON_POSITIVE)
//                                            .setTextColor(resources.getColor(R.color.yellow))
//                                    }
//                                    alert.show()
//                                } else
//                                    Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT)
//                                        .show()

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

    private fun ordersuccessmsg(){


        if (Utilities.isNetworkAvailable(mainActivity)) {

            val recipientslist = ArrayList<Recipient>()
            recipientslist.add(
                Recipient(
                    "91"+Shared_Preferences.getPhoneNo(),
                    "VALUE1",
                    "VALUE2"
                )
            )
            viewModel2.successmessage(
                RegistrationSuccessRequest(
                    recipientslist,
                    shortUrl = "1",
                    templateId = "6540bb11d6fc0551f40a0a26")
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.type.equals("success")){

                                Toast.makeText(mainActivity, "Order Placed Successfully" , Toast.LENGTH_SHORT).show()
//                                val navController = Navigation.findNavController(binding.root)
//                                navController.navigate(R.id.nav_home)
                                val intent = Intent(context,MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)


                            }else{

                                val builder = AlertDialog.Builder(mainActivity)
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
                            mainActivity.hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->" + resource.data?.message)
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

                        }

                        Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }

                    }

                }
            }


        } else {

            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()

        }
    }

    fun generateUniqueNumber(): Int {
        val currentTimeMillis = System.currentTimeMillis()
        val random = Random(currentTimeMillis)
        return random.nextInt()
    }

    override fun IphoneOnClick(position: Int, list: ArrayList<Data>, view: View, type:String, phoneName: String) {
        PHONENAME = phoneName
        modelcode = list[position].modelCode
        price = list[position].custPrice
        productid = list[position].imei1
        purchaseprice = list[position].purchasePrice
        stocktype = list[position].stockType

        if (type == "buy"){

            dialog = BottomSheetDialog(mainActivity,R.style.SheetDialog)
            dialog!!.setContentView(R.layout.bottom_sheet_buy_now)
            val addressListItems = dialog!!.findViewById<RecyclerView>(R.id.addressListItems)
            val btnAddNewAddress = dialog!!.findViewById<Button>(R.id.btnAddAddress)
            val btnContinue = dialog!!.findViewById<Button>(R.id.btnBuy)

            addressAdapter = AddressAdapter(mainActivity, this@AppLandingFragment)
            addressListItems!!.layoutManager = LinearLayoutManager(mainActivity)
            addressListItems.adapter = addressAdapter
            addresslist()
            dialog!!.show()
            btnAddNewAddress!!.setOnClickListener {
                val navController = Navigation.findNavController(binding.root)
                navController.navigate(R.id.nav_addaddress)
                dialog!!.dismiss()
            }
            btnContinue!!.setOnClickListener {
                if (Shared_Preferences.getShippingAddress()!!.isEmpty()) {
                    Toast.makeText(mainActivity, "Select Shipping Address First", Toast.LENGTH_SHORT)
                        .show()
                    val navController = Navigation.findNavController(binding.root)
                    navController.navigate(R.id.nav_saved_address)
                } else {

                    directbuy()

                }
                dialog!!.dismiss()

            }

        }else if(type == "cart"){

            addCart()
        }
        else{
            val bundle = Bundle()
            bundle.putSerializable("itemdetails", list[position])
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.nav_product_details, bundle)
        }
    }






    private fun primaryaddress(addressid:String){

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.defaultaddress(
                DefaultAddressRequest(
                    id = addressid,
                    userid = Shared_Preferences.getUserId(),
                    token = Shared_Preferences.getToken().toString()
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {
//                                Toast.makeText(mainActivity, resource.data?.response?.data?.get(0)!!.response, Toast.LENGTH_SHORT).show()
//                                val navController = Navigation.findNavController(binding.root)
//                                navController.navigate(R.id.nav_home)
                                Toast.makeText(mainActivity, "Default Address Changed Successfully", Toast.LENGTH_SHORT).show()
                                addresslist()

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



    private fun countCart() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.viewcart(
                ViewCartRequest(
                    token = Shared_Preferences.getToken().toString(),
                    userid = Shared_Preferences.getUserId()
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {

                                if (resource.data?.response?.data?.cartCount==0){
                                    mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_cart).isVisible = false
                                }else{
                                    mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_cart).isVisible = true
                                    mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_cart).number = resource.data?.response?.data?.cartCount!!.toInt()
                                    mainActivity.bottomNavView.getOrCreateBadge(R.id.nav_cart).backgroundColor = Color.parseColor("#E63425")

                                }

//                                if (resource.data?.response?.data?.cartdata!!.isEmpty()) {
//
//                                    binding.llButtonsInCart.visibility = View.GONE
//                                    binding.llpricedetails.visibility = View.GONE
//                                    binding.llApplyCouponCode.visibility = View.GONE
//                                    binding.rvCartList.visibility = View.GONE
//                                    binding.tvEmptycart.visibility = View.VISIBLE
//
//                                } else {
//
//                                    try {
//
//                                        finalamount = resource.data.response.data.totalAmt
//                                        checkoutamount = resource.data.response.data.totalAmt
//                                        Log.d(ContentValues.TAG, "finalamount-->"+finalamount)
//                                        binding.llButtonsInCart.visibility = View.VISIBLE
//                                        binding.llpricedetails.visibility = View.VISIBLE
//                                        binding.llApplyCouponCode.visibility = View.VISIBLE
//                                        binding.rvCartList.visibility = View.VISIBLE
//                                        binding.tvEmptycart.visibility = View.GONE
//                                        cartListAdapter?.updateData(resource.data.response.data.cartdata)
//                                        binding.tvItemsNoInCart.text = resource.data.response.data.totQnty.toString() + " Items"
//                                        binding.tvFinalAmountInCart.text = "₹ " + resource.data.response.data.totalAmt
//                                        binding.tvItemPriceInCart.text = "₹ " + resource.data.response.data.totalAmt
//                                        binding.tvTotalAmountInCart.text = "₹ " + resource.data.response.data.totalAmt
//                                        shippingfromaddress = resource.data.response.data.sellerAddress
//                                        shippingcity = resource.data.response.data.parentCity
////                                    shippingstate = resource.data.response.data.shippingZip
//                                        totalamount = resource.data.response.data.totalAmt
//                                        totalqty = resource.data.response.data.totQnty.toString()
//                                        parentstate = resource.data.response.data.parentState
//                                        parentcity = resource.data.response.data.parentCity
//
//                                    }catch (e:Exception){
//                                        Log.d(ContentValues.TAG, "error-->"+e)
//                                    }
//
//
//
//                                }

                            }


                        }

                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()

                            //Log.d(ContentValues.TAG, "print-->" + resource.data?.status)
//                            if (it.message!!.contains("401", true)) {
//                                val builder = AlertDialog.Builder(mainActivity)
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
//                                Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()

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

    override fun addressListOnClick(
        position: Int,
        list: ArrayList<Addressdata>,
        view: View,
        type: String
    ) {
        if (type.equals("setdefault")) {
            primaryaddress(list[position].addressid)
        }else if(type.equals("edit")){

            val bundle = Bundle()
            bundle.putString("viewtype", "edit")
            bundle.putSerializable("data", list[position])
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.nav_addaddress, bundle)
            dialog!!.dismiss()

        }  else {

            val builder = androidx.appcompat.app.AlertDialog.Builder(mainActivity)
            builder.setTitle("Delete ${list[position].addrsType}")
            builder.setMessage("Are you sure you delete ${list[position].addrsType} from the list?")
            builder.setPositiveButton(
                "Yes"
            ) { dialog, which ->

                deleteAddress(
                    list[position].addressid,
                    list[position].locality,
                    list[position].city,
                    list[position].state,
                    list[position].pincode,
                    list[position].landmark,
                    list[position].contactName,
                    list[position].contactNo,
                    list[position].alternateNo,
                    list[position].addrsType,
                    list[position].default_type
                )
                dialog.dismiss()
            }
            builder.setNegativeButton(
                "Cancel"
            ) { dialog, which ->

                dialog.dismiss()
            }
            val alert = builder.create()
            alert.setOnShowListener { arg0 ->
                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.blue))
                alert.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.blue))
            }
            alert.show()

        }
    }


    private fun deleteAddress(
        addressid: String,
        locality: String,
        city: String,
        state: String,
        pincode: String,
        landmark: String,
        contactname: String,
        contactno: String,
        alternateno: String,
        addresstype: String,
        defaulttype: String
    ) {


        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.manageaddress(
                ManageAddressRequest(
                    addressid = addressid,
                    addrsType = addresstype,
                    alternateNo = alternateno,
                    city = city,
                    contactName = contactname,
                    contactNo = contactno,
                    landmark = landmark,
                    locality = locality,
                    pincode = pincode,
                    reqtype = "D",
                    state = state,
                    userid = Shared_Preferences.getUserId(),
                    token = Shared_Preferences.getToken().toString()
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {

                                Toast.makeText(
                                    mainActivity,
                                    resource.data?.response?.data?.get(0)!!.response,
                                    Toast.LENGTH_SHORT
                                ).show()

                                addresslist()


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


}