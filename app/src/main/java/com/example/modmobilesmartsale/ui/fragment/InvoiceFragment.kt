package com.example.modmobilesmartsale.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.OrderStatusData
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentInvoiceBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.adapter.OrderStatusAdapter
import com.example.modmobilesmartsale.viewmodel.CommonViewModel

class InvoiceFragment : Fragment(), OrderStatusAdapter.OrderStatusClickListener {

    private lateinit var binding: FragmentInvoiceBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: CommonViewModel
    var orderStatusAdapter: OrderStatusAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invoice, container, false)
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

        binding.topBarInvoice.tvTopBar.setText("Orders Details")

        binding.topBarInvoice.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        mainActivity.setBottomNavigationVisibility(false)

        binding.tvWriteReview.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_write_review)
        }

        binding.tvSellAllUpdatesInInvoice.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_order_status)
        }

        orderStatusAdapter = OrderStatusAdapter(mainActivity, this@InvoiceFragment)
        val order_status_list = ArrayList<OrderStatusData>()
        order_status_list.add(OrderStatusData("Order Placed, 1st Sept 2023", ""))
        order_status_list.add(OrderStatusData("Delivery, 5th Sept 2023", ""))

        binding.rvOrderStatusInInvoice.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvOrderStatusInInvoice.adapter = orderStatusAdapter
        orderStatusAdapter!!.updateData(order_status_list)
    }

    override fun orderStatusOnClick(position: Int, list: ArrayList<OrderStatusData>, view: View) {

    }
}