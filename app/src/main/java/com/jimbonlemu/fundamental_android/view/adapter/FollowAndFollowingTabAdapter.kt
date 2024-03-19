package com.jimbonlemu.fundamental_android.view.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jimbonlemu.fundamental_android.view.fragment_pages.FollowFragment

class FollowAndFollowingTabAdapter(activity:AppCompatActivity):FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment:Fragment? = null
        when(position){
            0 -> fragment = FollowFragment()
            1 -> fragment = FollowFragment()
        }
        return fragment as Fragment
    }
    override fun getItemCount(): Int = 2

}