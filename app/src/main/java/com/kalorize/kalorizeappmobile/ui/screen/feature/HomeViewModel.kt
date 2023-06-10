package com.kalorize.kalorizeappmobile.ui.screen.feature

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalorize.kalorizeappmobile.data.remote.ApiRepository
import com.kalorize.kalorizeappmobile.data.remote.body.Breakfast
import com.kalorize.kalorizeappmobile.data.remote.body.ChooseFoodBody
import com.kalorize.kalorizeappmobile.data.remote.body.Dinner
import com.kalorize.kalorizeappmobile.data.remote.body.Lunch
import com.kalorize.kalorizeappmobile.data.remote.response.FoodDetailResponse
import com.kalorize.kalorizeappmobile.data.remote.response.PastRecommendation
import com.kalorize.kalorizeappmobile.data.remote.response.RecommendationHistoryResponse
import com.kalorize.kalorizeappmobile.data.remote.response.RecommendationResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val apiRepository: ApiRepository): ViewModel() {
    // To get recommendation history by date
    private val _history = MutableLiveData<RecommendationHistoryResponse>()
    var history: LiveData<RecommendationHistoryResponse> = _history

    fun getHistory(token: String , date: String){
        viewModelScope.launch {
            apiRepository.getRecommendationHistory(token , date)
                .onSuccess {
                    _history.postValue(it)
                }
                .onFailure {
                    _history.postValue(
                        RecommendationHistoryResponse(
                            status = it.message!!,
                            pastRecommendation = null
                        )
                    )
                }
        }
    }

    // to get food recommendation
    private val _recommendation = MutableLiveData<RecommendationResponse>()
    var recommendation: LiveData<RecommendationResponse> = _recommendation

    fun getRecommendation(token: String){
        viewModelScope.launch {
            apiRepository.getRecommendation(token)
                .onSuccess {
                    _recommendation.postValue(it)
                }
                .onFailure {
                    _recommendation.postValue(
                        RecommendationResponse(
                            status = it.message!!,
                            data = null
                        )
                    )
                }
        }
    }

    //to post food choice
    fun chooseFood(token: String ,date: String , breakfastId: Int, lunchId: Int , dinnerId: Int ){
        viewModelScope.launch {
            apiRepository.chooseFood(token , ChooseFoodBody(
                date,
                breakfast = Breakfast(breakfastId),
                lunch = Lunch(lunchId),
                dinner = Dinner(dinnerId)
            ))
                .onSuccess {
                    clearViewModel()
                }
                .onFailure {
                    Log.d("Check failed" , it.toString())
                }
        }
    }

    private fun clearViewModel(){
        viewModelScope.launch {
            _history.postValue(
                RecommendationHistoryResponse(
                    status = "",
                    pastRecommendation = null
                )
            )
        }
    }

    // Food detail viewmodel
    private val _foodDetail = MutableLiveData<FoodDetailResponse>()
    var foodDetail: LiveData<FoodDetailResponse> = _foodDetail

    fun getFoodDetail(token: String , id: String){
        viewModelScope.launch {
            apiRepository.getFoodDetail(token, id)
                .onSuccess {
                    _foodDetail.postValue(it)
                }
                .onFailure {
                    _foodDetail.postValue(
                        FoodDetailResponse(status = "Failed" , null)
                    )
                }
        }
    }
}