package com.example.modmobilesmartsale.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Patterns
import com.example.modmobilesmartsale.application.App


object Shared_Preferences {
    private const val PREF_IS_LANG = "lang_select"
    private const val PREF_TOKEN = "token"
    private const val PREF_IS_REMEMBERED = "forget_password"
    private const val PREF_IS_LOGIN = "logged_in"
    private const val PREF_USER_ID = "user_id"

    private const val PREF_PHONE_NO = "phone_no"
    private const val PREF_PASSWORD = "password"


    private const val PREF_USER_EMAIL = "userEmail"

    private const val PREF_USER_FIRST_NAME = "userFirstName"

    private const val IS_LOGIN = "IsLoggedIn"
    var editor: SharedPreferences.Editor?=null
    const val KEY_username = "username"
    const val KEY_password = "password"
    const val KEY_userId = "userid"




    private fun getSharedPreferences(): SharedPreferences {
        return App.getInstance()
            .getSharedPreferences(
                "APPPref",
                Context.MODE_PRIVATE
            )
    }


    fun getIsRemembered(): Boolean {
        return getSharedPreferences().getBoolean(PREF_IS_REMEMBERED, false)
    }

    fun setIsRemembered(isRemember: Boolean?) {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(PREF_IS_REMEMBERED, isRemember!!)
        editor.apply()
    }

    fun getLangStatus(): String {
        return getSharedPreferences().getString(PREF_IS_LANG, "en")!!
    }

    fun setLangStatus(lang: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_IS_LANG, lang!!)
        editor.apply()
    }


    fun getPhoneNo(): String {
        return getSharedPreferences().getString(PREF_PHONE_NO, "")!!
    }

    fun setPhoneNo(phone_no: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_PHONE_NO, phone_no!!)
        editor.apply()
    }

    fun getUserId(): String {
        return getSharedPreferences().getString(PREF_USER_ID, "0")!!
    }

    fun setUserId(userId: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_USER_ID, userId!!)
        editor.apply()
    }



    fun getSelectedclinetid(): String {
        return getSharedPreferences().getString("clientid", "")!!
    }

    fun setSelectedclinetid(clientid: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("clientid", clientid!!)
        editor.apply()
    }


    fun getSelectedclinetname(): String {
        return getSharedPreferences().getString("clientname", "")!!
    }

    fun setSelectedclinetname(clientname: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("clientname", clientname!!)
        editor.apply()
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }


    fun getLoginStatus(): Boolean? {
        return getSharedPreferences().getBoolean(PREF_IS_LOGIN, false)
    }

    fun setLoginStatus(token: Boolean?) {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(PREF_IS_LOGIN, token!!)
        editor.apply()
    }


    fun getToken(): String? {
        return getSharedPreferences().getString(PREF_TOKEN, "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2Njg3NTI5MzgsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY2ODgwNjkzOCwidXNlcklkIjoiMCJ9.Lha7L2QYnzWeaJ72w4dJtO2vWv_ol4zfVYheLTs5_S0")
    }

    fun setUserToken(token: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_TOKEN, token)
        editor.commit()
    }


    fun getLat(): String? {
        return getSharedPreferences().getString("latitude", "")
    }

    fun setLat(lat: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("latitude", lat)
        editor.apply()
    }




    fun getRegistrationType(): String? {
        return getSharedPreferences().getString("registrationtype", "")
    }

    fun setRegistrationType(registrationtype: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("registrationtype", registrationtype)
        editor.apply()
    }




    fun getMobileRegistrationNo(): String? {
        return getSharedPreferences().getString("mobileregno", "")
    }

    fun setMobileRegistrationNo(mobileregno: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("mobileregno", mobileregno)
        editor.apply()
    }




    fun getNameRegistration(): String? {
        return getSharedPreferences().getString("regname", "")
    }

    fun setNameRegistration(regname: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("regname", regname)
        editor.apply()
    }




    fun getEmailRegistration(): String? {
        return getSharedPreferences().getString("regemail", "")
    }

    fun setEmailRegistration(regemail: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("regemail", regemail)
        editor.apply()
    }



    fun getZoneRegistration(): String? {
        return getSharedPreferences().getString("regzone", "")
    }

    fun setZoneRegistration(regzone: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("regzone", regzone)
        editor.apply()
    }



    fun getPin(): String? {
        return getSharedPreferences().getString("regpin", "")
    }

    fun setPin(regpin: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("regpin", regpin)
        editor.apply()
    }



    fun getAddress1(): String? {
        return getSharedPreferences().getString("address1", "")
    }

    fun setAddress1(address1: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("address1", address1)
        editor.apply()
    }




    fun getUserType(): String? {
        return getSharedPreferences().getString("usertype", "")
    }

    fun setUserType(usertype: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("usertype", usertype)
        editor.apply()
    }



    fun getLong(): String? {
        return getSharedPreferences().getString("longitude", "")
    }

    fun setLong(long: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("longitude", long)
        editor.apply()
    }


    fun getUrcid(): String? {
        return getSharedPreferences().getString("urcid", "")
    }

    fun setUrcid(urcid: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("urcid", urcid)
        editor.apply()
    }


    fun getShippingAddress(): String? {
        return getSharedPreferences().getString("shippingaddress", "")
    }

    fun setShippingAddress(shippingaddress: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("shippingaddress", shippingaddress)
        editor.apply()
    }



    fun getShippingCity(): String? {
        return getSharedPreferences().getString("shippingcity", "")
    }

    fun setShippingCity(shippingcity: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("shippingcity", shippingcity)
        editor.apply()
    }




    fun getShippingState(): String? {
        return getSharedPreferences().getString("shippingstate", "")
    }


    fun setShippingState(shippingstate: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("shippingstate", shippingstate)
        editor.apply()
    }





    fun getShippingZip(): String? {
        return getSharedPreferences().getString("shippingzip", "")
    }


    fun setShippingZip(shippingzip: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("shippingzip", shippingzip)
        editor.apply()
    }



    fun getSelectedPickupdate(): String? {
        return getSharedPreferences().getString("pickupdate", "")
    }

    fun setSelectedPickupdate(pickupdate: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("pickupdate", pickupdate)
        editor.apply()
    }


    fun getSelectedPickuptime(): String? {
        return getSharedPreferences().getString("pickuptime", "")
    }

    fun setSelectedPickuptime(pickuptime: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("pickuptime", pickuptime)
        editor.apply()
    }




    fun getSelectedDeliverydate(): String? {
        return getSharedPreferences().getString("deliverydate", "")
    }

    fun setSelectedDeliverydate(deliverydate: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("deliverydate", deliverydate)
        editor.apply()
    }


    fun getSelectedDeliverytime(): String? {
        return getSharedPreferences().getString("deliverytime", "")
    }

    fun setSelectedDeliverytime(deliverytime: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("deliverytime", deliverytime)
        editor.apply()
    }






    fun getName(): String? {
        return getSharedPreferences().getString(PREF_USER_FIRST_NAME, "")
    }

    fun setName(firstName: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_USER_FIRST_NAME, firstName)
        editor.apply()
    }

    fun getEmail(): String? {
        return getSharedPreferences().getString(PREF_USER_EMAIL, "")
    }

    fun setEmail(email: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_USER_EMAIL, email)
        editor.apply()
    }


    fun getPassword(): String? {
        return getSharedPreferences().getString(PREF_PASSWORD, "")
    }

    fun setPassword(password: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_PASSWORD, password)
        editor.apply()
    }


    fun clearPref() {
        val editor = getSharedPreferences().edit()
        editor.clear()
        editor.apply()
    }


    // Get Login State
    val isLoggedIn: Boolean
        get() = getSharedPreferences().getBoolean(IS_LOGIN, false)


    fun logoutUser() {
        // Clearing all data from Shared Preferences
        editor?.clear()
        editor?.commit()

    }

    fun createLoginSession(username: String?, password: String?) {
        editor?.putBoolean(IS_LOGIN, true)
        editor?.putString(KEY_username, username)
        editor?.putString(KEY_password, password)
        editor?.commit()
    }

}