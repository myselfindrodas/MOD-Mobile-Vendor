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
import com.example.modmobilesmartsale.data.model.ReviewListData
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentReviewBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.adapter.ReviewListAdapter
import com.example.modmobilesmartsale.viewmodel.CommonViewModel

class ReviewFragment : Fragment(), ReviewListAdapter.ReviewItemClickListener {

    private lateinit var binding: FragmentReviewBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: CommonViewModel
    var reviewListAdapter: ReviewListAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false)
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

        binding.topBarReview.tvTopBar.setText("Rating & Review")

        binding.topBarReview.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        mainActivity.setBottomNavigationVisibility(false)

        binding.btnWriteReview.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_write_review)
        }

        reviewListAdapter = ReviewListAdapter(mainActivity, this@ReviewFragment)
        binding.rvReviewList.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvReviewList.adapter = reviewListAdapter



    }



    override fun ReviewItemOnClick(position: Int, list: ArrayList<ReviewListData>, view: View) {

    }
}