package com.jimbonlemu.fundamental_android.view.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jimbonlemu.fundamental_android.R
import com.jimbonlemu.fundamental_android.data.response.DetailSearchResponse
import com.jimbonlemu.fundamental_android.databinding.ActivityDetailBinding
import com.jimbonlemu.fundamental_android.view.adapter.FollowAndFollowingTabAdapter
import com.jimbonlemu.fundamental_android.view.view_model.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTabLayout()

        val detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        val getExtra = intent.extras?.getString("username")

        detailViewModel.getDataDetailUserGithub(getExtra)

        detailViewModel.isLoading.observe(this) {
            setupLoading(it)
        }

        detailViewModel.userDataDetail.observe(this) { value ->
            setUserDataDetail(value)
        }
    }
    private fun setUserDataDetail(data: DetailSearchResponse) {
        with(binding.layoutProfile) {
            Glide.with(root.context).load(data.avatarUrl).into(civDetailUserImage)
            tvDetailName.text = data.name
            tvDetailUsername.text = data.login
            tvDetailRepos.text = data.publicRepos.toString()
            tvDetailCompanies.text = data.company
            compTitleBio.append(" ${data.name ?: data.login} : ")
            tvDetailBio.text = "${data.bio ?: ""}"
            tvDetailLocation.text = data.location
        }

    }


    private fun setupTabLayout() {
        val sectionsPagerAdapter = FollowAndFollowingTabAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()
        supportActionBar?.hide()
    }

    private fun setupLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.detailProfileShimmerLoader.startShimmer()
            binding.detailProfileShimmerLoader.visibility = View.VISIBLE
            binding.layoutProfile.layoutProfileItem.visibility = View.INVISIBLE
        } else {
            binding.detailProfileShimmerLoader.stopShimmer()
            binding.detailProfileShimmerLoader.visibility = View.GONE
            binding.layoutProfile.layoutProfileItem.visibility = View.VISIBLE
        }
    }

    companion object {
        private val TAB_TITLES = arrayOf(
            "Following",
            "Followed"
        )
    }
}
