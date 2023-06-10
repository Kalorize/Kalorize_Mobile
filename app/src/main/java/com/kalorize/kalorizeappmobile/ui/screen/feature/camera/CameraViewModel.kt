package com.kalorize.kalorizeappmobile.ui.screen.feature.camera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalorize.kalorizeappmobile.data.remote.ApiRepository
import com.kalorize.kalorizeappmobile.data.remote.response.F2hwgResponse
import com.kalorize.kalorizeappmobile.data.remote.response.SimpleResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class CameraViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    private val _predictHwg = MutableLiveData<F2hwgResponse>()
    var predictHwg: LiveData<F2hwgResponse> = _predictHwg

    fun predictHwg(token: String, file: MultipartBody.Part) {
        Log.i("file path predictHwg", file.body.toString())
        viewModelScope.launch {
            apiRepository.f2hwg(token, file)
                .onSuccess {
                    _predictHwg.postValue(it)
                }
                .onFailure {
                    _predictHwg.postValue(
                        F2hwgResponse(
                            height = 0,
                            weight = 0,
                            gender = "Gagal memprediksi"
                        )
                    )
                }
        }
    }
}