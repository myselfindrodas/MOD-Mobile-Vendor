package com.example.modmobilesmartsale.ui.fragment

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.CategoryListItemData
import com.example.modmobilesmartsale.data.model.stockmodel.Data
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentFeaturesAndSpecificationBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.ui.adapter.CategoryListItemAdapter
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import java.io.Serializable

class FeaturesAndSpecificationFragment : Fragment(), CategoryListItemAdapter.CategoryListItemOnItemClickListener {

    private lateinit var binding: FragmentFeaturesAndSpecificationBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var mainActivity: MainActivity
    var featuresListItemAdapter: CategoryListItemAdapter?= null
    var data: Data?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_features_and_specification, container, false)
        mainActivity = activity as MainActivity
        val root = binding.root
        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }
        viewModel = vm

        val intent = arguments
        if (intent != null && intent.containsKey("productdetails")) {
            data = getDataSerializable(intent, "productdetails", Data::class.java)
            Log.d(ContentValues.TAG, "details-->"+data)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        binding.topBarFeatures.tvTopBar.setText("Back")


        binding.topBarFeatures.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        mainActivity.setBottomNavigationVisibility(false)

        binding.tvFeature.text = Html.fromHtml(data?.features)

        binding.tvFrontVideoRecordingValue.text = data?.frontVideoRecorder
        binding.tvFrontVideoRecordingValue.text = data?.forntFlash
        binding.tvFrontCameraResolutionValue.text = data?.frontCameraResolution
        binding.tvFrontCameraSetupValue.text = data?.forntSetup
        binding.tvFrontSensorValue.text = data?.forntSenser
        binding.tvFrontCameraTypeValue.text = data?.primaryCamera
        binding.tvWiFiSetup.text = data?.wifi
        binding.tvFMRadioSetup.text = data?.fmRadio
        binding.tvWiFiFeaturesType.text = data?.wifiFeatures

//        featuresListItemAdapter = CategoryListItemAdapter(mainActivity, this@FeaturesAndSpecificationFragment)
//        val feature_list_item = ArrayList<CategoryListItemData>()
//        for (i in 1..4){
//            feature_list_item.add(CategoryListItemData("Lorem Ipsum is simply dummy text of the", "FeaturesList"))
//        }
//        binding.rvFeaturesList.layoutManager = LinearLayoutManager(mainActivity)
//        binding.rvFeaturesList.adapter = featuresListItemAdapter
//        featuresListItemAdapter!!.updateData(feature_list_item)

        binding.tvFeatures.setOnClickListener {
            binding.featureIndicator.visibility = View.VISIBLE
            binding.specificationIndicator.visibility = View.GONE
            binding.tvFeature.visibility = View.VISIBLE
            binding.tlSpecification.visibility = View.GONE
        }

        binding.tvSpecification.setOnClickListener {
            binding.featureIndicator.visibility = View.GONE
            binding.specificationIndicator.visibility = View.VISIBLE
            binding.tvFeature.visibility = View.GONE
            binding.tlSpecification.visibility = View.VISIBLE
        }
    }

    override fun categoryListItemOnClick(
        position: Int,
        list: ArrayList<CategoryListItemData>,
        view: View
    ) {

    }


    private fun <T : Serializable?> getDataSerializable(
        @Nullable bundle: Bundle?,
        @Nullable key: String?,
        clazz: Class<T>
    ): T? {
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

}