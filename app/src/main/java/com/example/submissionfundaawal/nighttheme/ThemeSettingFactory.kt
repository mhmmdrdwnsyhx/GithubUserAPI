package com.example.submissionfundaawal.nighttheme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ThemeSettingFactory (private val mApplication: ThemePreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <X : ViewModel> create(modelClass: Class<X>): X {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(mApplication) as X }
        throw IllegalArgumentException("Unknown ViewModel class:" + modelClass.name)
    }
}