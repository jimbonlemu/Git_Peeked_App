package com.jimbonlemu.chicken_man

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)


        if ( fragment !is HomeFragment ) {
            Log.d("Chicken Man", "Fragment Name :" + HomeFragment::class.java.simpleName)

            fragmentManager.beginTransaction()
                .add(R.id.frame_container, HomeFragment(), HomeFragment::class.java.simpleName)
                .commit()
        }
    }
}