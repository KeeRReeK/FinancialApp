package com.example.financialapp

import MyBottomDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.financialapp.Fragments.HomeFragment
import com.example.financialapp.Fragments.InfoFragment
import com.example.financialapp.Fragments.SettingsFragment
import com.example.financialapp.Fragments.StatsFragment
import com.example.financialapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fab = findViewById(R.id.fab)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        replaceFragment(HomeFragment())

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.statistics -> replaceFragment(StatsFragment())
                R.id.settings -> replaceFragment(SettingsFragment())
                R.id.info -> replaceFragment(InfoFragment())
            }
            true
        }

        binding.fab.setOnClickListener { showBottomDialog() }
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: androidx.fragment.app.FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun showBottomDialog() {

        val bottomDialog = MyBottomDialog()
        bottomDialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme)
        bottomDialog.show(supportFragmentManager, "MyBottomDialog")

    }

}