package com.example.modmobilesmartsale.data

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


class ApiHelper(private val apiInterface: ApiInterface) {

    suspend fun login(requestBody: LoginRequestModel) = apiInterface.login(requestBody)

    suspend fun signup(requestBody: SignupRequest) = apiInterface.signup(requestBody)

    suspend fun dashboard(requestBody: DashboardRequest) = apiInterface.dashboard(requestBody)

    suspend fun getcolorstorage(requestBody: StorageColorRequest) = apiInterface.getcolorstorage(requestBody)

    suspend fun getstock(requestBody: StockRequest) = apiInterface.getstock(requestBody)

    suspend fun orderlist(requestBody: OrderListRequest) = apiInterface.orderlist(requestBody)

    suspend fun checkpincode(requestBody: PincodeRequest) = apiInterface.checkpincode(requestBody)

    suspend fun getproductimage(requestBody: GetProductImageRequest) = apiInterface.getproductimage(requestBody)

    suspend fun generatetoken(requestBody: TokenRequest) = apiInterface.generatetoken(requestBody)

//    suspend fun sentotp(requestBody: SentOtpRequest) = apiInterface.sentotp(requestBody)

//    suspend fun otpvalidate(requestBody: OtpValidationRequest) = apiInterface.otpvalidate(requestBody)
    suspend fun otpvalidate(otp:String, mobile: String) = apiInterface.otpvalidate(otp, mobile)

    suspend fun statemaster(requestBody: StateListRequest) = apiInterface.statemaster(requestBody)

    suspend fun orderdetails(requestBody: OrderRequest) = apiInterface.orderdetails(requestBody)

    suspend fun viewaddress(requestBody: ViewAddressRequest) = apiInterface.viewaddress(requestBody)

    suspend fun mandageaddress(requestBody: ManageAddressRequest) = apiInterface.manageaddress(requestBody)

    suspend fun defaultaddress(requestBody: DefaultAddressRequest) = apiInterface.defaultaddress(requestBody)

    suspend fun getfavouriteitems(requestBody: FavListRequest) = apiInterface.getfavouriteitems(requestBody)

    suspend fun addfav(requestBody: FavAddRemoveRequest) = apiInterface.addfav(requestBody)


    suspend fun addtocart(requestBody: AddtoCartRequest) = apiInterface.addtocart(requestBody)

    suspend fun pocreate(requestBody: POcreateRequest) = apiInterface.pocreate(requestBody)

    suspend fun invoicecreate(requestBody: InvoicecreateRequest) = apiInterface.invoicecreate(requestBody)


    suspend fun invoicecancel(requestBody: InvoiceCancelRequest) = apiInterface.invoicecancel(requestBody)


    suspend fun getreview(requestBody: ReviewlistRequest) = apiInterface.getreview(requestBody)

    suspend fun postreview(requestBody: PostReviewRequest) = apiInterface.postreview(requestBody)


    suspend fun couponmaster(requestBody: CouponRequest) = apiInterface.couponmaster(requestBody)


    suspend fun successmessage(requestBody: RegistrationSuccessRequest) = apiInterface.successmessage(requestBody)

    suspend fun sentotp(template_id:String, mobile: String) = apiInterface.sentotp(template_id, mobile)


    suspend fun QClist(requestBody: QCRequest) = apiInterface.QClist(requestBody)


    suspend fun resendotp(retrytype:String, mobile: String) = apiInterface.resendotp(retrytype, mobile)

    suspend fun viewcart(requestBody: ViewCartRequest) = apiInterface.viewcart(requestBody)

    suspend fun validateOTP(token: RequestBody, otpJSON: RequestBody) =
        apiInterface.validateOTP(token, otpJSON)

}