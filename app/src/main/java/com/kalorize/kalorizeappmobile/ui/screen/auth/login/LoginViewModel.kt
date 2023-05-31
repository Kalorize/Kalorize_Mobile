package com.kalorize.kalorizeappmobile.ui.screen.auth.login

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalorize.kalorizeappmobile.data.remote.ApiRepository
import com.kalorize.kalorizeappmobile.data.remote.body.LoginBody
import com.kalorize.kalorizeappmobile.data.remote.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val apiRepository: ApiRepository): ViewModel() {
    private val _login = MutableLiveData<LoginResponse>()
    var login: LiveData<LoginResponse> = _login

    fun doLogin(body: LoginBody) {
        viewModelScope.launch {
            apiRepository.login(body).onSuccess {
                _login.postValue(it)
            }
                .onFailure {

                }
        }
    }
}