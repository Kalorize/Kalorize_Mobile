package com.kalorize.kalorizeappmobile.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kalorize.kalorizeappmobile.di.Injection

@Suppress("UNCHECKED_CAST")
class MainModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(Injection.provideRepository(context)) as T
        }
        throw java.lang.IllegalArgumentException("Unknown View Model Class")
    }
}