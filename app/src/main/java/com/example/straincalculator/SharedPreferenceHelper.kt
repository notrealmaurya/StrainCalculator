package com.example.straincalculator

import android.content.Context

class SharedPreferenceHelper(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun isDarkModeEnabled(): Boolean {
        return sharedPreferences.getBoolean("dark_mode_enabled", false)
    }

    fun setDarkModeEnabled(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("dark_mode_enabled", enabled).apply()
    }

}