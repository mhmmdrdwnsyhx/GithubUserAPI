package com.example.submissionfundaawal.detail.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissionfundaawal.model.MainViewModel

class FavFactory constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavFactory? = null
        @JvmStatic
        fun getInstance(application: Application): FavFactory {
            if (INSTANCE == null) {
                synchronized(FavFactory::class.java) {
                    INSTANCE = FavFactory(application)
                }
            }
            return INSTANCE as FavFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <X : ViewModel> create(modelClass: Class<X>): X {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApplication) as X
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApplication) as X
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}