package com.jimbonlemu.fundamental_android.view.pages

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jimbonlemu.fundamental_android.R
import com.jimbonlemu.fundamental_android.data.response.DetailSearchResponse
import com.jimbonlemu.fundamental_android.databinding.ActivityDetailBinding
import com.jimbonlemu.fundamental_android.utils.SettingPreference
import com.jimbonlemu.fundamental_android.utils.dataStore
import com.jimbonlemu.fundamental_android.view.adapter.SectionsPagerAdapter
import com.jimbonlemu.fundamental_android.view.view_model.DetailViewModel

class DetailActivity : AppBarActivity("Detail User Page") {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private var isFavorite = false
    private var isDarkModeActive = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDarkMode()

        val getExtra = intent.extras?.getString("username")

        with(detailViewModel) {
            getDataDetailUserGithub(getExtra)

            isLoading.observe(this@DetailActivity) {
                setupLoading(it)
            }

            spawnSnackBar.observe(this@DetailActivity) {
                it.getContentIfUnhandled()?.let { textOnSnackBar ->
                    Snackbar.make(window.decorView.rootView, textOnSnackBar, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

            userDataDetail.observe(this@DetailActivity) { value ->
                setUserDataDetail(value)
                initTabLayout(getExtra!!, value.following.toString(), value.followers.toString())
            }
        }

        binding.layoutProfile.fabDetail.setOnClickListener {
            isFavorite = !isFavorite
            setFabMode(binding.layoutProfile.fabDetail)
        }

    }

    private fun initTabLayout(username: String, following: String = "", follower: String = "") {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
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
                layoutProfile.fabDetail.visibility = View.INVISIBLE
                detailProfileShimmerLoader.startShimmer()
                detailProfileShimmerLoader.visibility = View.VISIBLE
                layoutProfile.layoutProfileItem.visibility = View.INVISIBLE
            } else {
                detailProfileShimmerLoader.stopShimmer()
                detailProfileShimmerLoader.visibility = View.GONE
                layoutProfile.layoutProfileItem.visibility = View.VISIBLE
                layoutProfile.fabDetail.visibility = View.VISIBLE
            }
        }
    }

    private fun initDarkMode() {
        SettingPreference.getInstance(application.dataStore).getThemeSetting().asLiveData()
            .observe(this) { darkModeIsActive ->
                isDarkModeActive = darkModeIsActive
                setIconMode()
            }
    }

    private fun setIconMode() {
        with(binding.layoutProfile) {
            iconCompLocation.setImageResource(if (isDarkModeActive) R.drawable.icon_location_dark_mode else R.drawable.icon_location)
            iconCompCompanies.setImageResource(if (isDarkModeActive) R.drawable.icon_company_dark_mode else R.drawable.icon_company)
            iconCompRepos.setImageResource(if (isDarkModeActive) R.drawable.icon_repo_dark_mode else R.drawable.icon_repo)
        }
    }

    private fun setFabMode(image: ImageView) {
        val setResourceByMode = if (isFavorite) {
            if (isDarkModeActive) R.drawable.icon_favorited_dark_mode else R.drawable.icon_favorited
        } else {
            if (isDarkModeActive) R.drawable.icon_unfavorited_dark_mode else R.drawable.icon_unfavorited
        }
        image.setImageResource(setResourceByMode)
    }

    companion object {
        private val TAB_TITLES = arrayOf(
            "Following",
            "Followers",
        )
    }
}