package com.jimbonlemu.fundamental_android.view.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jimbonlemu.fundamental_android.view.pages.FollowFragment

class SectionsPagerAdapter(
    activity: AppCompatActivity,
    githubUsername: String,
) :
    FragmentStateAdapter(activity) {

    var username: String = githubUsername

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putString(FollowFragment.ARGS_GITHUB_USERNAME, username)
            putInt(FollowFragment.ARGS_TAB_POSITION, position + 1)
        }
        return fragment
    }
}
