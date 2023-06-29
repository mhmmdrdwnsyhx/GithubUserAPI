package com.example.submissionfundaawal.nighttheme

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemePreferences private constructor(private val dataStore: DataStore<Preferences>) {
    fun getTheme(): Flow<Boolean> {
        return dataStore.data.map {
            it[KEY] ?: false
        }
    }

    suspend fun saveTheme(isDarkModeActive: Boolean) {
        dataStore.edit {
            it[KEY] = isDarkModeActive
        }
    }
    companion object {
        private val KEY = booleanPreferencesKey("theme_setting")
        @Volatile
        private var INSTANCE: ThemePreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>): ThemePreferences {
            return INSTANCE ?: synchronized(this) {
                val themeInstance = ThemePreferences(dataStore)
                INSTANCE = themeInstance
                themeInstance
            }
        }
    }
}
