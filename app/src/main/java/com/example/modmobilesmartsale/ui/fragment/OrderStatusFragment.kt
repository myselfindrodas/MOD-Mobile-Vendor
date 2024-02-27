package com.example.modmobilesmartsale.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.OrderStatusData
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentOrderStatusBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.adapter.OrderStatusAdapter
import com.example.modmobilesmartsale.viewmodel.CommonViewModel

class OrderStatusFragment : Fragment(), OrderStatusAdapter.OrderStatusClickListener {

    private lateinit var binding: FragmentOrderStatusBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var mainActivity: MainActivity
    var orderStatusAdapter: OrderStatusAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_status, container, false)
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

        binding.topBarOrderStatus.tvTopBar.setText("Order Status")

        binding.topBarOrderStatus.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        mainActivity.setBottomNavigationVisibility(false)

        orderStatusAdapter = OrderStatusAdapter(mainActivity, this@OrderStatusFragment)
        val order_status_list = ArrayList<OrderStatusData>()
        order_status_list.add(OrderStatusData("Order Placed, 1st Sept 2023", "Your Order has been accepted"))
        order_status_list.add(OrderStatusData("Shipped, 3th Sept 2023", "Your Order has been shipped"))
        order_status_list.add(OrderStatusData("Out For Delivery, 5th Sept 2023", "Our delivery person is the way"))
        order_status_list.add(OrderStatusData("Delivered, 5th Sept 2023", "Item has been delivered"))

        binding.rvTrackOrderStatus.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvTrackOrderStatus.adapter = orderStatusAdapter
        orderStatusAdapter!!.updateData(order_status_list)
    }
    override fun orderStatusOnClick(position: Int, list: ArrayList<OrderStatusData>, view: View) {

    }

}