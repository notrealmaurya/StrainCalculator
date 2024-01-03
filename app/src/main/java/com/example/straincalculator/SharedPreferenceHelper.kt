package com.example.straincalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SharedPreferenceHelper(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(context.packageName, AppCompatActivity.MODE_PRIVATE)


    private val editor = sharedPreferences.edit()
    private val keyTheme="theme"
    var theme get() = sharedPreferences.getInt(keyTheme,2)
        set(value) {
            editor.putInt(keyTheme,value)
            editor.commit()
        }

    val themeFlag= arrayOf(
        AppCompatDelegate.MODE_NIGHT_NO,
        AppCompatDelegate.MODE_NIGHT_YES,
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    )



}