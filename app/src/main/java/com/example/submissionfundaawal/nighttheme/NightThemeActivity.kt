package com.example.submissionfundaawal.nighttheme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submissionfundaawal.R
import com.example.submissionfundaawal.databinding.ActivityNightThemeBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
class NightThemeActivity : AppCompatActivity() {
    private lateinit  var _binding: ActivityNightThemeBinding
    private lateinit var themeViewModel: ThemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNightThemeBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        supportActionBar?.title = "Setting"
        val preferences = ThemePreferences.getInstance(dataStore)
        themeViewModel = ViewModelProvider(
            this,
            ThemeSettingFactory(preferences)
        )[ThemeViewModel::class.java]

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        themeViewModel.getThemes().observe(this) {
            _binding.apply {
                if (it) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                    _binding.image.setImageResource(
                        R.drawable.ic_baseline_moon_24
                    )
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                    _binding.image.setImageResource(
                        R.drawable.ic_baseline_sunny_24
                    )
                }
            }
        }
        _binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean -> themeViewModel.saveSettings(isChecked)
        }
    }
    override fun onOptionsItemSelected(items: MenuItem): Boolean {
        if(items.itemId == android.R.id.home){ finish() }
        return super.onOptionsItemSelected(items)
    }
}