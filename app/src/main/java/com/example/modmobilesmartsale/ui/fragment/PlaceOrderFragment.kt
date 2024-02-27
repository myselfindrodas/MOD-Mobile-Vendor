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
import com.example.modmobilesmartsale.data.model.PlacedOrderListData
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentPlaceOrderBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.adapter.PlacedOrderAdapter
import com.example.modmobilesmartsale.viewmodel.CommonViewModel

class PlaceOrderFragment : Fragment(), PlacedOrderAdapter.PlacedOrderItemClickListener {

    private lateinit var binding: FragmentPlaceOrderBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: CommonViewModel
    var placedOrderAdapter: PlacedOrderAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_place_order, container, false)
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

        binding.topBarPlaceOrder.tvTopBar.setText("Place Order")

        binding.topBarPlaceOrder.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        mainActivity.setBottomNavigationVisibility(false)

        placedOrderAdapter = PlacedOrderAdapter(mainActivity, this@PlaceOrderFragment)
        binding.rvPlaceOrderList.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvPlaceOrderList.adapter = placedOrderAdapter
//        placedOrderAdapter!!.updateData(placed_order_list)

        binding.btnPayNow.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_invoice)
        }

    }




    override fun placedOrderItemOnClick(position: Int, list: ArrayList<PlacedOrderListData>, view: View) {



    }

}