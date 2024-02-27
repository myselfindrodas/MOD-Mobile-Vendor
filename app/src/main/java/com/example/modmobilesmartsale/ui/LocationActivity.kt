package com.example.modmobilesmartsale.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.databinding.ViewDataBinding
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.modmobilesmartsale.R
import com.example.modmobilesmartsale.base.BaseActivity
import com.example.modmobilesmartsale.data.ApiClient
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.modelfactory.CommonModelFactory
import com.example.modmobilesmartsale.databinding.ActivityLocationBinding
import com.example.modmobilesmartsale.utils.Constants.TAG
import com.example.modmobilesmartsale.utils.Shared_Preferences
import com.example.modmobilesmartsale.utils.Utilities
import com.example.modmobilesmartsale.viewmodel.CommonViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import org.json.JSONObject
import java.net.URL

class LocationActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityLocationBinding
    private lateinit var viewModel: CommonViewModel
    lateinit var mMap: GoogleMap
    var latitude: String? = ""
    var longitude: String? = ""
    private val pERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var mapFragment: SupportMapFragment
    var currentLocation: LatLng = LatLng(0.0, 0.0)
    var fulladdress = ""


    override fun resourceLayout(): Int {
        return R.layout.activity_location
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityLocationBinding
    }

    override fun setFunction() {
        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        binding.topBarLocation.tvTopBar.setText("Back")

        binding.topBarLocation.ivBack.setOnClickListener {
            onBackPressed()
        }

        if (!Places.isInitialized()) {
            Places.initialize(this, getString(R.string.google_maps_key))
        }

//        this.showProgressDialog()
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.tvTypeManually.setOnClickListener {
            openSearchBar()
        }

        binding.llCurrentLocation.setOnClickListener {
            getLastLocation()
        }

        binding.btnNextInLocation.setOnClickListener {
            if (fulladdress.isEmpty()){
                Toast.makeText(this, "Please Select Location", Toast.LENGTH_LONG).show()
            }else{
                val intent = Intent(this, UploadDocumentActivity::class.java)
                startActivity(intent)
            }

        }
    }


    private fun openSearchBar() {
        val fields = listOf(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(this)
        resolutionForPlaceResult.launch(intent)
    }

    private val resolutionForPlaceResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {

                val place = Autocomplete.getPlaceFromIntent(activityResult.data!!)
                val address = place.address

                val lati = place.latLng!!.latitude.toString() + ""
                val longi = place.latLng!!.longitude.toString() + ""
                latitude = lati
                longitude = longi

                currentLocation = LatLng(lati.toDouble(),longi.toDouble())
                reverseGeocoding(latitude.toString(), longitude.toString())

                mapFragment.getMapAsync {
                    mMap = it
                    mMap.clear()
                    mMap.addMarker(MarkerOptions().position(currentLocation).title("My Location"))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))

                }
            } else if (activityResult.resultCode == AutocompleteActivity.RESULT_ERROR) {
//                this.showProgressDialog()
            } else if (activityResult.resultCode == RESULT_CANCELED) {
            }
        }

    private fun getLastLocation(): LatLng {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        mMap.clear()
                        mMap.addMarker(MarkerOptions().position(currentLocation).title("My Location"))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))
                        reverseGeocoding(location.latitude.toString(), location.longitude.toString())

                    }
                }
            }
            else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
            }
        }
        else {
            requestPermissions()
        }
        return currentLocation
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 1000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions()
        }
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }


    private fun reverseGeocoding(lat: String, long: String) {

        if (!Utilities.isNetworkAvailable(this)) {
            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
            return
        }
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://maps.googleapis.com/maps/api/geocode/json?" + "latlng=$lat,$long&key=AIzaSyCGRQavtVfIlnBuSkELe98R2MFjXQdnLRc",
            null,
            { response ->
                val resultsArray = response.getJSONArray("results")
                val resultsObj = resultsArray.getJSONObject(0)
                val formattedAddress = resultsObj.getString("formatted_address")
                val address_components = resultsObj.getJSONArray("address_components")
                val zipcodeJson = address_components.getJSONObject(address_components.length()-1)
                val zipcode=zipcodeJson.getString("long_name")

                Shared_Preferences.setPin(zipcode)
                Shared_Preferences.setAddress1(formattedAddress)

                fulladdress = formattedAddress

                Log.d(TAG, "fulladdress-->"+formattedAddress)

                // Start the AddAddressActivity activity and pass the reverse geocoded address components as extras.
//                val intent = Intent(this, AddAddressActivity::class.java)
//                    .putExtra("buildingNo", buildingNo)
//                    .putExtra("street", street)
//                    .putExtra("area", area)
//                    .putExtra("city", city)
//                    .putExtra("district", district)
//                    .putExtra("state", state)
//                    .putExtra("zipcode", zipcode)
//                startActivity(intent)
            },
            { error ->
                Toast.makeText(this, "Failed to reverse geocode the address", Toast.LENGTH_SHORT).show()
            }
        )

        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location? = locationResult.lastLocation
            currentLocation = LatLng(mLastLocation!!.latitude, mLastLocation.longitude)
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ), pERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == pERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
//        this.hideProgressDialog()
        getLastLocation()
    }
}