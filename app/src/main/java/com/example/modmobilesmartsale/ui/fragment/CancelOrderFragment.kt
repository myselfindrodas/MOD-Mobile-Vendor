package com.example.modmobilesmartsale.ui.fragment

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
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
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.invoicecancelmodel.InvoiceCancelRequest
import com.example.modmobilesmartsale.data.model.myoderlistmodel.Data
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.FragmentCancelOrderBinding
import com.example.modmobilesmartsale.ui.MainActivity
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Status
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import com.squareup.picasso.Picasso
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener
import java.io.Serializable

class CancelOrderFragment : Fragment() {

    private lateinit var binding: FragmentCancelOrderBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var viewModel: CommonViewModel
//    var cancelReasonAdapter: CancelReasonAdapter?=null
    var data: Data?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cancel_order, container, false)
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

        binding.topBarCancelOrder.tvTopBar.setText("Cancel Order")

        binding.tvOrderId.text = data?.invoiceNo
        binding.tvCancelMobileQuality.text = data?.stockType
        binding.tvCancelMobileName.text = data?.modelName
        binding.tvCancelMobileStorage.text = data?.memory
        binding.tvCancelMobileOffPrice.text = "₹ "+data?.itemTotal
        binding.tvCancelMobileRealPrice.text = "₹ "+data?.mrp
        binding.tvSavePriceOnCancelMobile.text = "Save ₹ "+data?.itemTotal?.toFloat()?.minus(data?.purchasePrice?.toFloat()!!)
        binding.tvCancelMobileQty.text = "QTY : "+data?.qty

        Picasso.get()
            .load(data?.image1)
            .error(R.drawable.phone_image)
            .placeholder(R.drawable.phone_image)
            .into(binding.ivCancelMobileImage)

        binding.topBarCancelOrder.ivBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        mainActivity.setBottomNavigationVisibility(false)

        binding.btnSubmit.setOnClickListener {

            if (binding.etCancelreason.text.isEmpty()){
                Toast.makeText(mainActivity, "Enter Cancel Reason", Toast.LENGTH_SHORT).show()
            }else{

                cancelorder(binding.etCancelreason.text.toString())
            }
        }

//        cancelReasonAdapter = CancelReasonAdapter(mainActivity, this@CancelOrderFragment)
//        val order_status_list = ArrayList<CancelReasonData>()
//        for (i in 1..4){
//            order_status_list.add(CancelReasonData("Lorem ipsum dolor sit amet, consectetur adipisc"))
//        }
//
//        binding.rvReasonsForCancel.layoutManager = LinearLayoutManager(mainActivity)
//        binding.rvReasonsForCancel.adapter = cancelReasonAdapter
//        cancelReasonAdapter!!.updateData(order_status_list)
    }

//    override fun CancelReasonOnClick(position: Int, list: ArrayList<CancelReasonData>, view: View) {
//
//    }




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




    private fun cancelorder(reason:String){

        viewModel.invoicecancel(
            InvoiceCancelRequest(
                challanNo = data?.challanNo.toString(),
                remark = reason,
                token = Shared_Preferences.getToken().toString()
            )
        )
            .observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.response?.status.equals("true")) {


                                AestheticDialog.Builder(mainActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                                    .setTitle("Cancelled Successfully")
                                    .setMessage(resource.data?.response?.data!![0].crmInvoiceNo)
                                    .setCancelable(false)
                                    .setDarkMode(false)
                                    .setGravity(Gravity.CENTER)
                                    .setAnimation(DialogAnimation.SHRINK)
                                    .setOnClickListener(object : OnDialogClickListener {
                                        override fun onClick(dialog: AestheticDialog.Builder) {
                                            dialog.dismiss()
//                                            val navController = Navigation.findNavController(binding.root)
//                                            navController.navigate(R.id.nav_home)
                                            val intent = Intent(context,MainActivity::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            startActivity(intent)
                                        }
                                    })
                                    .show()



//                                val builder = androidx.appcompat.app.AlertDialog.Builder(mainActivity)
//                                builder.setMessage("Order Cancelled Successfully "+ resource.data?.response?.data!![0].crmInvoiceNo)
//                                builder.setPositiveButton(
//                                    "Ok"
//                                ) { dialog, which ->
//                                    val navController = Navigation.findNavController(binding.root)
//                                    navController.navigate(R.id.nav_home)
//                                    dialog.cancel()
//                                }
//
//                                val alert = builder.create()
//                                alert.setOnShowListener { arg0: DialogInterface? ->
//                                    alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
//                                        .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
//                                }
//                                alert.show()


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

    }

}