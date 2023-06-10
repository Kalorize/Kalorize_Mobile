package com.kalorize.kalorizeappmobile.ui.screen.feature.questionnaire

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalorize.kalorizeappmobile.data.remote.ApiRepository
import com.kalorize.kalorizeappmobile.data.remote.response.RecommendationResponse
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class QuestionnaireViewModel(private val apiRepository: ApiRepository) : ViewModel() {
    private val _foodRec = MutableLiveData<RecommendationResponse>()
    var foodRec: LiveData<RecommendationResponse> = _foodRec

    fun doFoodRec(
        token: String,
        gender: RequestBody,
        age: Float,
        height: Float,
        weight: Float,
        activity: RequestBody,
        target: RequestBody,
    ) {
        viewModelScope.launch {
            apiRepository.foodRec(token, gender, age, height, weight, activity, target)
                .onSuccess {
                    _foodRec.postValue(it)
                }
                .onFailure {
                    _foodRec.postValue(
                        RecommendationResponse(
                            status = it.message!!,
                            data = null
                        )
                    )
                }
        }
    }
}