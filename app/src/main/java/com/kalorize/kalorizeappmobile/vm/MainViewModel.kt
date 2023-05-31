package com.kalorize.kalorizeappmobile.vm

import androidx.lifecycle.ViewModel
import com.kalorize.kalorizeappmobile.ui.screen.auth.login.LoginViewModel
import com.kalorize.kalorizeappmobile.data.remote.ApiRepository

class MainViewModel(private val apiRepository: ApiRepository): ViewModel() {
    val loginViewModel = LoginViewModel(this.apiRepository)
}