package com.example.modmobilesmartsale.ui.fragment

import android.app.AlertDialog
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
import com.example.modmobilesmartsale.data.model.myoderlistmodel.Data
import com.example.modmobilesmartsale.data.model.myoderlistmodel.OrderListRequest
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentOrdersBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.adapter.OrderAdapter
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel

class OrdersFragment : Fragment(), OrderAdapter.OrderItemClickListener {

    private lateinit var binding: FragmentOrdersBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var mainActivity: MainActivity
    var orderAdapter: OrderAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false)
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

        binding.topBarOrders.tvTopBar.setText("Orders")

        binding.topBarOrders.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        mainActivity.setBottomNavigationVisibility(false)

        orderAdapter = OrderAdapter(mainActivity, this@OrdersFragment)
        binding.rvOrderList.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvOrderList.adapter = orderAdapter
        orderlist()

    }




    private fun orderlist(){
        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.orderlist(
                OrderListRequest(
                    challan_no = Shared_Preferences.getUserId(),
                    token = Shared_Preferences.getToken().toString()
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {


                                orderAdapter?.updateData(resource.data?.response?.data!!)

                                if (orderAdapter?.itemCount == 0){

                                    binding.noOrdersAvailable.visibility = View.VISIBLE

                                }else{

                                    binding.noOrdersAvailable.visibility = View.GONE

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

    override fun orderItemOnClick(position: Int, list: ArrayList<Data>, view: View) {

        val bundle = Bundle()
        bundle.putSerializable("orderdetails", list[position])
        val navController = Navigation.findNavController(binding.root)
        navController.navigate(R.id.nav_order_details, bundle)

    }

}