package com.kalorize.kalorizeappmobile.ui.screen.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalorize.kalorizeappmobile.data.remote.ApiRepository
import com.kalorize.kalorizeappmobile.data.remote.body.LoginBody
import com.kalorize.kalorizeappmobile.data.remote.response.LoginData
import com.kalorize.kalorizeappmobile.data.remote.response.LoginResponse
import com.kalorize.kalorizeappmobile.data.remote.response.LoginUser
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
                    _login.postValue(
                        LoginResponse(
                        status = it.message!!,
                        data = LoginData(
                            token = "",
                            user = LoginUser(
                                id = -1,
                                email = "",
                                password = "",
                                name = ""
                            )
                        )
                    )
                    )
                }
        }
    }

    fun cleanLogin(){
        _login.postValue(
            LoginResponse(
                status = "",
                data = LoginData(
                    token = "",
                    user = LoginUser(
                        id = -1,
                        email = "",
                        password = "",
                        name = ""
                    )
                )
        ))
    }
}