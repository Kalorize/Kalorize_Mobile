package com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.reinputemail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalorize.kalorizeappmobile.data.remote.ApiRepository
import com.kalorize.kalorizeappmobile.data.remote.body.RequestOtpBody
import com.kalorize.kalorizeappmobile.data.remote.response.SimpleResponse
import kotlinx.coroutines.launch


class ReInputEmailViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    private val _otpResponse = MutableLiveData<SimpleResponse>()
    var otpResponse :LiveData<SimpleResponse> = _otpResponse

    fun doRequestOtp(body: RequestOtpBody){
        viewModelScope.launch {
            apiRepository.reqOtp(body).onSuccess {
                _otpResponse.postValue(it)
            }
                .onFailure {
                    _otpResponse.postValue(
                        SimpleResponse(
                            status = it.message!!,
                            message = "user not found"
                        )
                    )
                }
        }
    }
}