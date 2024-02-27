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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.FilterCategoryData
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentFilterBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.adapter.FilterCategoryAdapter
import com.example.modmobilesmartsale.ui.adapter.FilterScreenSizeAdapter
import com.example.modmobilesmartsale.data.model.FilterScreenSizeData
import com.example.modmobilesmartsale.viewmodel.CommonViewModel

class FilterFragment : Fragment(), FilterCategoryAdapter.OnItemClickListener
    ,FilterScreenSizeAdapter.FilterScreenSizeClickListener {

    private lateinit var binding: FragmentFilterBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var mainActivity: MainActivity
    private lateinit var filterCategoryAdapter: FilterCategoryAdapter
    private lateinit var filterScreenSizeAdapter: FilterScreenSizeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false)
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

        binding.topBarFilter.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        binding.topBarFilter.tvTopBar.setText("Filter")

        mainActivity.setBottomNavigationVisibility(false)

        filterCategoryAdapter = FilterCategoryAdapter(mainActivity, this@FilterFragment)
        val filter_list = ArrayList<FilterCategoryData>()
        for (i in 1..8){
            filter_list.add(FilterCategoryData(R.drawable.brand_image))
        }
        binding.rvFilterList.layoutManager = GridLayoutManager(mainActivity, 3)
        binding.rvFilterList.adapter = filterCategoryAdapter
        filterCategoryAdapter!!.updateData(filter_list)


        binding.rlCategory.setBackgroundResource(R.drawable.blue_rectangle_box)
        binding.categoryCheck.visibility = View.VISIBLE

        binding.rlCategory.setOnClickListener {
            filterOptions()
            binding.rlCategory.setBackgroundResource(R.drawable.blue_rectangle_box)
            binding.categoryCheck.visibility = View.VISIBLE
            binding.rvFilterList.visibility = View.VISIBLE

            filterCategoryAdapter = FilterCategoryAdapter(mainActivity, this@FilterFragment)
            val filter_list = ArrayList<FilterCategoryData>()
            for (i in 1..8){
                filter_list.add(FilterCategoryData(R.drawable.brand_image))
            }
            binding.rvFilterList.layoutManager = GridLayoutManager(mainActivity, 3)
            binding.rvFilterList.adapter = filterCategoryAdapter
            filterCategoryAdapter!!.updateData(filter_list)
        }

        binding.rlBrand.setOnClickListener {
            filterOptions()
            binding.rlBrand.setBackgroundResource(R.drawable.blue_rectangle_box)
            binding.brandCheck.visibility = View.VISIBLE

            filterCategoryAdapter = FilterCategoryAdapter(mainActivity, this@FilterFragment)
            val filter_list = ArrayList<FilterCategoryData>()
            for (i in 1..8){
                filter_list.add(FilterCategoryData(R.drawable.brand_image))
            }
            binding.rvFilterList.layoutManager = GridLayoutManager(mainActivity, 3)
            binding.rvFilterList.adapter = filterCategoryAdapter
            filterCategoryAdapter!!.updateData(filter_list)

        }

        binding.rlScreenSize.setOnClickListener {
            filterOptions()
            binding.rlScreenSize.setBackgroundResource(R.drawable.blue_rectangle_box)
            binding.screenSizeCheck.visibility = View.VISIBLE

            filterScreenSizeAdapter = FilterScreenSizeAdapter(mainActivity, this@FilterFragment)
            val filter_list2 = ArrayList<FilterScreenSizeData>()
            for (i in 1..8){
                filter_list2.add(FilterScreenSizeData("Size 10-20"))
            }

            binding.rvFilterList.layoutManager = LinearLayoutManager(mainActivity)
            binding.rvFilterList.adapter = filterCategoryAdapter
            filterScreenSizeAdapter!!.updateData(filter_list2)

        }

        binding.rlStorage.setOnClickListener {
            filterOptions()
            binding.rlStorage.setBackgroundResource(R.drawable.blue_rectangle_box)
            binding.storageCheck.visibility = View.VISIBLE

            filterScreenSizeAdapter = FilterScreenSizeAdapter(mainActivity, this@FilterFragment)
            val filter_list2 = ArrayList<FilterScreenSizeData>()
            for (i in 1..8){
                filter_list2.add(FilterScreenSizeData("Size 10-20"))
            }
            binding.rvFilterList.layoutManager = LinearLayoutManager(mainActivity)
            binding.rvFilterList.adapter = filterCategoryAdapter
            filterScreenSizeAdapter!!.updateData(filter_list2)

        }

        binding.btnApply.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_product_details)
        }
    }

    fun filterOptions(){
        binding.rlCategory.setBackgroundResource(R.drawable.white_rectangle_box)
        binding.categoryCheck.visibility = View.GONE
        binding.rlBrand.setBackgroundResource(R.drawable.white_rectangle_box)
        binding.brandCheck.visibility = View.GONE
        binding.rlScreenSize.setBackgroundResource(R.drawable.white_rectangle_box)
        binding.screenSizeCheck.visibility = View.GONE
        binding.rlStorage.setBackgroundResource(R.drawable.white_rectangle_box)
        binding.storageCheck.visibility = View.GONE
    }
    override fun OnClick(position: Int, list: ArrayList<FilterCategoryData>, view: View) {

    }

    override fun FilterScreenOnClick(
        position: Int,
        list: ArrayList<FilterScreenSizeData>,
        view: View
    ) {
    }
}