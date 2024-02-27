package com.example.modmobilesmartsale.ui.fragment

import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilecustomer.ui.adapter.CartListAdapter
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.addtocartmodel.AddtoCartItem
import com.example.modmobilesmartsale.data.model.addtocartmodel.AddtoCartRequest
import com.example.modmobilesmartsale.data.model.defaultaddressmodel.DefaultAddressRequest
import com.example.modmobilesmartsale.data.model.favouritemodel.FavAddRemoveRequest
import com.example.modmobilesmartsale.data.model.invoicecreatemodel.BillJSON
import com.example.modmobilesmartsale.data.model.invoicecreatemodel.InvoiceDetail
import com.example.modmobilesmartsale.data.model.invoicecreatemodel.InvoicecreateRequest
import com.example.modmobilesmartsale.data.model.manageaddressmodel.ManageAddressRequest
import com.example.modmobilesmartsale.data.model.pocreatemodel.OrderDetail
import com.example.modmobilesmartsale.data.model.pocreatemodel.POJSON
import com.example.modmobilesmartsale.data.model.pocreatemodel.POcreateRequest
import com.example.modmobilesmartsale.data.model.registrationsuccessmodel.Recipient
import com.example.modmobilesmartsale.data.model.registrationsuccessmodel.RegistrationSuccessRequest
import com.example.modmobilesmartsale.data.model.viewaddressmodel.Addressdata
import com.example.modmobilesmartsale.data.model.viewaddressmodel.ViewAddressRequest
import com.example.modmobilesmartsale.data.model.viewcartmodel.Cartdata
import com.example.modmobilesmartsale.data.model.viewcartmodel.ViewCartRequest
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory2
import com.example.modmobilesmartsale.databinding.FragmentCartBinding
import com.example.modmobilesmartsale.data.model.getfavouritemodel.Data
import com.example.modmobilesmartsale.data.model.getfavouritemodel.FavListRequest
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.MainActivity.Companion.payment
import com.example.modmobilesmartsale.ui.adapter.AddressAdapter
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

class CartFragment : Fragment(), CartListAdapter.CartItemClickListener,
    AddressAdapter.AddressOnItemClickListener {

    private lateinit var binding: FragmentCartBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: CommonViewModel
    private lateinit var viewModel2: CommonViewModel2
    var cartListAdapter: CartListAdapter? = null
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val pERMISSION_ID = 42
    var addressAdapter: AddressAdapter? = null
    var shippingfromaddress = ""
    var shippingcity = ""
    var shippingfromstate = ""
    var shippingzip = ""
    var totalamount = ""
    var totalqty = ""
    var parentstate = ""
    var parentaddress = ""
    var parentzip = ""
    var parentcity = ""
    var totaldiscountamount = "0"
    var orderdetailsItem = ArrayList<OrderDetail>()
    var invoicedetailsItem = ArrayList<InvoiceDetail>()
    var discountprice = 0
    var finalamount = ""
    var checkoutamount = ""
    var finaldiscount = "0"
    var dialog:BottomSheetDialog?=null
    var dialog2:BottomSheetDialog?=null
    var paymentmode = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
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

        mainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        binding.topBarCart.tvTopBar.setText("Cart")

        binding.topBarCart.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        mainActivity.setBottomNavigationVisibility(false)

        if (!Places.isInitialized()) {
            Places.initialize(mainActivity, getString(R.string.google_maps_key))
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mainActivity)

//        getCurrentLocation()

        binding.btnCartLocation.setOnClickListener {
            showBottomDialog()
        }

        cartListAdapter = CartListAdapter(mainActivity, this@CartFragment)
//        val cart_list = ArrayList<CartListData>()
//        for (i in 1..4){
//            cart_list.add(
//                CartListData(R.drawable.phone_image, "20% Off", "Apple I Phone 13 Pro",
//                "(20 Reviews)", "15 In Stock", "GOOD", "4GB / 256GB",
//                "₹ 17120", "₹ 22000", "save ₹ 3120")
//            )
//        }
        binding.rvCartList.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvCartList.adapter = cartListAdapter
//        viewCart()
        addresslist()

//        cartListAdapter!!.updateData(cart_list)




        binding.btnPlaceOrder.setOnClickListener {

//            val poItems = ArrayList<POJSON>()
            val cartList = cartListAdapter?.getcartItems()!!

            println("Cart List" + cartList)

//            val orderdetailsItem = ArrayList<OrderDetail>()
            for (i in 0 until cartList.size) {
                orderdetailsItem.add(
                    OrderDetail(
                        cartList[i].itemCategory,
                        cartList[i].productName,
                        cartList[i].price,
                        cartList[i].productQty,
                        cartList[i].modelCode
                    )
                )
            }


            if (orderdetailsItem.isEmpty()) {
                Toast.makeText(mainActivity, "Select item first", Toast.LENGTH_SHORT).show()
            } else {
                if (Shared_Preferences.getShippingAddress()!!.isEmpty()){
                    Toast.makeText(mainActivity, "Select Shipping Address First", Toast.LENGTH_SHORT).show()
                    val navController = Navigation.findNavController(binding.root)
                    navController.navigate(R.id.nav_saved_address)
                }else{

                    dialog2 = BottomSheetDialog(mainActivity,R.style.SheetDialog)
                    dialog2!!.setContentView(R.layout.bottom_sheet_payment)
                    val btnCOD = dialog2!!.findViewById<AppCompatButton>(R.id.btnCOD)
                    val btnOnline = dialog2!!.findViewById<AppCompatButton>(R.id.btnOnline)
                    dialog2!!.show()
                    btnCOD!!.setOnClickListener {

                        dialog2!!.dismiss()
                        paymentmode = "cod"
                        pocreate()

                    }


                    btnOnline!!.setOnClickListener {

                        dialog2!!.dismiss()
                        paymentmode = "wallet"
                        mainActivity.startPayment(checkoutamount)

                    }

//                    mainActivity.startPayment(checkoutamount)
//                    pocreate()

                }
//                Log.d(ContentValues.TAG, "polist-->" + poItems.toString())
            }


//            val navController = Navigation.findNavController(it)
//            navController.navigate(R.id.nav_place_order)
        }



        setFragmentResultListener("11") { requestKey, bundle ->

            if (requestKey == "11" && bundle.containsKey("discountprice")) {
                discountprice = bundle.getString("discountprice")!!.toDouble().toInt()
                binding.etCouponcode.setText(bundle.getString("coupontitle").toString())
//                viewCart()
                favouriteList()

            }
        }


        binding.etCouponcode.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_couponlist)
        }



        binding.btnApplyCouponCode.setOnClickListener {
            if (discountprice > 0) {

                try {
                    binding.tvDiscountInCart.text = "₹ " + discountprice
                    binding.tvFinalAmountInCart.text = "₹ " + (finalamount.toFloat().minus(discountprice.toFloat()))
                    binding.tvTotalAmountInCart.text = "₹ " + (finalamount.toFloat().minus(discountprice.toFloat()))
                    binding.tvDiscountInCart.text = "₹ " + (discountprice)
                    binding.tvSavingsInCart.text = "(save ₹ "+discountprice+")"
                    checkoutamount = (finalamount.toFloat().minus(discountprice.toFloat())).toString()
                    finaldiscount = discountprice.toString()
                    binding.btnApplyCouponCode.text = "APPLIED"
                    binding.btnRemovecoupon.visibility = View.VISIBLE

                    Log.d(TAG, "discount -->" + discountprice)

                } catch (e: Exception) { // handle your exception
                    Log.d(TAG, "discount -->" + e)
                }



            } else {

                Toast.makeText(mainActivity, "Enter Valid Coupon For Applied", Toast.LENGTH_SHORT)
                    .show()

            }
        }


        binding.btnRemovecoupon.setOnClickListener {
            if (binding.btnApplyCouponCode.text.isNotEmpty()) {

                try {
                    var removediscoutprice = "0"
                    discountprice = 0
                    binding.tvDiscountInCart.text = "₹ " + removediscoutprice
                    binding.tvFinalAmountInCart.text = "₹ " + (finalamount.toFloat().minus(removediscoutprice.toFloat()))
                    binding.tvTotalAmountInCart.text = "₹ " + (finalamount.toFloat().minus(removediscoutprice.toFloat()))
                    binding.tvDiscountInCart.text = "₹ " + (removediscoutprice)
                    binding.tvSavingsInCart.text = "(save ₹ "+removediscoutprice+")"
                    checkoutamount = (finalamount.toFloat().minus(removediscoutprice.toFloat())).toString()
                    finaldiscount = removediscoutprice.toString()
                    binding.btnApplyCouponCode.text = "APPLY"
                    binding.etCouponcode.setText("")
                    binding.btnRemovecoupon.visibility = View.GONE

                    Log.d(TAG, "discount -->" + removediscoutprice)

                } catch (e: Exception) { // handle your exception
                    Log.d(TAG, "discount -->" + e)
                }



            } else {

                Toast.makeText(mainActivity, "Enter Valid Coupon For Applied", Toast.LENGTH_SHORT)
                    .show()

            }
        }

        favouriteList()

    }



    override fun onResume() {
        super.onResume()

        if (payment.equals("success")){

            pocreate()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        payment = ""

    }

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
//
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

    private fun showBottomDialog() {
        dialog = BottomSheetDialog(mainActivity, R.style.SheetDialog)
        dialog!!.setContentView(R.layout.bottom_sheet_address)
        val addressListItems = dialog!!.findViewById<RecyclerView>(R.id.addressListItems)
        val btnAddNewAddress = dialog!!.findViewById<Button>(R.id.btnAddNewAddress)
        addressAdapter = AddressAdapter(mainActivity, this@CartFragment)
//        val address_list = ArrayList<AddressListData>()
//        for (i in 1..4){
//            address_list.add(AddressListData(R.drawable.icon_address_home,"Home", "Kolkata, West Bengal, 7000156"))
//        }
        addressListItems!!.layoutManager = LinearLayoutManager(mainActivity)
        addressListItems.adapter = addressAdapter
        addresslist()
//        addressAdapter!!.updateData(address_list)
        dialog!!.show()
        btnAddNewAddress!!.setOnClickListener {

            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.nav_addaddress)
            dialog!!.dismiss()
//            openSearchBar()
//            dialog.dismiss()
        }
    }


    private fun viewCart(list: List<Any>) {

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

                                if (resource.data?.response?.data?.cartdata!!.isEmpty()) {

                                    binding.llButtonsInCart.visibility = View.GONE
                                    binding.llpricedetails.visibility = View.GONE
                                    binding.llApplyCouponCode.visibility = View.GONE
                                    binding.rvCartList.visibility = View.GONE
                                    binding.tvEmptycart.visibility = View.VISIBLE

                                } else {

                                    finalamount = resource.data.response.data.totalAmt
                                    checkoutamount = resource.data.response.data.totalAmt
                                    Log.d(TAG, "finalamount-->"+finalamount)
                                    binding.llButtonsInCart.visibility = View.VISIBLE
                                    binding.llpricedetails.visibility = View.VISIBLE
                                    binding.llApplyCouponCode.visibility = View.VISIBLE
                                    binding.rvCartList.visibility = View.VISIBLE
                                    binding.tvEmptycart.visibility = View.GONE
                                    binding.btnRemovecoupon.visibility = View.GONE

//                                    cartListAdapter?.updateData(resource.data.response.data.cartdata)
                                    cartListAdapter?.updateData(list, resource.data.response.data.cartdata)

                                    binding.tvItemsNoInCart.text = resource.data.response.data.totQnty.toString() + " Items"
                                    binding.tvFinalAmountInCart.text = "₹ " + resource.data.response.data.totalAmt
                                    binding.tvItemPriceInCart.text = "₹ " + resource.data.response.data.totalAmt
                                    binding.tvTotalAmountInCart.text = "₹ " + resource.data.response.data.totalAmt
                                    shippingfromaddress = resource.data.response.data.sellerAddress
                                    shippingcity = resource.data.response.data.parentCity
//                                    shippingstate = resource.data.response.data.shippingZip
                                    totalamount = resource.data.response.data.totalAmt
                                    totalqty = resource.data.response.data.totQnty.toString()
                                    parentstate = resource.data.response.data.parentState
                                    parentcity = resource.data.response.data.parentCity


                                }

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


    override fun onPause() {
        super.onPause()

        mainActivity.homeBottomIcon()
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

    override fun addressListOnClick(
        position: Int,
        list: ArrayList<Addressdata>,
        view: View,
        type: String
    ) {
        if (type.equals("setdefault")) {
//            dialog!!.dismiss()
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

    override fun cartsItemOnClick(
        position: Int,
        list: ArrayList<Cartdata>,
        view: View,
        type: String,
        favlist: ArrayList<Data>
    ) {

        if (type.equals("removeitem")) {

            val cartItem = ArrayList<AddtoCartItem>()
            cartItem.add(
                AddtoCartItem(
                    "",
                    list[position].modelCode,
                    "",
                    "",
                    list[position].price,
                    list[position].productId,
                    list[position].purchasePrice,
                    "1",
                    "R",
                    "MMWHDL002",
                    Shared_Preferences.getUserId(),
                    list[position].itemCategory,
                    Shared_Preferences.getUserId()


                )
            )

            removeTocart(favlist, cartItem)
        } else {

            addtofav(list[position].productId)
        }


    }

    private fun removeTocart(favlist: ArrayList<Data>, cartItem: ArrayList<AddtoCartItem>) {

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

                                    Toast.makeText(
                                        mainActivity,
                                        resource.data.get(0).response,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    viewCart(favlist)

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

    private fun addtofav(productid: String) {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.addfav(
                FavAddRemoveRequest(
                    userid = Shared_Preferences.getUserId(),
                    productid = productid,
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

                                    val builder = AlertDialog.Builder(mainActivity)
                                    builder.setMessage(resource.data?.response?.data!![0].response)
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

    private fun favouriteList(){

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.getfavouriteitems(FavListRequest(userid = Shared_Preferences.getUserId(), token = Shared_Preferences.getToken().toString()))

                .observe(mainActivity) {

                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {

                                mainActivity.hideProgressDialog()

                                if (resource.data?.response?.status.equals("true")) {

                                    viewCart(resource.data!!.response.data)

                                }else{
                                    viewCart(listOf())
                                }

                            }

                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                viewCart(listOf())
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

                                Toast.makeText(mainActivity, "Address is Selected.", Toast.LENGTH_SHORT).show()
                                addresslist()
//                                val navController = Navigation.findNavController(binding.root)
//                                navController.navigate(R.id.nav_cart)
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
                        paymentmode,
                        paymentmode,
                        Shared_Preferences.getShippingAddress().toString(),
                        Shared_Preferences.getShippingCity().toString(),
                        Shared_Preferences.getShippingState().toString(),
                        Shared_Preferences.getShippingZip().toString(),
                        checkoutamount,
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

                                    try {

                                        val cartList = cartListAdapter?.getcartItems()!!
                                        var sgstcost = ""
                                        var cgstcost = ""
                                        var igstcost = ""
                                        var marginprice = ""
                                        var totalsgst = 0
                                        var totalcgst = 0
                                        var totaligst = 0
                                        var totaltaxableamount = 0
                                        var basicamount = 0
//                                        var totalamount = 0

                                        for (i in 0 until cartList.size) {
                                            if (parentstate.equals("Delhi")) {
                                                if (cartList[i].tax_type.equals("margin")) {
                                                    if (finaldiscount.toInt()>0){
                                                        marginprice = (cartList[i].price.toDouble().minus(cartList[i].purchasePrice.toDouble())).minus(finaldiscount.toDouble()).toString()
                                                        basicamount = ((marginprice.toDouble())/((100.00+cartList[i].sgstPer.toDouble()+cartList[i].cgstPer.toDouble())/100)).toInt()

                                                    }else{
                                                        marginprice = (cartList[i].price.toDouble().minus(cartList[i].purchasePrice.toDouble())).toString()
                                                        basicamount = ((marginprice.toDouble())/((100.00+cartList[i].sgstPer.toDouble()+cartList[i].cgstPer.toDouble())/100)).toInt()

                                                    }

                                                    sgstcost = ((cartList[i].sgstPer.toDouble() / 100.00) * basicamount).toString()
                                                    cgstcost = ((cartList[i].cgstPer.toDouble() / 100.00) * basicamount).toString()

                                                } else {

                                                    if (finaldiscount.toInt()>0){

                                                        sgstcost = ((cartList[i].sgstPer.toDouble() / 100.00) * cartList[i].price.toDouble().minus(finaldiscount.toDouble())).toString()
                                                        cgstcost = ((cartList[i].cgstPer.toDouble() / 100.00) * cartList[i].price.toDouble().minus(finaldiscount.toDouble())).toString()
                                                    }else{
                                                        sgstcost = ((cartList[i].sgstPer.toDouble() / 100.00) * cartList[i].price.toDouble()).toString()
                                                        cgstcost = ((cartList[i].cgstPer.toDouble() / 100.00) * cartList[i].price.toDouble()).toString()
                                                    }



                                                }

                                                totalcgst += cgstcost.toDouble().toInt()
                                                totalsgst += sgstcost.toDouble().toInt()


                                                Log.d(TAG, "totalsgst-->" + totalsgst)
                                                Log.d(TAG, "basicamount-->" + basicamount)
                                                Log.d(TAG, "totalcgst-->" + totalcgst)

                                            } else {
                                                if (cartList[i].tax_type.equals("margin")) {
                                                    if (finaldiscount.toInt()>0){
                                                        marginprice = (cartList[i].price.toDouble().minus(cartList[i].purchasePrice.toDouble())).minus(finaldiscount.toDouble()).toString()
                                                        basicamount = ((marginprice.toDouble())/((100.00+cartList[i].igstPer.toDouble())/100)).toInt()

                                                    }else{
                                                        marginprice = (cartList[i].price.toDouble().minus(cartList[i].purchasePrice.toDouble())).toString()
                                                        basicamount = ((marginprice.toDouble())/((100.00+cartList[i].igstPer.toDouble())/100)).toInt()

                                                    }
                                                    igstcost = ((cartList[i].igstPer.toDouble() / 100.00) * basicamount).toString()
                                                } else {

                                                    if (finaldiscount.toInt()>0){

                                                        igstcost = ((cartList[i].igstPer.toDouble() / 100.00) * cartList[i].price.toDouble().minus(finaldiscount.toDouble())).toString()

                                                    }else{
                                                        igstcost = ((cartList[i].igstPer.toDouble() / 100.00) * cartList[i].price.toDouble()).toString()

                                                    }
                                                }

                                                totaligst += igstcost.toDouble().toInt()

                                                Log.d(TAG, "totaligst-->" + totaligst)
                                                Log.d(TAG, "basicamount-->" + basicamount)

                                            }

                                            invoicedetailsItem.add(
                                                InvoiceDetail(
                                                    cgstcost,
                                                    cartList[i].cgstPer.toString(),
                                                    finaldiscount,
                                                    cartList[i].hsnCode,
                                                    igstcost,
                                                    cartList[i].igstPer.toString(),
                                                    cartList[i].productId,
                                                    cartList[i].productId,
                                                    cartList[i].itemCategory,
                                                    cartList[i].itemTotal.toDouble().minus(finaldiscount.toDouble()).toString(),
                                                    cartList[i].modelCode,
                                                    cartList[i].productName,
                                                    cartList[i].price,
                                                    cartList[i].purchasePrice,
                                                    cartList[i].productQty,
                                                    sgstcost,
                                                    cartList[i].sgstPer.toString(),
                                                    basicamount.toDouble(),
                                                    cartList[i].type
                                                )
                                            )
                                            if (parentstate.equals("Delhi")) {
//                                                totaltaxableamount += cartList[i].taxableValue.toDouble().toInt()
//                                                totaltaxableamount += (cartList[i].taxableValue.toDouble()-((cartList[i].cgstPer.toDouble()+cartList[i].sgstPer.toDouble())/100.00) * cartList[i].taxableValue.toDouble()).toInt()
//                                                basicamount = (cartList[i].taxableValue.toDouble()-((cartList[i].cgstPer.toDouble()+cartList[i].sgstPer.toDouble())/100.00) * cartList[i].taxableValue.toDouble()).toInt()
                                                if (finaldiscount.toInt()>0){
                                                    totaltaxableamount += ((cartList[i].price.toDouble().minus(cartList[i].purchasePrice.toDouble()).minus(finaldiscount.toDouble()))/((100.00+cartList[i].sgstPer.toDouble()+cartList[i].cgstPer.toDouble())/100)).toInt()

                                                }else{
                                                    totaltaxableamount += ((cartList[i].price.toDouble().minus(cartList[i].purchasePrice.toDouble()))/((100.00+cartList[i].sgstPer.toDouble()+cartList[i].cgstPer.toDouble())/100)).toInt()
                                                }


                                            }else{

                                                if (finaldiscount.toInt()>0){
                                                    totaltaxableamount += ((cartList[i].price.toDouble().minus(cartList[i].purchasePrice.toDouble()).minus(finaldiscount.toDouble()))/((100.00+cartList[i].igstPer.toDouble())/100)).toInt()

                                                }else{
                                                    totaltaxableamount += ((cartList[i].price.toDouble().minus(cartList[i].purchasePrice.toDouble()))/((100.00+cartList[i].igstPer.toDouble())/100)).toInt()

                                                }


//                                                totaltaxableamount += (cartList[i].taxableValue.toDouble()-(cartList[i].igstPer.toDouble()/100.00) * cartList[i].taxableValue.toDouble()).toInt()
                                            }
//                                            totalamount += cartList[i].amount.toDouble().toInt()

                                        }


                                        invoicecreate(
                                            resource.data?.appOrderNo.toString(),
                                            resource.data?.poNumber.toString(),
                                            orderDate,
                                            totalqty,
                                            finaldiscount,
                                            totaltaxableamount.toString(),
                                            totalcgst.toString(),
                                            totalsgst.toString(),
                                            checkoutamount,
                                            totaligst.toString()

                                        )

                                    }catch (e:Exception){
                                        Log.d(TAG, "exception-->"+e)
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




    fun generateUniqueNumber(): Int {
        val currentTimeMillis = System.currentTimeMillis()
        val random = Random(currentTimeMillis)
        return random.nextInt()
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
                        totaltaxableamt)
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

                                payment = ""
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






}