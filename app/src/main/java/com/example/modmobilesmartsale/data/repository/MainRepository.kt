package com.example.modmobilesmartsale.data.repository

import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.model.addtocartmodel.AddtoCartRequest
import com.example.modmobilesmartsale.data.model.couponmastermodel.CouponRequest
import com.example.modmobilesmartsale.data.model.dashboardmodel.DashboardRequest
import com.example.modmobilesmartsale.data.model.defaultaddressmodel.DefaultAddressRequest
import com.example.modmobilesmartsale.data.model.favouritemodel.FavAddRemoveRequest
import com.example.modmobilesmartsale.data.model.getfavouritemodel.FavListRequest
import com.example.modmobilesmartsale.data.model.getimagemodel.GetProductImageRequest
import com.example.modmobilesmartsale.data.model.invoicecancelmodel.InvoiceCancelRequest
import com.example.modmobilesmartsale.data.model.invoicecreatemodel.InvoicecreateRequest
import com.example.modmobilesmartsale.data.model.login_model.LoginRequestModel
import com.example.modmobilesmartsale.data.model.manageaddressmodel.ManageAddressRequest
import com.example.modmobilesmartsale.data.model.myoderlistmodel.OrderListRequest
import com.example.modmobilesmartsale.data.model.orderlistdetailsmodel.OrderRequest
import com.example.modmobilesmartsale.data.model.pincodemodel.PincodeRequest
import com.example.modmobilesmartsale.data.model.pocreatemodel.POcreateRequest
import com.example.modmobilesmartsale.data.model.postreviewmodel.PostReviewRequest
import com.example.modmobilesmartsale.data.model.qclistmodel.QCRequest
import com.example.modmobilesmartsale.data.model.registrationsuccessmodel.RegistrationSuccessRequest
import com.example.modmobilesmartsale.data.model.reviewlistmodel.ReviewlistRequest
import com.example.modmobilesmartsale.data.model.signupmodel.SignupRequest
import com.example.modmobilesmartsale.data.model.statemodel.StateListRequest
import com.example.modmobilesmartsale.data.model.stockmodel.StockRequest
import com.example.modmobilesmartsale.data.model.storagecolormodel.StorageColorRequest
import com.example.modmobilesmartsale.data.model.tokenmodel.TokenRequest
import com.example.modmobilesmartsale.data.model.viewaddressmodel.ViewAddressRequest
import com.example.modmobilesmartsale.data.model.viewcartmodel.ViewCartRequest
import okhttp3.RequestBody

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun login(requestBody: LoginRequestModel) = apiHelper.login(requestBody)

    suspend fun signup(requestBody: SignupRequest) = apiHelper.signup(requestBody)

    suspend fun dashboard(requestBody: DashboardRequest) = apiHelper.dashboard(requestBody)

    suspend fun getcolorstorage(requestBody: StorageColorRequest) =
        apiHelper.getcolorstorage(requestBody)


    suspend fun getstock(requestBody: StockRequest) = apiHelper.getstock(requestBody)

    suspend fun orderlist(requestBody: OrderListRequest) = apiHelper.orderlist(requestBody)


    suspend fun checkpincode(requestBody: PincodeRequest) = apiHelper.checkpincode(requestBody)


    suspend fun getproductimage(requestBody: GetProductImageRequest) =
        apiHelper.getproductimage(requestBody)


    suspend fun generatetoken(requestBody: TokenRequest) = apiHelper.generatetoken(requestBody)


//    suspend fun sentotp(requestBody: SentOtpRequest) = apiHelper.sentotp(requestBody)

    //    suspend fun otpvalidate(requestBody: OtpValidationRequest) = apiHelper.otpvalidate(requestBody)
    suspend fun otpvalidate(otp: String, mobile: String) = apiHelper.otpvalidate(otp, mobile)

    suspend fun statemaster(requestBody: StateListRequest) = apiHelper.statemaster(requestBody)

    suspend fun orderdetails(requestBody: OrderRequest) = apiHelper.orderdetails(requestBody)

    suspend fun viewaddress(requestBody: ViewAddressRequest) = apiHelper.viewaddress(requestBody)

    suspend fun manageaddress(requestBody: ManageAddressRequest) =
        apiHelper.mandageaddress(requestBody)

    suspend fun defaultaddress(requestBody: DefaultAddressRequest) =
        apiHelper.defaultaddress(requestBody)

    suspend fun getfavouriteitems(requestBody: FavListRequest) =
        apiHelper.getfavouriteitems(requestBody)


    suspend fun addfav(requestBody: FavAddRemoveRequest) = apiHelper.addfav(requestBody)

    suspend fun addtocart(requestBody: AddtoCartRequest) = apiHelper.addtocart(requestBody)

    suspend fun pocreate(requestBody: POcreateRequest) = apiHelper.pocreate(requestBody)

    suspend fun invoicecreate(requestBody: InvoicecreateRequest) =
        apiHelper.invoicecreate(requestBody)

    suspend fun invoicecancel(requestBody: InvoiceCancelRequest) =
        apiHelper.invoicecancel(requestBody)

    suspend fun couponmaster(requestBody: CouponRequest) = apiHelper.couponmaster(requestBody)


    suspend fun getreview(requestBody: ReviewlistRequest) = apiHelper.getreview(requestBody)

    suspend fun postreview(requestBody: PostReviewRequest) = apiHelper.postreview(requestBody)

    suspend fun viewcart(requestBody: ViewCartRequest) = apiHelper.viewcart(requestBody)

    suspend fun successmessage(requestBody: RegistrationSuccessRequest) =
        apiHelper.successmessage(requestBody)

    suspend fun sentotp(template_id: String, mobile: String) =
        apiHelper.sentotp(template_id, mobile)


    suspend fun resendotp(retrytype:String, mobile: String) = apiHelper.resendotp(retrytype, mobile)

    suspend fun QClist(requestBody: QCRequest) = apiHelper.QClist(requestBody)


    suspend fun validateOTP(token: RequestBody, otpJSON: RequestBody) =
        apiHelper.validateOTP(token, otpJSON)

}