package com.example.modmobilesmartsale.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.CategoryListItemData
import com.example.modmobilesmartsale.data.model.FeedBackData
import com.example.modmobilesmartsale.data.model.addtocartmodel.AddtoCartItem
import com.example.modmobilesmartsale.data.model.addtocartmodel.AddtoCartRequest
import com.example.modmobilesmartsale.data.model.defaultaddressmodel.DefaultAddressRequest
import com.example.modmobilesmartsale.data.model.favouritemodel.FavAddRemoveRequest
import com.example.modmobilesmartsale.data.model.getfavouritemodel.FavListRequest
import com.example.modmobilesmartsale.data.model.getimagemodel.GetProductImageRequest
import com.example.modmobilesmartsale.data.model.invoicecreatemodel.BillJSON
import com.example.modmobilesmartsale.data.model.invoicecreatemodel.InvoiceDetail
import com.example.modmobilesmartsale.data.model.invoicecreatemodel.InvoicecreateRequest
import com.example.modmobilesmartsale.data.model.manageaddressmodel.ManageAddressRequest
import com.example.modmobilesmartsale.data.model.pincodemodel.PincodeRequest
import com.example.modmobilesmartsale.data.model.pocreatemodel.OrderDetail
import com.example.modmobilesmartsale.data.model.pocreatemodel.POJSON
import com.example.modmobilesmartsale.data.model.pocreatemodel.POcreateRequest
import com.example.modmobilesmartsale.data.model.registrationsuccessmodel.Recipient
import com.example.modmobilesmartsale.data.model.registrationsuccessmodel.RegistrationSuccessRequest
import com.example.modmobilesmartsale.data.model.reviewlistmodel.ReviewlistRequest
import com.example.modmobilesmartsale.data.model.stockmodel.Data
import com.example.modmobilesmartsale.data.model.stockmodel.StockRequest
import com.example.modmobilesmartsale.data.model.storagecolormodel.StorageColorRequest
import com.example.modmobilesmartsale.data.model.viewaddressmodel.Addressdata
import com.example.modmobilesmartsale.data.model.viewaddressmodel.ViewAddressRequest
import com.example.modmobilesmartsale.data.model.viewcartmodel.ViewCartRequest
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory2
import com.example.modmobilesmartsale.databinding.FragmentProductDetailsBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.MainActivity.Companion.payment
import com.example.modmobilesmartsale.ui.adapter.AddressAdapter
import com.example.modmobilesmartsale.ui.adapter.CategoryListItemAdapter
import com.example.modmobilesmartsale.ui.adapter.ColorListAdapter
import com.example.modmobilesmartsale.ui.adapter.FeedBackAdapter
import com.example.modmobilesmartsale.ui.adapter.RelatedProductListAdapter
import com.example.modmobilesmartsale.ui.adapter.SlidingPhonesImageAdapter
import com.example.modmobilesmartsale.ui.adapter.StorageAdapter
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import com.example.modmobilesmartsale.viewmodel.CommonViewModel2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.like.LikeButton
import com.like.OnLikeListener
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random


class ProductDetailsFragment : Fragment(), SlidingPhonesImageAdapter.OnItemClickListener,
    StorageAdapter.StorageItemClickListener, ColorListAdapter.ColorItemClickListener,
    CategoryListItemAdapter.CategoryListItemOnItemClickListener,
    RelatedProductListAdapter.RelatedProductItemClickListener,
    FeedBackAdapter.FeedBackListOnItemClickListener,
    AddressAdapter.AddressOnItemClickListener{

    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var viewModel2: CommonViewModel2
    private lateinit var mainActivity: MainActivity
    var dialog:BottomSheetDialog?=null
    var dialog2:BottomSheetDialog?=null

    var slidingPhonesImageAdapter: SlidingPhonesImageAdapter? = null
    var addressAdapter: AddressAdapter?= null

    var storageAdapter: StorageAdapter? = null
    var colorListAdapter: ColorListAdapter? = null
    var feedBackAdapter: FeedBackAdapter? = null
    var featuresListItemAdapter: CategoryListItemAdapter? = null
    var relatedProductListAdapter: RelatedProductListAdapter? = null
    private var storageExpanded = -1
    private var colorExpanded = -1
    var checkFavourite: Int = 0
    var data: Data? = null
    var modelcode = ""
    var price = ""
    var productid = ""
    var purchaseprice = ""
    var stocktype = ""
    var orderdetailsItem = ArrayList<OrderDetail>()
    var invoicedetailsItem = ArrayList<InvoiceDetail>()
    var shippingfromaddress = ""
    var shippingcity = ""
    var parentstate = ""
    var parentcity = ""
    var totalamount = ""
    var totalqty = ""
    var margintype = ""
    var sgstPer = ""
    var cgstPer = ""
    var igstPer = ""
    var hsncode = ""
    var taxablevalue = ""
    var type = ""
    var paymentmode = ""
    var cart = "add to cart"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)
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
        if (intent != null && intent.containsKey("itemdetails")) {
            data = getDataSerializable(intent, "itemdetails", Data::class.java) as Data?
            Log.d(TAG, "details-->" + data)
        }



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        binding.topBarProductDetails.tvTopBar.setText("Product Details")
        binding.phoneName.text = data?.modelName
        binding.tvphoneVersion.text = data?.stockType
        binding.tvStockqty.text = data?.stockQty + " In Stock"
        var pricedifference:Float?=null
        if (Shared_Preferences.getUserType().equals("Retailer")){
            pricedifference = data?.mRP?.toFloat()!!.minus(data?.retPrice!!.toFloat())
            binding.tvCustomerprice.text = "₹ " + data?.retPrice
            price = data?.retPrice.toString()
        }else if (Shared_Preferences.getUserType().equals("Distributer")){
            pricedifference = data?.mRP?.toFloat()!!.minus(data?.distPrice!!.toFloat())
            binding.tvCustomerprice.text = "₹ " + data?.distPrice
            price = data?.distPrice.toString()
        }
        val offpercent = (pricedifference?.div(data?.mRP?.toFloat()!!)!! * 100)
        binding.tvDiscount.text = String.format("%.2f", offpercent) + " %"
        binding.tvMRP.text = "₹ " + data?.mRP
        binding.tvSaveAmount.text = "Save ₹ " + pricedifference
        binding.tvFeatures.text = Html.fromHtml(data?.features)

        modelcode = data?.modelCode.toString()
        productid = data?.imei1.toString()
        purchaseprice = data?.purchasePrice.toString()
        stocktype = data?.stockType.toString()

        binding.topBarProductDetails.ivBack.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_home)
        }

        mainActivity.setBottomNavigationVisibility(false)

        slidingPhonesImageAdapter =
            SlidingPhonesImageAdapter(mainActivity, this@ProductDetailsFragment)
        binding.slidingPhonesImage.setAdapter(slidingPhonesImageAdapter)
        binding.sliderDots.attachTo(binding.slidingPhonesImage)

        if (data?.favourite.equals("Y")) {
            binding.favButton.isLiked = true
        } else {
            binding.favButton.isLiked = false
        }

        binding.favButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {

                addremovefavourite()
            }

            override fun unLiked(likeButton: LikeButton) {
                addremovefavourite()
            }
        })

//        binding.imgFavourite.setOnClickListener {
//            if (checkFavourite == 0) {
//                binding.imgFavourite.setImageResource(R.drawable.ic_add_to_favourite)
//                checkFavourite = 1
//            } else {
//                binding.imgFavourite.setImageResource(R.drawable.icon_red_heart)
//            }
//        }

        storageAdapter = StorageAdapter(mainActivity, this@ProductDetailsFragment)
        binding.rvStorageList.layoutManager =
            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvStorageList.adapter = storageAdapter

        colorListAdapter = ColorListAdapter(mainActivity, this@ProductDetailsFragment)
        binding.rvColorList.layoutManager =
            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvColorList.adapter = colorListAdapter



        feedBackAdapter = FeedBackAdapter(mainActivity, this@ProductDetailsFragment)
        binding.feedbackList.layoutManager =
            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.feedbackList.adapter = feedBackAdapter


        featuresListItemAdapter = CategoryListItemAdapter(mainActivity, this@ProductDetailsFragment)
        val feature_list_item = ArrayList<CategoryListItemData>()
        for (i in 1..4) {
            feature_list_item.add(
                CategoryListItemData(
                    "Lorem Ipsum is simply dummy text of the",
                    "FeaturesList"
                )
            )
        }


        relatedProductListAdapter =
            RelatedProductListAdapter(mainActivity, this@ProductDetailsFragment)
        binding.rvRelatedProducts.layoutManager =
            LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvRelatedProducts.adapter = relatedProductListAdapter

        binding.llStorage.setOnClickListener {
            if (storageExpanded == -1) {
                binding.rvStorageList.visibility = View.VISIBLE
                storageExpanded = 1
            } else {
                binding.rvStorageList.visibility = View.GONE
                storageExpanded = -1
            }
        }
        binding.llColor.setOnClickListener {
            if (colorExpanded == -1) {
                binding.rvColorList.visibility = View.VISIBLE
                colorExpanded = 1
            } else {
                binding.rvColorList.visibility = View.GONE
                colorExpanded = -1
            }
        }


        binding.btnCheck.setOnClickListener {

            if (binding.etPincode.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Please enter pincode", Toast.LENGTH_SHORT).show()
            } else {

                checkpincode(binding.etPincode.text.toString())
            }
        }

        binding.rlSpecification.setOnClickListener {

            val bundle = Bundle()
            bundle.putSerializable("productdetails", data)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_features, bundle)
        }


        binding.rlQCdetails.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("imei", data?.imei1)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_qclist, bundle)
        }


        binding.btnAddToCart.setOnClickListener {

            if (cart.equals("add to cart")){
                addCart()
            }else{
                val navController = Navigation.findNavController(binding.root)
                navController.navigate(R.id.nav_cart)
            }
        }



        binding.btnBuyNow.setOnClickListener {

            showBottomDialog()

        }


        storagecolor()
        getproductimage()
        getrelatedproductlist(data?.brandid.toString())
        productfeedback()
    }

    private fun showBottomDialog() {
        dialog = BottomSheetDialog(mainActivity,R.style.SheetDialog)
        dialog!!.setContentView(R.layout.bottom_sheet_buy_now)
        val addressListItems = dialog!!.findViewById<RecyclerView>(R.id.addressListItems)
        val btnAddNewAddress = dialog!!.findViewById<Button>(R.id.btnAddAddress)
        val btnContinue = dialog!!.findViewById<Button>(R.id.btnBuy)

        addressAdapter = AddressAdapter(mainActivity, this@ProductDetailsFragment)
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

                dialog2 = BottomSheetDialog(mainActivity,R.style.SheetDialog)
                dialog2!!.setContentView(R.layout.bottom_sheet_payment)
                val btnCOD = dialog2!!.findViewById<AppCompatButton>(R.id.btnCOD)
                val btnOnline = dialog2!!.findViewById<AppCompatButton>(R.id.btnOnline)
                dialog2!!.show()
                btnCOD!!.setOnClickListener {

                    dialog2!!.dismiss()
                    paymentmode = "cod"
                    directbuy()

                }


                btnOnline!!.setOnClickListener {

                    dialog2!!.dismiss()
                    paymentmode = "wallet"
                    mainActivity.startPayment(price)

                }

//               mainActivity.startPayment(price)

            }
            dialog!!.dismiss()

        }
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

    private fun productfeedback(){

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.getreview(
                ReviewlistRequest(
                    productid = data?.modelCode.toString(),
                    token = Shared_Preferences.getToken().toString(),
                    userid = Shared_Preferences.getUserId()
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {

                                feedBackAdapter!!.updateData(resource.data?.response?.data!!)

                            }



                        }

                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
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


    private fun storagecolor() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.getcolorstorage(
                StorageColorRequest(
                    modelName = data?.modelCode.toString(),
                    token = Shared_Preferences.getToken().toString()
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()

                                if (resource.data?.response?.status.equals("true")) {

                                    storageAdapter?.updateData(resource.data?.response?.data!!)
                                    colorListAdapter?.updateData(resource.data?.response?.data!!)


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
//
//                                    Toast.makeText(mainActivity, resource.data.get(0).response, Toast.LENGTH_SHORT).show()
//                                    val navController = Navigation.findNavController(binding.root)
//                                    navController.navigate(R.id.nav_cart)
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


    override fun onResume() {
        super.onResume()


        if (payment.equals("success")){

            directbuy()
        }

        favouriteList()

        viewCartList()
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

                                    if (resource.data!!.response.data.isNotEmpty()) {

                                        resource.data.response.data.forEach { favList ->

                                            if (favList.imei1 == productid) {
                                                binding.favButton.isLiked = true
                                                return@observe

                                            } else {
                                                binding.favButton.isLiked = false

                                            }
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

    override fun onDestroy() {
        super.onDestroy()
        payment = ""

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

                                for (i in 0 until resource.data?.response?.data!!.cartdata.size) {
                                    if (resource.data.response.data.cartdata[i].productId == data?.imei1) {

                                        shippingfromaddress = resource.data.response.data.sellerAddress
                                        parentstate = resource.data.response.data.parentState
                                        parentcity = resource.data.response.data.parentCity
                                        shippingfromaddress = resource.data.response.data.sellerAddress
                                        shippingcity = resource.data.response.data.parentCity

                                        totalamount = resource.data.response.data.totalAmt
                                        totalqty = resource.data.response.data.totQnty.toString()
                                        parentstate = resource.data.response.data.parentState
                                        margintype = resource.data.response.data.cartdata[i].tax_type
                                        cgstPer = resource.data.response.data.cartdata[i].cgstPer.toString()
                                        sgstPer = resource.data.response.data.cartdata[i].sgstPer.toString()
                                        igstPer = resource.data.response.data.cartdata[i].igstPer.toString()
                                        hsncode = resource.data.response.data.cartdata[i].hsnCode.toString()
                                        taxablevalue = resource.data.response.data.cartdata[i].taxableValue.toString()
                                        type = resource.data.response.data.cartdata[i].type

                                        orderdetailsItem.add(
                                            OrderDetail(
                                                stocktype,
                                                binding.phoneName.text.toString(),
                                                price,
                                                "1",
                                                modelcode
                                            )
                                        )

                                        pocreate()

                                    }
                                }
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


    @SuppressLint("SuspiciousIndentation")
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
                                        var basicamount = 0

//                                        var totalamount = 0

                                            if (parentstate.equals("Delhi")) {
                                                if (margintype.equals("margin")) {
                                                    marginprice = (price.toDouble().minus(purchaseprice.toDouble())).toString()
                                                    basicamount = ((marginprice.toDouble())/((100.00+sgstPer.toDouble()+cgstPer.toDouble())/100)).toInt()

                                                    sgstcost = ((sgstPer.toDouble() / 100.00) * basicamount).toString()
                                                    cgstcost = ((cgstPer.toDouble() / 100.00) * basicamount).toString()

                                                } else {
                                                    sgstcost = ((sgstPer.toDouble() / 100.00) * price.toDouble()).toString()
                                                    cgstcost = ((cgstPer.toDouble() / 100.00) * price.toDouble()).toString()

                                                }

                                                totalcgst += cgstcost.toDouble().toInt()
                                                totalsgst += sgstcost.toDouble().toInt()
//                                                basicamount = (taxablevalue.toDouble()-((cgstPer.toDouble()+sgstPer.toDouble())/100.00) * taxablevalue.toDouble()).toInt()


                                                Log.d(TAG, "totalsgst-->" + totalsgst)
                                                Log.d(TAG, "totalcgst-->" + totalcgst)

                                            } else {
                                                if (margintype.equals("margin")) {
                                                    marginprice = (price.toDouble().minus(purchaseprice.toDouble())).toString()
                                                    basicamount = ((marginprice.toDouble())/((100.00+igstPer.toDouble())/100)).toInt()
                                                    igstcost = ((igstPer.toDouble() / 100.00) * basicamount).toString()

                                                } else {
                                                    igstcost = ((igstPer.toDouble() / 100.00) * price.toDouble()).toString()
                                                }

                                                totaligst += igstcost.toDouble().toInt()
//                                                basicamount = (taxablevalue.toDouble()-(igstPer.toDouble()/100.00) * taxablevalue.toDouble()).toInt()

                                                Log.d(TAG, "totaligst-->" + totaligst)

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
                                                    binding.phoneName.text.toString(),
                                                    price,
                                                    purchaseprice,
                                                    "1",
                                                    sgstcost,
                                                    sgstPer,
                                                    basicamount.toDouble(),
                                                    type
                                                )
                                            )

                                        if (parentstate.equals("Delhi")) {
//                                                totaltaxableamount += cartList[i].taxableValue.toDouble().toInt()
//                                            totaltaxableamount += (taxablevalue.toDouble()-((cgstPer.toDouble()+sgstPer.toDouble())/100.00) * taxablevalue.toDouble()).toInt()
                                            totaltaxableamount += ((price.toDouble().minus(purchaseprice.toDouble()))/((100.00+sgstPer.toDouble()+cgstPer.toDouble())/100)).toInt()

//                                                basicamount = (cartList[i].taxableValue.toDouble()-((cartList[i].cgstPer.toDouble()+cartList[i].sgstPer.toDouble())/100.00) * cartList[i].taxableValue.toDouble()).toInt()


                                        }else{
                                            totaltaxableamount += ((price.toDouble().minus(purchaseprice.toDouble()))/((100.00+igstPer.toDouble())/100)).toInt()
                                        }

//                                            totaltaxableamount += taxablevalue.toDouble().toInt()
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

    fun generateUniqueNumber(): Int {
        val currentTimeMillis = System.currentTimeMillis()
        val random = Random(currentTimeMillis)
        return random.nextInt()
    }


    private fun getstocklist(color: String, memory: String, modelname: String) {

        var userType=""
        if (Shared_Preferences.getUserType().equals("Retailer")){
            userType = "Ret"
        }else{
            userType = "Dist"
        }


        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.getstock(
                StockRequest(
                    ascCode = "MMWHDL002", brandId = "",
                    hotDeal = "",
                    search = "",
                    imei = "",
                    modelCode = "",
                    color = color,
                    memory = memory,
                    modelName = modelname,
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



                                if (resource.data?.response?.data?.isEmpty()!!){

                                    val builder = AlertDialog.Builder(mainActivity)
                                    builder.setMessage("This variant is not available!")
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

                                }else{


                                    modelcode = resource.data?.response?.data?.get(0)?.modelCode.toString()
                                    if (Shared_Preferences.getUserType().equals("Retailer")){
                                        price = resource.data?.response?.data?.get(0)?.retPrice.toString()

                                    }else if (Shared_Preferences.getUserType().equals("Distributer")){
                                        price = resource.data?.response?.data?.get(0)?.distPrice.toString()

                                    }
                                    productid = resource.data?.response?.data?.get(0)?.imei1.toString()
                                    purchaseprice =
                                        resource.data?.response?.data?.get(0)?.purchasePrice.toString()
                                    stocktype =
                                        resource.data?.response?.data?.get(0)?.stockType.toString()

                                    binding.phoneName.text =
                                        resource.data?.response?.data?.get(0)?.modelName
                                    binding.tvphoneVersion.text =
                                        resource.data?.response?.data?.get(0)?.brand
                                    binding.tvStockqty.text =
                                        resource.data?.response?.data?.get(0)?.stockQty + " In Stock"
                                    var pricedifference:Float?=null
                                    if (Shared_Preferences.getUserType().equals("Retailer")){
                                        pricedifference = resource.data?.response?.data?.get(0)?.mRP?.toFloat()!!.minus(resource.data?.response?.data?.get(0)?.retPrice!!.toFloat())

                                    }else if (Shared_Preferences.getUserType().equals("Distributer")){

                                        pricedifference = resource.data?.response?.data?.get(0)?.mRP?.toFloat()!!.minus(resource.data?.response?.data?.get(0)?.distPrice!!.toFloat())

                                    }
                                    val offpercent = (pricedifference?.div(resource.data?.response?.data?.get(0)?.mRP?.toFloat()!!)!! * 100)
                                    binding.tvDiscount.text = String.format("%.2f", offpercent) + " %"

                                    if (Shared_Preferences.getUserType().equals("Retailer")){
                                        binding.tvCustomerprice.text =
                                            "₹ " + resource.data?.response?.data?.get(0)?.retPrice
                                    }else if (Shared_Preferences.getUserType().equals("Distributer")){
                                        binding.tvCustomerprice.text =
                                            "₹ " + resource.data?.response?.data?.get(0)?.distPrice

                                    }

                                    binding.tvMRP.text =
                                        "₹ " + resource.data?.response?.data?.get(0)?.mRP
                                    binding.tvSaveAmount.text = "Save ₹ " + pricedifference
                                    binding.tvFeatures.text =
                                        resource.data?.response?.data?.get(0)?.features

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

    private fun getrelatedproductlist(brandid: String) {

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
                    brandId = brandid,
                    hotDeal = "",
                    search = "",
                    imei = "",
                    modelCode = "",
                    color = "",
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


                                relatedProductListAdapter?.updateData(resource.data?.response?.data!!)


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


    @SuppressLint("SuspiciousIndentation")
    private fun getproductimage() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.getproductimage(
                GetProductImageRequest(
                    imei = data?.imei1.toString(),
                    token = Shared_Preferences.getToken().toString()
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()

                                if (resource.data?.response?.status.equals("true")) {

                                    try {

                                        val imagelist = ArrayList<String>()
                                        if (resource.data?.response?.data?.imaUrl!![0].image1!=null) {
                                            imagelist.add(resource.data?.response?.data?.imaUrl!![0].image1)
                                        }
                                        if (resource.data.response.data.imaUrl[0].image2!=null) {
                                            imagelist.add(resource.data.response.data.imaUrl[0].image2)
                                        }
                                        if (resource.data.response.data.imaUrl[0].image3!=null) {
                                            imagelist.add(resource.data.response.data.imaUrl[0].image3)
                                        }
                                        if (resource.data.response.data.imaUrl[0].image4!=null) {
                                            imagelist.add(resource.data.response.data.imaUrl[0].image4)
                                        }
                                        slidingPhonesImageAdapter?.updateImageData(imagelist)


                                    } catch (e: Exception) {
                                        Log.d(TAG, "exception-->" + e)
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


    private fun checkpincode(pincode: String) {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.checkpincode(
                PincodeRequest(
                    pincode = pincode,
                    token = Shared_Preferences.getToken().toString()
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()

                                if (resource.data?.response?.status.equals("true")) {

                                    if (resource.data?.response?.data!!.isNotEmpty()) {
                                        if (resource.data.response.data[0].delivery.equals("Y")) {
                                            binding.tvDaysLeftForDelivery.text =
                                                "Item Delivery Available"
                                        }
                                    } else if (resource.data.response.data.isEmpty()) {

                                        binding.tvDaysLeftForDelivery.text =
                                            "Delivery Not Available"

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


    private fun addremovefavourite() {

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.addfav(
                FavAddRemoveRequest(
                    userid = Shared_Preferences.getUserId(),
                    productid = data?.imei1.toString(),
                    token = Shared_Preferences.getToken().toString()
                )
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()

                                if (resource.data?.response?.status.equals("true")) {

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


    override fun onClick(position: Int, view: View) {

    }

    override fun storageListOnClick(
        position: Int,
        list: ArrayList<com.example.modmobilesmartsale.data.model.storagecolormodel.Data>,
        view: View
    ) {


        storageAdapter?.updateData(list)
        getstocklist(list[position].color, list[position].memory, data?.model.toString())
    }

    override fun colorListOnClick(
        position: Int,
        list: ArrayList<com.example.modmobilesmartsale.data.model.storagecolormodel.Data>,
        view: View
    ) {
        colorListAdapter?.updateData(list)
        getstocklist(list[position].color, list[position].memory, data?.model.toString())

    }

    override fun categoryListItemOnClick(
        position: Int,
        list: ArrayList<CategoryListItemData>,
        view: View
    ) {


    }

    override fun relatedProductOnClick(
        position: Int,
        list: ArrayList<Data>,
        view: View
    ) {

        val bundle = Bundle()
        bundle.putSerializable("itemdetails", list[position])
        val navController = Navigation.findNavController(binding.root)
        navController.navigate(R.id.nav_product_details, bundle)

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
                                Toast.makeText(mainActivity, "Default Address Changed Successfully", Toast.LENGTH_SHORT).show()
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



    override fun FeedbackOnClick(position: Int, list: ArrayList<FeedBackData>, view: View) {

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






    private fun viewCartList() {
        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.viewcart(
                ViewCartRequest(
                    token = Shared_Preferences.getToken().toString(),
                    userid = Shared_Preferences.getUserId())).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {

                                if (resource.data?.response?.data!!.cartdata.isNotEmpty()) {

                                    resource.data.response.data.cartdata.forEach { cartList ->
//                                        println(cartList.productId)
//                                        println("proId"+productid)
                                        if (cartList.productId.equals(productid.trim()) ) {
                                            binding.btnAddToCart.setText("GO TO CART")
                                            cart = "gotocart"

                                            return@observe

                                        } else {
                                            binding.btnAddToCart.setText("ADD TO CART")
                                            cart = "add to cart"

                                        }
                                    }
                                }

                            }else{

                                binding.btnAddToCart.setText("ADD TO CART")
                                cart = "add to cart"
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

}