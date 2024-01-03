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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val themeList = arrayOf("Light Mode", "Night Mode", "Auto (System Defaults)")
    private lateinit var sharedPreferencesHelper: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesHelper = SharedPreferenceHelper(this)


        //NightMode
        var checkedTheme = sharedPreferencesHelper.theme
        binding.darkModeText.text = "Theme: ${themeList[sharedPreferencesHelper.theme]}"

        binding.darkModeText.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle("Change theme")
                .setPositiveButton("Ok") { _, _ ->
                    sharedPreferencesHelper.theme = checkedTheme
                    AppCompatDelegate.setDefaultNightMode(sharedPreferencesHelper.themeFlag[checkedTheme])
                    binding.darkModeText.text = "Theme: ${themeList[sharedPreferencesHelper.theme]}"
                }
                .setSingleChoiceItems(themeList, checkedTheme) { _, which ->
                    checkedTheme = which
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()

            dialog.setOnDismissListener {
                dialog.dismiss()
            }
        }

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

