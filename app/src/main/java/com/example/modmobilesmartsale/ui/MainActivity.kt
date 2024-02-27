package com.example.modmobilesmartsale.ui


import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.base.BaseActivity
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.ActivityMainBinding
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class MainActivity : BaseActivity(),
    PaymentResultWithDataListener,
    ExternalWalletListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CommonViewModel
    lateinit var bottomNavView: BottomNavigationView
    lateinit var llHeader: LinearLayout
    lateinit var llDashboard: LinearLayout
    lateinit var llMyOrder: LinearLayout
    lateinit var llCategory: LinearLayout
    lateinit var llAccount: LinearLayout
    lateinit var llFavourite: LinearLayout
    lateinit var llDeliveryPartner: LinearLayout
    lateinit var llPayment: LinearLayout
    lateinit var llReview: LinearLayout
    lateinit var llNotification: LinearLayout
    lateinit var tvUserName: TextView
    lateinit var tvNavUserNo: TextView
    lateinit var logout: LinearLayout
    lateinit var llVersion: LinearLayout
    lateinit var drawerLayout: DrawerLayout



    override fun resourceLayout(): Int {
        return R.layout.activity_main
    }

    companion object{
        var payment = ""
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityMainBinding
    }

    override fun setFunction() {

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        llHeader = findViewById(R.id.llHeader)
        llDashboard = findViewById(R.id.llDashboard)
        llMyOrder = findViewById(R.id.llMyOrder)
        llCategory = findViewById(R.id.llCategory)
        llAccount = findViewById(R.id.llAccount)
        llFavourite = findViewById(R.id.llFavourite)
        llDeliveryPartner = findViewById(R.id.llDeliveryPartner)
        llPayment = findViewById(R.id.llPayment)
        llReview = findViewById(R.id.llReview)
        llNotification = findViewById(R.id.llNotification)
        logout = findViewById(R.id.logout)
        llVersion = findViewById(R.id.llVersion)
        tvUserName = findViewById(R.id.tvUserName)
        tvNavUserNo = findViewById(R.id.tvNavUserNo)

        val navController = findNavController(R.id.nav_host)
        drawerLayout = binding.drawerLayout

        llHeader.setOnClickListener{
            return@setOnClickListener
        }

        llVersion.setOnClickListener{
            return@setOnClickListener
        }

        llDashboard.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigateUp()
            navController.navigate(R.id.nav_home)
            bottomNavView.selectedItemId = R.id.nav_home
        }

        llMyOrder.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigateUp()
            navController.navigate(R.id.nav_my_order)
        }

        llAccount.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigateUp()
            navController.navigate(R.id.nav_account)
            bottomNavView.selectedItemId = R.id.nav_account
        }

        llFavourite.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigateUp()
            navController.navigate(R.id.nav_favourities)
            bottomNavView.selectedItemId = R.id.nav_favourities
        }

//        llReview.setOnClickListener {
//            drawerLayout.closeDrawer(GravityCompat.START)
//            navController.navigateUp()
//            navController.navigate(R.id.nav_reviews)
//        }

        llCategory.setOnClickListener {
            Toast.makeText(this, "Page not given", Toast.LENGTH_SHORT).show()
        }
        llDeliveryPartner.setOnClickListener {
            Toast.makeText(this, "Page not given", Toast.LENGTH_SHORT).show()
        }

        llPayment.setOnClickListener {
            Toast.makeText(this, "Page not given", Toast.LENGTH_SHORT).show()
        }

        llReview.setOnClickListener {
            Toast.makeText(this, "Page not given", Toast.LENGTH_SHORT).show()
        }
        llNotification.setOnClickListener {
            Toast.makeText(this, "Page not given", Toast.LENGTH_SHORT).show()
        }

        binding.contentMain.bottomNavigationView.visibility = View.VISIBLE
        binding.contentMain.bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED

        bottomNavView = findViewById(R.id.bottomNavigationView)
        bottomNavView.setupWithNavController(navController)

        tvUserName.text = Shared_Preferences.getName()
        tvNavUserNo.text = Shared_Preferences.getPhoneNo()

        navController.navigateUp()
        navController.navigate(R.id.nav_home)


        bottomNavView.itemIconTintList = null

        bottomNavView.menu.findItem(R.id.nav_home).setIcon(R.drawable.icon_home)

        bottomNavView.setOnItemSelectedListener {item->
            when(item.itemId){
                R.id.nav_home -> {
                    bottomIconView()
                    item.setIcon(R.drawable.icon_home)
                    navController.navigateUp()
                    navController.navigate(R.id.nav_home)
                    true
                }
                R.id.nav_favourities -> {
                    bottomIconView()
                    item.setIcon(R.drawable.icon_favourite)
                    navController.navigateUp()
                    navController.navigate(R.id.nav_favourities)
                    true
                }
                R.id.nav_cart -> {
                    bottomIconView()
                    item.setIcon(R.drawable.icon_cart)
                    navController.navigateUp()
                    navController.navigate(R.id.nav_cart)
                    true
                }
                R.id.nav_account -> {
                    bottomIconView()
                    item.setIcon(R.drawable.icon_of_user)
                    navController.navigateUp()
                    navController.navigate(R.id.nav_account)
                    true
                }
                else -> false
            }

        }

        logout.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setMessage("Do you want to logout?")
            builder.setPositiveButton(
                "Ok"
            ) { dialog, which ->
                Shared_Preferences.setLoginStatus(false)
                Shared_Preferences.clearPref()
                val intent = Intent(this, SplashActivity::class.java)
                startActivity(intent)
                finish()
                dialog.cancel()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
            val alert = builder.create()
            alert.setOnShowListener { arg0: DialogInterface? ->
                alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
                alert.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
            }
            alert.show()
        }


    }

    fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START,true)
    }

    fun dragDrawer(){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    fun bottomIconView(){
        bottomNavView.menu.findItem(R.id.nav_home).setIcon(R.drawable.ic_home)
        bottomNavView.menu.findItem(R.id.nav_favourities).setIcon(R.drawable.icon_of_favourite)
        bottomNavView.menu.findItem(R.id.nav_cart).setIcon(R.drawable.ic_cart)
        bottomNavView.menu.findItem(R.id.nav_account).setIcon(R.drawable.ic_user)
    }

    fun homeBottomIcon(){
        bottomNavView.menu.findItem(R.id.nav_home).setIcon(R.drawable.icon_home)
        bottomNavView.menu.findItem(R.id.nav_favourities).setIcon(R.drawable.icon_of_favourite)
        bottomNavView.menu.findItem(R.id.nav_cart).setIcon(R.drawable.ic_cart)
        bottomNavView.menu.findItem(R.id.nav_account).setIcon(R.drawable.ic_user)
    }

    fun favouriteBottomIcon(){
        bottomNavView.menu.findItem(R.id.nav_home).setIcon(R.drawable.ic_home)
        bottomNavView.menu.findItem(R.id.nav_favourities).setIcon(R.drawable.icon_favourite)
        bottomNavView.menu.findItem(R.id.nav_cart).setIcon(R.drawable.ic_cart)
        bottomNavView.menu.findItem(R.id.nav_account).setIcon(R.drawable.ic_user)
    }

    fun setBottomNavigationVisibility(isVisible:Boolean=false){
        if (isVisible){
            binding.contentMain.bottomNavigationView.visibility = View.VISIBLE
        }else{
            binding.contentMain.bottomNavigationView.visibility = View.GONE
        }
    }

    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE
    }

    fun startPayment(amount:String) {
        val co = Checkout()
        co.setKeyID("rzp_live_GLjfV6zvSruaZJ")
        //  co.setKeyID("rzp_test_QgKM8E1RJQWaGB")
        // co.setKeyID("rzp_live_8Tu7NHdgOVlTDY")

        val totalamount: Double = amount.toDouble() * 100
//        orderid = orderId
//        refid = refId

        try {
            val options = JSONObject()
            options.put("name","Mod Mobile Payment")
            options.put("description","Order Charges")
            options.put("image",R.mipmap.ic_launcher)
            options.put("theme.color", "#0F385A")
            options.put("currency","INR")
//            options.put("order_id", "order_DBJOWzybf0sJbb")
            options.put("amount",totalamount.toString())
            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            val prefill = JSONObject()
            prefill.put("email","gaurav.kumar@example.com")
            prefill.put("contact","9876543210")

            options.put("prefill",prefill)
            co.open(this,options)

        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }


    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {

        Toast.makeText(this, p0, Toast.LENGTH_SHORT).show()
        Log.d(ContentValues.TAG, "paymentid-->"+p1?.data)
        payment = "success"
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show()
        Log.d(ContentValues.TAG, "Error-->"+p1)

    }

    override fun onExternalWalletSelected(p0: String?, p1: PaymentData?) {
        Toast.makeText(this, p0, Toast.LENGTH_SHORT).show()

    }
}