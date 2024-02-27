package com.example.modmobilesmartsale.data.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.modmobilesmartsale.data.ApiHelper
import com.example.modmobilesmartsale.data.repository.MainRepository
import com.example.modmobilesmartsale.viewmodel.CommonViewModel


class CommonModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CommonViewModel(MainRepository(apiHelper)) as T

}