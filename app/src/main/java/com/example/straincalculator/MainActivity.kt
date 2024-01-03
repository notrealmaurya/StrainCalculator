package com.example.straincalculator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.straincalculator.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferencesDarkMode: SharedPreferences
    private lateinit var sharedPreferencesHelper: SharedPreferenceHelper
    private var isDarkModeEnabled = false // Initialize with your default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesHelper = SharedPreferenceHelper(this)

// SharedPreference for Switching Dark Mode
        sharedPreferencesDarkMode = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        isDarkModeEnabled = sharedPreferencesHelper.isDarkModeEnabled()

// Initialize the app's theme based on the stored mode
        val nightMode =
            if (isDarkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(nightMode)


        binding.navView.visibility = View.VISIBLE

        initViewPager()
        listeners()

    }


    private fun initViewPager() {

        val myAdapter = ViewPagerAdapter(this)
        myAdapter.addFragment(StrainFragment(), "Strain")
        myAdapter.addFragment(StrainEnergyFragment(), "Strain Energy")


        val viewPager = binding.viewPAGER
        viewPager.adapter = myAdapter


        val tabLayout = binding.tabLayout
        val tabIndicator = ContextCompat.getDrawable(this, R.drawable.custom_tab_indicator)
        tabLayout.setSelectedTabIndicator(tabIndicator)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = myAdapter.getTitle(position)
        }.attach()


    }


    private fun listeners() {

        //top toolbar
        binding.menuImageView.setOnClickListener(View.OnClickListener {
            binding.MainDrawerLayout.openDrawer(GravityCompat.START)
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT)
        })



        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_feedback -> {
                    val websiteUrl =
                        "https://docs.google.com/forms/d/e/1FAIpQLSfRsCpO9jc0t61V6E5IkjH6L0HSoWmk2LQdy0EPJ1SmBL7_hQ/viewform"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
                    startActivity(intent)
                }


                R.id.nav_about -> {
                    val customDialogFragment = AboutDialogFragment()
                    customDialogFragment.show(supportFragmentManager, "CustomDialogFragment")
                }

            }
            menuItem.isChecked = false
            binding.MainDrawerLayout.closeDrawer(GravityCompat.START)
            true // Return true to indicate that the click was handled
        }


        //dark mode switch
        val isDarkModeEnabled = sharedPreferencesHelper.isDarkModeEnabled()
        // Initialize the dark mode switch
        binding.darkModeSwitch.isChecked = isDarkModeEnabled
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Update the shared preference using the helper
            sharedPreferencesHelper.setDarkModeEnabled(isChecked)
            // Update the theme based on the switch state
            val nightMode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(nightMode)

            this.isDarkModeEnabled = isChecked
        }

    }


    override fun onBackPressed() {
        if (binding.MainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.MainDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val titleList: MutableList<String> = ArrayList()

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    fun getTitle(position: Int): String = titleList[position]
}

