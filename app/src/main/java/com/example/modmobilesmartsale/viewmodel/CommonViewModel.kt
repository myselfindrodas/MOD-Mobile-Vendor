package com.example.modmobilesmartsale.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.modmobilesmartsale.data.Resource
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
import com.example.modmobilesmartsale.data.model.reviewlistmodel.ReviewlistRequest
import com.example.modmobilesmartsale.data.model.signupmodel.SignupRequest
import com.example.modmobilesmartsale.data.model.statemodel.StateListRequest
import com.example.modmobilesmartsale.data.model.stockmodel.StockRequest
import com.example.modmobilesmartsale.data.model.storagecolormodel.StorageColorRequest
import com.example.modmobilesmartsale.data.model.tokenmodel.TokenRequest
import com.example.modmobilesmartsale.data.model.viewaddressmodel.ViewAddressRequest
import com.example.modmobilesmartsale.data.model.viewcartmodel.ViewCartRequest
import com.example.modmobilesmartsale.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers

class CommonViewModel(private val mainRepository: MainRepository) : ViewModel() {

    fun login(requestBody: LoginRequestModel) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.login(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }


    fun signup(requestBody: SignupRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.signup(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }



    fun dashboard(requestBody: DashboardRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.dashboard(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }



    fun getcolorstorage(requestBody: StorageColorRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.getcolorstorage(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }



    fun getstock(requestBody: StockRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.getstock(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }



    fun orderlist(requestBody: OrderListRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.orderlist(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }




    fun checkpincode(requestBody: PincodeRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.checkpincode(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }







    fun getproductimage(requestBody: GetProductImageRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.getproductimage(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }


    fun generatetoken(requestBody: TokenRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.generatetoken(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }


//    fun sentotp(requestBody: SentOtpRequest) =
//        liveData(Dispatchers.IO) {
//            emit(Resource.loading(data = null))
//
//            try {
//                emit(Resource.success(data = mainRepository.sentotp(requestBody)))
//            } catch (e: Exception) {
//                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//            }
//        }


//    fun otpvalidate(requestBody: OtpValidationRequest) =
//        liveData(Dispatchers.IO) {
//            emit(Resource.loading(data = null))
//
//            try {
//                emit(Resource.success(data = mainRepository.otpvalidate(requestBody)))
//            } catch (e: Exception) {
//                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//            }
//        }


    fun statemaster(requestBody: StateListRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.statemaster(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }




    fun orderdetails(requestBody: OrderRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.orderdetails(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }




    fun viewaddress(requestBody: ViewAddressRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.viewaddress(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }




    fun manageaddress(requestBody: ManageAddressRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.manageaddress(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }




    fun defaultaddress(requestBody: DefaultAddressRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.defaultaddress(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }




    fun getfavouriteitems(requestBody: FavListRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.getfavouriteitems(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }



    fun addfav(requestBody: FavAddRemoveRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.addfav(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }



    fun addtocart(requestBody: AddtoCartRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.addtocart(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }




    fun pocreate(requestBody: POcreateRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.pocreate(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }



    fun invoicecreate(requestBody: InvoicecreateRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.invoicecreate(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }




    fun invoicecancel(requestBody: InvoiceCancelRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.invoicecancel(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }





    fun couponmaster(requestBody: CouponRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.couponmaster(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }



    fun getreview(requestBody: ReviewlistRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.getreview(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }




    fun postreview(requestBody: PostReviewRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.postreview(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }


    fun viewcart(requestBody: ViewCartRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.viewcart(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }





    fun QClist(requestBody: QCRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.QClist(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }


//    fun registrationsuccess(requestBody: RegistrationSuccessRequest) =
//        liveData(Dispatchers.IO) {
//            emit(Resource.loading(data = null))
//
//            try {
//                emit(Resource.success(data = mainRepository.registrationsuccess(requestBody)))
//            } catch (e: Exception) {
//                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//            }
//        }

//    fun validateOTP(token: RequestBody, otpJSON: RequestBody) =
//        liveData(Dispatchers.IO) {
//            emit(Resource.loading(data = null))
//
//            try {
//                emit(Resource.success(data = mainRepository.validateOTP(token, otpJSON)))
//            } catch (e: Exception) {
//                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//            }
//        }


}