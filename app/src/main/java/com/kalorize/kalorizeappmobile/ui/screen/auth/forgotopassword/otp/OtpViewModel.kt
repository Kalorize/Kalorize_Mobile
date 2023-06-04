package com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalorize.kalorizeappmobile.data.remote.ApiRepository
import com.kalorize.kalorizeappmobile.data.remote.body.RequestOtpBody
import com.kalorize.kalorizeappmobile.data.remote.body.ValidatingOtpBody
import com.kalorize.kalorizeappmobile.data.remote.response.SimpleResponse
import kotlinx.coroutines.launch

class OtpViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    private val _validateOtpResponse = MutableLiveData<SimpleResponse>()
    var validateOtpResponse : LiveData<SimpleResponse> = _validateOtpResponse

    private val _otpResponse = MutableLiveData<SimpleResponse>()
    var otpResponse :LiveData<SimpleResponse> = _otpResponse

    fun doValidateOtp(body: ValidatingOtpBody){
        viewModelScope.launch {
            apiRepository.validateOtp(body)
                .onSuccess {
                    _validateOtpResponse.postValue(it)
                }
                .onFailure {
                    _validateOtpResponse.postValue(
                        SimpleResponse(
                            status = it.message!!,
                            message = "wrong code"
                        )
                    )
                }
        }
    }

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