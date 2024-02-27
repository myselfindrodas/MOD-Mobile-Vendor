package com.example.modmobilesmartsale.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.modmobilesmartsale.data.Resource
import com.example.modmobilesmartsale.data.model.qclistmodel.QCRequest
import com.example.modmobilesmartsale.data.model.registrationsuccessmodel.RegistrationSuccessRequest
import com.example.modmobilesmartsale.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers

class CommonViewModel2(private val mainRepository: MainRepository) : ViewModel() {


    fun successmessage(requestBody: RegistrationSuccessRequest) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.successmessage(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }



    fun sentotp(template_id:String, mobile: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.sentotp(template_id, mobile)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }



    fun otpvalidate(otp:String, mobile: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.otpvalidate(otp, mobile)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }




    fun resendotp(retrytype:String, mobile: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.resendotp(retrytype, mobile)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }






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