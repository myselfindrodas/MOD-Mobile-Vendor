package com.example.modmobilesmartsale.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.modmobilesmartsale.data.model.dashboardmodel.TopBrand
import com.example.modmobilesmartsale.data.model.stockmodel.Data
import com.example.modmobilesmartsale.data.model.stockmodel.StockRequest
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentSearchBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.adapter.BrandListAdapter
import com.example.modmobilesmartsale.ui.adapter.SearchAdapter
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel

class SearchFragment : Fragment(), BrandListAdapter.BrandListOnItemClickListener, SearchAdapter.SearchItemClickListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var mainActivity: MainActivity
    var brandListAdapter: BrandListAdapter ?= null
    var searchAdapter: SearchAdapter ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
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

        binding.topBarSearch.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        binding.topBarSearch.tvTopBar.setText("Search")

        mainActivity.setBottomNavigationVisibility(false)

        brandListAdapter = BrandListAdapter(mainActivity, this@SearchFragment)
//        val brand_list = ArrayList<BrandData>()
//        for (i in 1..3){
//            brand_list.add(BrandData(R.drawable.brand_image))
//        }
        binding.popularSearchList.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.popularSearchList.adapter = brandListAdapter
//        brandListAdapter!!.updateData(brand_list)

        searchAdapter = SearchAdapter(mainActivity, this@SearchFragment)
//        val search_list = ArrayList<SearchData>()
//        for (i in 1..3){
//            search_list.add(SearchData("I Phone"))
//        }
        binding.rvSearchList.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvSearchList.adapter = searchAdapter
//        searchAdapter!!.updateData(search_list)


        binding.etSearchProduct.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length > 0) {
                    binding.rvSearchList.visibility = View.VISIBLE
                    binding.noData.visibility = View.GONE
                    search(s.toString())
                } else {

                    binding.rvSearchList.visibility = View.GONE
//                    Toast.makeText(mainActivity, "Enter Keyword", Toast.LENGTH_SHORT).show()

//                    refferalAdapter?.updateData(refferalListModelArrayList)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

    }


    private fun search(searchkeyword:String){

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
                    search = searchkeyword,
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
                                searchAdapter?.updateData(resource.data?.response?.data!!)

                                if (resource.data?.response?.data!!.isEmpty()){

                                    binding.noData.visibility = View.VISIBLE

                                }else{

                                    binding.noData.visibility = View.GONE

                                }

//                                val dealsofthedaylist = ArrayList<Data>()
//                                val androidlist = ArrayList<Data>()
//                                val iphonelist = ArrayList<Data>()
//                                for (i in 0 until resource.data?.response?.data!!.size) {
//                                    if (resource.data.response.data[i].dealOfDays.equals("Y")){
//                                        dealsofthedaylist.add(resource.data.response.data[i])
//                                        dayDealsAdapter?.updateData(dealsofthedaylist)
//                                    }
//                                    if (resource.data.response.data[i].phoneVersion.equals("Android")){
//                                        androidlist.add(resource.data.response.data[i])
//                                        androidPhoneAdapter?.updateData(androidlist)
//                                    }
//
//                                    if (resource.data.response.data[i].phoneVersion.equals("Iphone")){
//                                        iphonelist.add(resource.data.response.data[i])
//                                        iPhoneAdapter?.updateData(iphonelist)
//                                    }
//
//                                }



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

    override fun brandListOnClick(position: Int, list: ArrayList<TopBrand>, view: View) {

    }




    override fun searchListOnClick(position: Int, list: ArrayList<Data>, view: View) {

        val bundle = Bundle()
        bundle.putSerializable("itemdetails", list[position])
        val navController = Navigation.findNavController(binding.root)
        navController.navigate(R.id.nav_product_details, bundle)

    }
}