package com.example.modmobilesmartsale.data.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.repository.MainRepository
import com.example.modmobilesmartsale.viewmodel.CommonViewModel2


class CommonModelFactory2(private val apiHelper: ApiHelper) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CommonViewModel2(MainRepository(apiHelper)) as T

}