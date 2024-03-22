package com.jimbonlemu.fundamental_android.view.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jimbonlemu.fundamental_android.R
import com.jimbonlemu.fundamental_android.data.response.DetailSearchResponse
import com.jimbonlemu.fundamental_android.databinding.ActivityDetailBinding
import com.jimbonlemu.fundamental_android.view.adapter.SectionsPagerAdapter
import com.jimbonlemu.fundamental_android.view.view_model.DetailViewModel

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAppBar()

        val getExtra = intent.extras?.getString("username")

        with(detailViewModel) {
            getDataDetailUserGithub(getExtra)

            isLoading.observe(this@DetailActivity) {
                setupLoading(it)
            }

            spawnSnackBar.observe(this@DetailActivity) {
                it.getContentIfUnhandled()?.let { textOnSnackBar ->
                    Snackbar.make(window.decorView.rootView, textOnSnackBar, Snackbar.LENGTH_SHORT).show()
                }
            }

            userDataDetail.observe(this@DetailActivity) { value ->
                setUserDataDetail(value)
                initTabLayout(getExtra!!, value.following.toString(), value.followers.toString())
            }
        }
    }

    private fun setAppBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_back)
        supportActionBar?.title = "Detail User"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initTabLayout(username: String, following: String = "", follower: String = "") {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text =
                TAB_TITLES[position] + if (position == 0) "\n" + following else "\n" + follower
        }.attach()

    }

    private fun setUserDataDetail(data: DetailSearchResponse) {
        with(binding.layoutProfile) {
            with(data) {
                Glide.with(root.context).load(avatarUrl).into(civDetailUserImage)
                tvDetailName.text = name ?: "-"
                tvDetailUsername.text = login ?: "-"
                tvDetailRepos.text = "${publicRepos ?: "-"}"
                tvDetailCompanies.text = company ?: "-"
                compTitleBio.append(" ${name ?: login} : ")
                tvDetailBio.text = "${bio ?: "-"}"
                tvDetailLocation.text = location ?: "-"
            }
        }

    }

    private fun setupLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                detailProfileShimmerLoader.startShimmer()
                detailProfileShimmerLoader.visibility = View.VISIBLE
                layoutProfile.layoutProfileItem.visibility = View.INVISIBLE
            } else {
                detailProfileShimmerLoader.stopShimmer()
                detailProfileShimmerLoader.visibility = View.GONE
                layoutProfile.layoutProfileItem.visibility = View.VISIBLE
            }
        }
    }


    companion object {
        private val TAB_TITLES = arrayOf(
            "Following",
            "Followers",
        )
    }
}
