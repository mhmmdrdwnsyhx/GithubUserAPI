package com.example.submissionfundaawal.nighttheme

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ThemeViewModel (private val valuedata: ThemePreferences) : ViewModel() {
    fun getThemes(): LiveData<Boolean> {
        return valuedata.getTheme().asLiveData()
    }

    fun saveSettings(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            valuedata.saveTheme(isDarkModeActive)
        }
    }
}