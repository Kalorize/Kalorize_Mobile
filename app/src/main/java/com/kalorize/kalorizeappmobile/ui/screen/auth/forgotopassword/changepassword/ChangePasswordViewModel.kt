package com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalorize.kalorizeappmobile.data.remote.ApiRepository
import com.kalorize.kalorizeappmobile.data.remote.body.UpdatePassBody
import com.kalorize.kalorizeappmobile.data.remote.body.ValidatingOtpBody
import com.kalorize.kalorizeappmobile.data.remote.response.SimpleResponse
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val apiRepository: ApiRepository):ViewModel() {
    private val _updatePasswordResponse = MutableLiveData<SimpleResponse>()
    var updatePasswordResponse : LiveData<SimpleResponse> = _updatePasswordResponse

    fun doUpdatePassword(body:UpdatePassBody){
        viewModelScope.launch {
            apiRepository.rePassword(body)
                .onSuccess {
                    _updatePasswordResponse.postValue(it)
                }
                .onFailure {
                    _updatePasswordResponse.postValue(
                        SimpleResponse(
                            status = it.message!!,
                            message = "Password failed to change"
                        )
                    )
                }
        }
    }
}