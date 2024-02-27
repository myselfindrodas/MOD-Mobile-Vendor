package com.example.modmobilesmartsale.ui.fragment

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.addtocartmodel.AddtoCartItem
import com.example.modmobilesmartsale.data.model.addtocartmodel.AddtoCartRequest
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
import com.example.modmobilesmartsale.databinding.FragmentProductListBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.adapter.AddressAdapter
import com.example.modmobilesmartsale.ui.adapter.AndroidPhoneAdapter
import com.example.modmobilesmartsale.ui.adapter.BrandListAdapter
import com.example.modmobilesmartsale.ui.adapter.BrandPhoneAdapter
import com.example.modmobilesmartsale.ui.adapter.DayDealsAdapter
import com.example.modmobilesmartsale.ui.adapter.IPhoneAdapter
import com.example.modmobilesmartsale.ui.adapter.VerticalBudgetListAdapter
import com.example.modmobilesmartsale.ui.adapter.VerticalLatestEditionAdapter
import com.example.modmobilesmartsale.utils.Constants.TAG
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import com.example.modmobilesmartsale.viewmodel.CommonViewModel2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random

class ProductListFragment : Fragment(), DayDealsAdapter.DayDealsListOnItemClickListener,
    IPhoneAdapter.IphoneListOnItemClickListener,
    AndroidPhoneAdapter.AndroidListOnItemClickListener,
    BrandPhoneAdapter.BrandListOnItemClickListener,
    BrandListAdapter.BrandListOnItemClickListener,
    VerticalBudgetListAdapter.VerticalBudgetListOnItemClickListener,
    VerticalLatestEditionAdapter.VerticalLatestEditionListOnItemClickListener,
    AddressAdapter.AddressOnItemClickListener {

    private lateinit var binding: FragmentProductListBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var viewModel2: CommonViewModel2

    private lateinit var mainActivity: MainActivity
    var productTitle = ""
    var brandname = ""
    var hotdeals = ""
    var dayDealsAdapter: DayDealsAdapter? = null
    var androidPhoneAdapter: AndroidPhoneAdapter? = null
    var brandPhoneAdapter: BrandPhoneAdapter? = null
    var iPhoneAdapter: IPhoneAdapter? = null
    var addressAdapter: AddressAdapter? = null

    var imeibox = ""
    var brandid = ""
    var pricerange = ""
    private lateinit var sortList: Array<String>
    var brandListAdapter: BrandListAdapter? = null
    var verticalBudgetListAdapter: VerticalBudgetListAdapter? = null
    var latestEditionAdapter: VerticalLatestEditionAdapter? = null
    var dialog: BottomSheetDialog? = null

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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)
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

        val intent = arguments
        if (intent != null && intent.containsKey("productTitle")) {
            productTitle = intent.getString("productTitle", "")
        }


        if (intent != null && intent.containsKey("brandname")) {
            brandname = intent.getString("brandname", "")
        }

        if (intent != null && intent.containsKey("imeibox")) {
            imeibox = intent.getString("imeibox", "")
        }


        if (intent != null && intent.containsKey("brandid")) {
            brandid = intent.getString("brandid", "")
        }



        if (intent != null && intent.containsKey("pricerange")) {
            pricerange = intent.getString("pricerange", "")
        }


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)

        binding.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        mainActivity.setBottomNavigationVisibility(false)

        binding.llFilterBy.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_filter)
        }

        if (brandname.isEmpty()) {
            if (productTitle.equals("android")) {
                binding.tvPhoneNameInProducts.text = "Android"
                binding.rvProductList.visibility = View.GONE
                binding.iPhonesList.visibility = View.GONE
                binding.rvBrandlist.visibility = View.GONE
                binding.androidPhonesList.visibility = View.VISIBLE
                binding.rvTopBrandList.visibility = View.GONE
                binding.rvBudgetList.visibility = View.GONE
                binding.rvLatestEditionsList.visibility = View.GONE


            } else if (productTitle.equals("ios")) {
                binding.tvPhoneNameInProducts.text = "IPhone"

                binding.rvProductList.visibility = View.GONE
                binding.iPhonesList.visibility = View.VISIBLE
                binding.androidPhonesList.visibility = View.GONE
                binding.rvBrandlist.visibility = View.GONE
                binding.rvTopBrandList.visibility = View.GONE
                binding.rvBudgetList.visibility = View.GONE
                binding.rvLatestEditionsList.visibility = View.GONE


            } else if (productTitle.equals("dealsoftheday")) {
                binding.tvPhoneNameInProducts.text = "Deals Of the day"

                binding.rvProductList.visibility = View.VISIBLE
                binding.iPhonesList.visibility = View.GONE
                binding.androidPhonesList.visibility = View.GONE
                binding.rvBrandlist.visibility = View.GONE
                binding.rvTopBrandList.visibility = View.GONE
                binding.rvBudgetList.visibility = View.GONE
                binding.rvLatestEditionsList.visibility = View.GONE


            } else if (productTitle.equals("hotdeals")) {

                binding.tvPhoneNameInProducts.text = "Hot Deals"
                binding.rvProductList.visibility = View.VISIBLE
                binding.iPhonesList.visibility = View.GONE
                binding.androidPhonesList.visibility = View.GONE
                binding.rvBrandlist.visibility = View.GONE
                hotdeals = "Y"
                binding.rvTopBrandList.visibility = View.GONE
                binding.rvBudgetList.visibility = View.GONE
                binding.rvLatestEditionsList.visibility = View.GONE


            } else if (productTitle.equals("category")) {

                binding.tvPhoneNameInProducts.text = "Category"
                binding.rvProductList.visibility = View.VISIBLE
                binding.iPhonesList.visibility = View.GONE
                binding.androidPhonesList.visibility = View.GONE
                binding.rvBrandlist.visibility = View.GONE
                binding.rvTopBrandList.visibility = View.GONE
                binding.rvBudgetList.visibility = View.GONE
                binding.rvLatestEditionsList.visibility = View.GONE


            } else if (productTitle.equals("brand")) {

                binding.tvPhoneNameInProducts.text = "Brands"
                binding.rvProductList.visibility = View.VISIBLE
                binding.iPhonesList.visibility = View.GONE
                binding.androidPhonesList.visibility = View.GONE
                binding.rvBrandlist.visibility = View.GONE
                binding.rvTopBrandList.visibility = View.GONE
                binding.rvBudgetList.visibility = View.GONE
                binding.rvLatestEditionsList.visibility = View.GONE


            } else if (productTitle.equals("TopBrands")) {

                binding.tvPhoneNameInProducts.text = "Top Brands"
                binding.rvProductList.visibility = View.GONE
                binding.iPhonesList.visibility = View.GONE
                binding.rvBrandlist.visibility = View.GONE
                binding.androidPhonesList.visibility = View.GONE
                binding.rvTopBrandList.visibility = View.VISIBLE
                binding.rvBudgetList.visibility = View.GONE
                binding.rvLatestEditionsList.visibility = View.GONE


            } else if (productTitle.equals("Budget")) {
                binding.tvPhoneNameInProducts.text = "Shop By Budget"
                binding.tvTopBar.text = "Shop by Budget"
                binding.rvProductList.visibility = View.GONE
                binding.iPhonesList.visibility = View.GONE
                binding.androidPhonesList.visibility = View.GONE
                binding.rvBrandlist.visibility = View.GONE
                binding.rvTopBrandList.visibility = View.GONE
                binding.rvBudgetList.visibility = View.VISIBLE
                binding.rvLatestEditionsList.visibility = View.GONE


            } else if (productTitle.equals("LatestEdition")) {
                binding.tvPhoneNameInProducts.text = "Latest Edition"

                binding.rvProductList.visibility = View.GONE
                binding.iPhonesList.visibility = View.GONE
                binding.androidPhonesList.visibility = View.GONE
                binding.rvBrandlist.visibility = View.GONE
                binding.rvTopBrandList.visibility = View.GONE
                binding.rvBudgetList.visibility = View.GONE
                binding.rvLatestEditionsList.visibility = View.VISIBLE


            } else if (productTitle.equals("range")) {

                binding.tvPhoneNameInProducts.text = "Price Range " + pricerange + " (₹)"
                binding.rvProductList.visibility = View.VISIBLE
                binding.iPhonesList.visibility = View.GONE
                binding.androidPhonesList.visibility = View.GONE
                binding.rvBrandlist.visibility = View.GONE
                binding.rvTopBrandList.visibility = View.GONE
                binding.rvBudgetList.visibility = View.GONE
                binding.rvLatestEditionsList.visibility = View.GONE


            }
        } else {

            binding.tvPhoneNameInProducts.text = brandname
            binding.rvProductList.visibility = View.GONE
            binding.iPhonesList.visibility = View.GONE
            binding.rvBrandlist.visibility = View.VISIBLE
            binding.rvTopBrandList.visibility = View.GONE
            binding.rvBudgetList.visibility = View.GONE
            binding.rvLatestEditionsList.visibility = View.GONE
        }

        sortList = arrayOf(
            "Sort By",
            "Recently Added",
            "Most Relevent",
            "Popular",
            "Price: High-Low",
            "Price: Low-High"
        )
        val sortAdapter = ArrayAdapter(mainActivity, R.layout.spinner_text, sortList)
        binding.sortSpinner.adapter = sortAdapter

        dayDealsAdapter = DayDealsAdapter(mainActivity, this@ProductListFragment)
        binding.rvProductList.layoutManager = GridLayoutManager(mainActivity, 2)
        binding.rvProductList.adapter = dayDealsAdapter


        androidPhoneAdapter = AndroidPhoneAdapter(mainActivity, this@ProductListFragment)
        binding.androidPhonesList.layoutManager = GridLayoutManager(mainActivity, 2)
        binding.androidPhonesList.adapter = androidPhoneAdapter


        iPhoneAdapter = IPhoneAdapter(mainActivity, this@ProductListFragment)
        binding.iPhonesList.layoutManager = GridLayoutManager(mainActivity, 2)
        binding.iPhonesList.adapter = iPhoneAdapter



        brandPhoneAdapter = BrandPhoneAdapter(mainActivity, this@ProductListFragment)
        binding.rvBrandlist.layoutManager = GridLayoutManager(mainActivity, 2)
        binding.rvBrandlist.adapter = brandPhoneAdapter

        brandListAdapter = BrandListAdapter(mainActivity, this@ProductListFragment)
        binding.rvTopBrandList.layoutManager = GridLayoutManager(mainActivity, 4)
        binding.rvTopBrandList.adapter = brandListAdapter

        verticalBudgetListAdapter =
            VerticalBudgetListAdapter(mainActivity, this@ProductListFragment)
        binding.rvBudgetList.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvBudgetList.adapter = verticalBudgetListAdapter

        latestEditionAdapter = VerticalLatestEditionAdapter(mainActivity, this@ProductListFragment)
        binding.rvLatestEditionsList.layoutManager =
            LinearLayoutManager(mainActivity)
        binding.rvLatestEditionsList.adapter = latestEditionAdapter

        getstocklist()
        DashboardList()

    }

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

                                    brandListAdapter?.updateData(resource.data?.response?.data?.topBrands!!)
                                    verticalBudgetListAdapter?.updateData(resource.data?.response?.data?.shopByBudgetImg!!)
                                    latestEditionAdapter?.updateData(resource.data?.response?.data?.latestEditionImg!!)

                                    if (binding.rvTopBrandList.isVisible){
                                        if (brandListAdapter?.itemCount == 0) {

                                            binding.noProducts.visibility = View.VISIBLE

                                        } else {

                                            binding.noProducts.visibility = View.GONE

                                        }
                                    }else if (binding.rvBudgetList.isVisible){
                                        if (verticalBudgetListAdapter?.itemCount == 0) {

                                            binding.noProducts.visibility = View.VISIBLE

                                        } else {

                                            binding.noProducts.visibility = View.GONE

                                        }
                                    }else if (binding.rvLatestEditionsList.isVisible){
                                        if (latestEditionAdapter?.itemCount == 0) {

                                            binding.noProducts.visibility = View.VISIBLE

                                        } else {

                                            binding.noProducts.visibility = View.GONE

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

        try {

            if (Utilities.isNetworkAvailable(mainActivity)) {

                viewModel.getstock(
                    StockRequest(
                        ascCode = "MMWHDL002",
                        brandId = brandid,
                        hotDeal = hotdeals,
                        search = "",
                        imei = "",
                        modelCode = "",
                        color = "",
                        memory = "",
                        modelName = "",
                        phoneVersion = "",
                        price = pricerange,
                        stockType = imeibox,
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
                                    val brandlist = ArrayList<Data>()
                                    for (i in 0 until resource.data?.response?.data!!.size) {

                                        if (resource.data.response.data[i].dealOfDays.equals("Y")) {
                                            dealsofthedaylist.add(resource.data.response.data[i])
                                            dayDealsAdapter?.updateData(dealsofthedaylist)

                                        }
                                        if (resource.data.response.data[i].phoneVersion.equals("Android")) {
                                            androidlist.add(resource.data.response.data[i])
                                            androidPhoneAdapter?.updateData(androidlist)

                                        }

                                        if (resource.data.response.data[i].phoneVersion.equals("Iphone")) {
                                            iphonelist.add(resource.data.response.data[i])
                                            iPhoneAdapter?.updateData(iphonelist)

                                        }
                                        if (resource.data.response.data[i].brand.equals(brandname)) {
                                            brandlist.add(resource.data.response.data[i])
                                            brandPhoneAdapter?.updateData(brandlist)

                                        }


                                    }

                                    if (binding.rvProductList.isVisible){
                                        if (dayDealsAdapter?.itemCount == 0) {

                                            binding.noProducts.visibility = View.VISIBLE

                                        } else {

                                            binding.noProducts.visibility = View.GONE

                                        }
                                    }else if (binding.androidPhonesList.isVisible){
                                        if (androidPhoneAdapter?.itemCount == 0) {

                                            binding.noProducts.visibility = View.VISIBLE

                                        } else {

                                            binding.noProducts.visibility = View.GONE

                                        }
                                    }else if (binding.iPhonesList.isVisible){
                                        if (iPhoneAdapter?.itemCount == 0) {

                                            binding.noProducts.visibility = View.VISIBLE

                                        } else {

                                            binding.noProducts.visibility = View.GONE

                                        }
                                    }else if (binding.rvBrandlist.isVisible){
                                        if (brandPhoneAdapter?.itemCount == 0) {

                                            binding.noProducts.visibility = View.VISIBLE

                                        } else {

                                            binding.noProducts.visibility = View.GONE

                                        }
                                    }


                                    if (hotdeals.isNotEmpty()) {
                                        dayDealsAdapter?.updateData(resource.data.response.data)

                                        if (dayDealsAdapter?.itemCount == 0) {

                                            binding.noProducts.visibility = View.VISIBLE

                                        } else {

                                            binding.noProducts.visibility = View.GONE

                                        }
                                    }

                                    if (brandid.isNotEmpty()) {
                                        dayDealsAdapter?.updateData(resource.data.response.data)

                                        if (dayDealsAdapter?.itemCount == 0) {

                                            binding.noProducts.visibility = View.VISIBLE

                                        } else {

                                            binding.noProducts.visibility = View.GONE

                                        }
                                    }

                                    if (imeibox.isNotEmpty()) {
                                        dayDealsAdapter?.updateData(resource.data.response.data)

                                        if (dayDealsAdapter?.itemCount == 0) {

                                            binding.noProducts.visibility = View.VISIBLE

                                        } else {

                                            binding.noProducts.visibility = View.GONE

                                        }

                                    }

                                    if (pricerange.isNotEmpty()) {
                                        dayDealsAdapter?.updateData(resource.data.response.data)

                                        if (dayDealsAdapter?.itemCount == 0) {

                                            binding.noProducts.visibility = View.VISIBLE

                                        } else {

                                            binding.noProducts.visibility = View.GONE

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

        } catch (e: Exception) {

            Log.d(TAG, "error-->" + e)
        }


    }


    override fun dayDealsOnClick(
        position: Int,
        list: ArrayList<Data>,
        view: View,
        type: String,
        phoneName: String
    ) {

        PHONENAME = phoneName
        modelcode = list[position].modelCode
        price = list[position].custPrice
        productid = list[position].imei1
        purchaseprice = list[position].purchasePrice
        stocktype = list[position].stockType

        if (type == "Abuy") {

            dialog = BottomSheetDialog(mainActivity,R.style.SheetDialog)
            dialog!!.setContentView(R.layout.bottom_sheet_buy_now)
            val addressListItems = dialog!!.findViewById<RecyclerView>(R.id.addressListItems)
            val btnAddNewAddress = dialog!!.findViewById<Button>(R.id.btnAddAddress)
            val btnContinue = dialog!!.findViewById<Button>(R.id.btnBuy)

            addressAdapter = AddressAdapter(mainActivity, this@ProductListFragment)
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

        } else if (type == "Acart") {

            addCart()

        } else if (type == "Aclick") {

            val bundle = Bundle()
            bundle.putSerializable("itemdetails", list[position])
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.nav_product_details, bundle)

        }


    }

    override fun AndroidOnClick(
        position: Int,
        list: ArrayList<Data>,
        view: View,
        type: String,
        phoneName: String
    ) {

        PHONENAME = phoneName
        modelcode = list[position].modelCode
        price = list[position].custPrice
        productid = list[position].imei1
        purchaseprice = list[position].purchasePrice
        stocktype = list[position].stockType

        if (type == "Abuy") {

            dialog = BottomSheetDialog(mainActivity,R.style.SheetDialog)
            dialog!!.setContentView(R.layout.bottom_sheet_buy_now)
            val addressListItems = dialog!!.findViewById<RecyclerView>(R.id.addressListItems)
            val btnAddNewAddress = dialog!!.findViewById<Button>(R.id.btnAddAddress)
            val btnContinue = dialog!!.findViewById<Button>(R.id.btnBuy)

            addressAdapter = AddressAdapter(mainActivity, this@ProductListFragment)
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

        } else if (type == "Acart") {

            addCart()

        } else if (type == "Aclick") {

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

                                if (resource.data?.get(0)!!.status.equals("true")) {


                                    AestheticDialog.Builder(
                                        mainActivity,
                                        DialogStyle.FLAT,
                                        DialogType.SUCCESS
                                    )
                                        .setTitle("Item Added To Cart")
                                        .setMessage(resource.data.get(0).response)
                                        .setCancelable(false)
                                        .setDarkMode(false)
                                        .setGravity(Gravity.CENTER)
                                        .setAnimation(DialogAnimation.SHRINK)
                                        .setOnClickListener(object : OnDialogClickListener {
                                            override fun onClick(dialog: AestheticDialog.Builder) {
                                                dialog.dismiss()
                                                val navController =
                                                    Navigation.findNavController(binding.root)
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
                                taxablevalue =
                                    resource.data.response.data.cartdata[0].taxableValue.toString()
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
                                                marginprice = (price.toDouble()
                                                    .minus(purchaseprice.toDouble())).toString()
                                                sgstcost =
                                                    ((sgstPer.toDouble() / 100.00) * marginprice.toDouble()).toString()
                                                cgstcost =
                                                    ((cgstPer.toDouble() / 100.00) * marginprice.toDouble()).toString()

                                            } else {
                                                sgstcost =
                                                    ((sgstPer.toDouble() / 100.00) * price.toDouble()).toString()
                                                cgstcost =
                                                    ((cgstPer.toDouble() / 100.00) * price.toDouble()).toString()

                                            }

                                            totalcgst += cgstcost.toDouble().toInt()
                                            totalsgst += sgstcost.toDouble().toInt()

                                            Log.d(ContentValues.TAG, "totalsgst-->" + totalsgst)
                                            Log.d(ContentValues.TAG, "totalcgst-->" + totalcgst)

                                        } else {
                                            if (margintype.equals("margin")) {
                                                marginprice = (price.toDouble()
                                                    .minus(purchaseprice.toDouble())).toString()
                                                igstcost =
                                                    ((igstPer.toDouble() / 100.00) * marginprice.toDouble()).toString()
                                            } else {
                                                igstcost =
                                                    ((igstPer.toDouble() / 100.00) * price.toDouble()).toString()
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

                                    } catch (e: Exception) {
                                        Log.d(ContentValues.TAG, "exception-->" + e)
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
        totaligst: String
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

                                    AestheticDialog.Builder(
                                        mainActivity,
                                        DialogStyle.FLAT,
                                        DialogType.SUCCESS
                                    )
                                        .setTitle("Item Order Successfully")
                                        .setMessage("Invoice Created Successfully " + resource.data?.crmInvoiceNo)
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

    private fun ordersuccessmsg() {


        if (Utilities.isNetworkAvailable(mainActivity)) {

            val recipientslist = ArrayList<Recipient>()
            recipientslist.add(
                Recipient(
                    "91" + Shared_Preferences.getPhoneNo(),
                    "VALUE1",
                    "VALUE2"
                )
            )
            viewModel2.successmessage(
                RegistrationSuccessRequest(
                    recipientslist,
                    shortUrl = "1",
                    templateId = "6540bb11d6fc0551f40a0a26"
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.type.equals("success")) {

                                Toast.makeText(
                                    mainActivity,
                                    "Order Placed Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
//                                val navController = Navigation.findNavController(binding.root)
//                                navController.navigate(R.id.nav_home)
                                val intent = Intent(context,MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)

                            } else {

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

            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()

        }
    }

    fun generateUniqueNumber(): Int {
        val currentTimeMillis = System.currentTimeMillis()
        val random = Random(currentTimeMillis)
        return random.nextInt()
    }

    override fun IphoneOnClick(
        position: Int,
        list: ArrayList<Data>,
        view: View,
        type: String,
        phoneName: String
    ) {

        PHONENAME = phoneName
        modelcode = list[position].modelCode
        price = list[position].custPrice
        productid = list[position].imei1
        purchaseprice = list[position].purchasePrice
        stocktype = list[position].stockType

        if (type == "buy") {

            dialog = BottomSheetDialog(mainActivity,R.style.SheetDialog)
            dialog!!.setContentView(R.layout.bottom_sheet_buy_now)
            val addressListItems = dialog!!.findViewById<RecyclerView>(R.id.addressListItems)
            val btnAddNewAddress = dialog!!.findViewById<Button>(R.id.btnAddAddress)
            val btnContinue = dialog!!.findViewById<Button>(R.id.btnBuy)

            addressAdapter = AddressAdapter(mainActivity, this@ProductListFragment)
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

        } else if (type == "cart") {

            addCart()
        } else {
            val bundle = Bundle()
            bundle.putSerializable("itemdetails", list[position])
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.nav_product_details, bundle)
        }

    }

    override fun BrandOnClick(
        position: Int,
        list: ArrayList<Data>,
        view: View,
        type: String,
        phoneName: String
    ) {

        PHONENAME = phoneName
        modelcode = list[position].modelCode
        price = list[position].custPrice
        productid = list[position].imei1
        purchaseprice = list[position].purchasePrice
        stocktype = list[position].stockType

        if (type == "buy") {

            dialog = BottomSheetDialog(mainActivity,R.style.SheetDialog)
            dialog!!.setContentView(R.layout.bottom_sheet_buy_now)
            val addressListItems = dialog!!.findViewById<RecyclerView>(R.id.addressListItems)
            val btnAddNewAddress = dialog!!.findViewById<Button>(R.id.btnAddAddress)
            val btnContinue = dialog!!.findViewById<Button>(R.id.btnBuy)

            addressAdapter = AddressAdapter(mainActivity, this@ProductListFragment)
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

        } else if (type == "cart") {

            addCart()
        } else {
            val bundle = Bundle()
            bundle.putSerializable("itemdetails", list[position])
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.nav_product_details, bundle)
        }

    }

    override fun brandListOnClick(position: Int, list: ArrayList<TopBrand>, view: View) {
        val bundle = Bundle()
        bundle.putString("brandname", list[position].name)
        val navController = Navigation.findNavController(binding.root)
        navController.navigate(R.id.nav_product_list, bundle)
    }

    override fun verticalBudgetListOnClick(
        position: Int,
        list: ArrayList<ShopByBudgetImg>,
        view: View
    ) {

        val bundle = Bundle()
        bundle.putString("productTitle", "hotdeals")
        val navController = Navigation.findNavController(binding.root)
        navController.navigate(R.id.nav_product_list, bundle)

    }

    override fun latesteditionListOnClick(
        position: Int,
        mVerticalLatesteditionList: ArrayList<LatestEditionImg>,
        view: View
    ) {
        val bundle = Bundle()
        bundle.putString("pricerange", mVerticalLatesteditionList[position].price_range)
        bundle.putString("productTitle", "range")
        val navController = Navigation.findNavController(binding.root)
        navController.navigate(R.id.nav_product_list, bundle)
    }



    private fun primaryaddress(addressid: String) {

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
                                Toast.makeText(
                                    mainActivity,
                                    resource.data?.response?.data?.get(0)!!.response,
                                    Toast.LENGTH_SHORT
                                ).show()
//                                val navController = Navigation.findNavController(binding.root)
//                                navController.navigate(R.id.nav_home)

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

    private fun addresslist() {
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
                                    if (resource.data.response.data.addressdata[i].default_type.equals(
                                            "Y"
                                        )
                                    ) {
                                        Shared_Preferences.setShippingAddress(
                                            resource.data.response.data.addressdata[i].locality + "," +
                                                    resource.data.response.data.addressdata[i].city + "," + resource.data.response.data.addressdata[i].state
                                        )
                                        Shared_Preferences.setShippingState(resource.data.response.data.addressdata[i].state)
                                        Shared_Preferences.setShippingCity(resource.data.response.data.addressdata[i].city)
                                        Shared_Preferences.setShippingZip(resource.data.response.data.addressdata[i].pincode)

//                                        binding.tvSelectedLocation.text = resource.data.response.data.addressdata[i].locality+","+
//                                                resource.data.response.data.addressdata[i].city+","+ resource.data.response.data.addressdata[i].state

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