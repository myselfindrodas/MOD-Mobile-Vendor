package com.example.modmobilesmartsale.ui.fragment

import android.app.AlertDialog
import android.content.ContentValues
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.OrderStatusData
import com.example.modmobilesmartsale.data.model.myoderlistmodel.Data
import com.example.modmobilesmartsale.data.model.orderlistdetailsmodel.OrderRequest
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentOrderDetailsBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.adapter.OrderStatusAdapter
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import com.squareup.picasso.Picasso
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class OrderDetailsFragment : Fragment(), OrderStatusAdapter.OrderStatusClickListener {

    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var mainActivity: MainActivity
    var orderStatusAdapter: OrderStatusAdapter?=null
    var data: Data?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false)
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

        binding.topBarOrderDetails.tvTopBar.setText("Orders Details")

        binding.tvOrderId.text = data?.invoiceNo
        binding.tvDeliveryBy.text = data?.deliveryTime
        binding.tvOrderedMobileQuality.text = data?.stockType
        binding.tvOrderedMobileStorage.text = data?.memory
        binding.tvOrderedMobileOffPrice.text = "₹ "+data?.itemTotal
        binding.tvOrderedMobileRealPrice.text = "₹ "+data?.mrp
        binding.tvSavePriceOnOrderedMobile.text = "Save ₹ "+data?.mrp?.toFloat()?.minus(data?.itemTotal?.toFloat()!!)
        binding.tvOrderedMobileQty.text = "QTY : "+data?.qty
        binding.tvItemPrice.text = "₹ "+data?.itemTotal
        binding.tvDiscount.text = "₹ "+data?.discount
        binding.tvTotalAmount.text = "₹ "+data?.itemTotal?.toFloat()?.minus(data?.discount?.toFloat()!!)


        Picasso.get()
            .load(data?.image1)
            .error(R.drawable.phone_image)
            .placeholder(R.drawable.phone_image)
            .into(binding.ivMobileImage)

        binding.tvOrderedMobileName.text = data?.modelName


        binding.topBarOrderDetails.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        val dateTimeString = data?.datetime
        val hoursToAdd = data?.working_hour
        val newDateTimeString = addHoursToTimeString(dateTimeString.toString(), hoursToAdd, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss")

        Log.d(ContentValues.TAG, "newDateTime-->"+newDateTimeString)
        if (newDateTimeString != null) {
            println(newDateTimeString) // "2024-02-20 15:23:45"
        } else {
            // Handle parsing error
        }

        if (hasExceededDateTime(newDateTimeString.toString())) {
            binding.rlCancelOrder.visibility = View.GONE
            println("The datetime has already passed.")
        } else {
            binding.rlCancelOrder.visibility = View.VISIBLE
            println("The datetime is still in the future or equal to the current date.")
        }

//        if (data?.working_hour?.toInt()!! >=48){
//
//            binding.rlCancelOrder.visibility = View.GONE
//        }else{
//
//            binding.rlCancelOrder.visibility = View.VISIBLE
//        }

        mainActivity.setBottomNavigationVisibility(false)

        orderStatusAdapter = OrderStatusAdapter(mainActivity, this@OrderDetailsFragment)
        val order_status_list = ArrayList<OrderStatusData>()
        order_status_list.add(OrderStatusData("Order Placed, 1st Sept 2023", ""))
        order_status_list.add(OrderStatusData("Delivery, 5th Sept 2023", ""))

        binding.rvOrderStatus.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvOrderStatus.adapter = orderStatusAdapter
        orderStatusAdapter!!.updateData(order_status_list)

        binding.tvSellAllUpdates.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_order_status)
        }

        binding.rlCancelOrder.setOnClickListener {

            val bundle = Bundle()
            bundle.putSerializable("orderdetails", data)
            val navController = Navigation.findNavController(binding.root)
            navController.navigate(R.id.nav_cancel_order, bundle)

        }

        if (data?.status.equals("Dispatched")){

            binding.btnWriteReview.visibility = View.VISIBLE
        }else{
            binding.btnWriteReview.visibility = View.GONE

        }


        binding.btnWriteReview.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("orderdetails", data)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_write_review, bundle)
        }


        orderdetails()
    }



    fun addHoursToTimeString(dateString: String, hoursToAdd: String?, inputFormat: String, outputFormat: String): String? {
        val format = SimpleDateFormat(inputFormat)
        try {
            val date = format.parse(dateString)

            val cal = Calendar.getInstance()
            cal.time = date
            cal.add(Calendar.HOUR_OF_DAY, hoursToAdd!!.toInt())

            val outputFormatter = SimpleDateFormat(outputFormat)
            return outputFormatter.format(cal.time)
        } catch (e: ParseException) {
            Log.e("DateTimeUtils", "Error parsing date", e)
            return null // Or handle differently if needed
        }
    }


    fun hasExceededDateTime(dateTimeString: String): Boolean {
        val inputFormat = "yyyy-MM-dd HH:mm:ss"
        val format = SimpleDateFormat(inputFormat)

        val dateTime = try {
            format.parse(dateTimeString)
        } catch (e: ParseException) {
            Log.e("DateTimeUtils", "Error parsing date-time string", e)
            return false // Return false for parsing failure
        }

        val currentDate = Date()
        return currentDate.after(dateTime)
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


    override fun orderStatusOnClick(position: Int, list: ArrayList<OrderStatusData>, view: View) {

    }


    private fun orderdetails(){

        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.orderdetails(OrderRequest(invoiceNo = data?.invoiceNo.toString(), token = Shared_Preferences.getToken().toString()))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()

                                if (resource.data?.response?.status.equals("true")) {


                                    binding.tvDeliveryAddress.text = resource.data?.response?.data!![0].customerAddress

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
}