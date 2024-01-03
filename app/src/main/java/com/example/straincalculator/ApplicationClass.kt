package com.example.straincalculator

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

class ApplicationClass:Application() {


    override fun onCreate() {
        super.onCreate()

        val sharedPreferenceHelper = SharedPreferenceHelper(this)
        AppCompatDelegate.setDefaultNightMode(sharedPreferenceHelper.themeFlag[sharedPreferenceHelper.theme])


    }
}