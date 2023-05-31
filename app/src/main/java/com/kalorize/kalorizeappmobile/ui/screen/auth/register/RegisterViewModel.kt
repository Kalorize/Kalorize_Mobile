package com.kalorize.kalorizeappmobile.ui.screen.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalorize.kalorizeappmobile.data.remote.ApiRepository
import com.kalorize.kalorizeappmobile.data.remote.body.RegisterBody
import com.kalorize.kalorizeappmobile.data.remote.response.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    private val _register = MutableLiveData<RegisterResponse>()
    var register: LiveData<RegisterResponse> = _register

    fun doRegister(body: RegisterBody){
        viewModelScope.launch {
            _register.postValue(apiRepository.register(body))
        }
    }
}