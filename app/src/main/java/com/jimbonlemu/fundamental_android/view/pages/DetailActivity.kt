package com.jimbonlemu.fundamental_android.view.pages

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jimbonlemu.fundamental_android.R
import com.jimbonlemu.fundamental_android.data.local.entity.FavoriteEntity
import com.jimbonlemu.fundamental_android.data.remote.response.DetailSearchResponse
import com.jimbonlemu.fundamental_android.databinding.ActivityDetailBinding
import com.jimbonlemu.fundamental_android.utils.SettingPreference
import com.jimbonlemu.fundamental_android.utils.dataStore
import com.jimbonlemu.fundamental_android.view.adapter.SectionsPagerAdapter
import com.jimbonlemu.fundamental_android.view.view_model.DetailViewModel
import com.jimbonlemu.fundamental_android.view.view_model.FavoriteViewModel
import com.jimbonlemu.fundamental_android.view.view_model_factory.FavoriteViewModelFactory

class DetailActivity : AppBarActivity("Detail User Page") {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private var detailSearchResponse = DetailSearchResponse()
    private var isDarkModeActive = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDarkMode()

        val favViewModelFactory = FavoriteViewModelFactory.getInstance(this)
        val favViewModel: FavoriteViewModel by viewModels {
            favViewModelFactory
        }

        var isFavorite = false

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
                detailSearchResponse = value
            }
        }

        with(favViewModel) {
            with(binding.layoutProfile) {
                checkStatusFavorite(getExtra ?: "").observe(this@DetailActivity) { fav ->
                    isFavorite = fav != null
                    if (isFavorite) {
                        fabDetail.setImageResource(R.drawable.icon_favorited)
                    } else {
                        fabDetail.setImageResource(R.drawable.icon_unfavorited)
                    }
                }

                fabDetail.setOnClickListener {
                    val entity = FavoriteEntity(
                        username = detailSearchResponse.login ?: "-",
                        userImage = detailSearchResponse.avatarUrl
                    )
                    if (isFavorite) {
                        deleteFavorite(entity)
                        getToast("Success delete $getExtra from Favorite")
                    } else {
                        insertFavorite(entity)
                        getToast("Success added $getExtra to Favorite")
                    }
                }
            }
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
                detailProfileShimmerLoader.startShimmer()

            } else {
                detailProfileShimmerLoader.stopShimmer()
            }
            detailProfileShimmerLoader.visibility = if (isLoading) View.VISIBLE else View.GONE
            layoutProfile.layoutProfileItem.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
            layoutProfile.fabDetail.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        }
    }

    private fun initDarkMode() {
        SettingPreference.getInstance(application.dataStore).getThemeSetting().asLiveData()
            .observe(this) { darkModeIsActive ->
                isDarkModeActive = darkModeIsActive
                setIconMode()
                setTabLayoutBackground()
            }
    }

    private fun setTabLayoutBackground() {
        val selectedColor = if (isDarkModeActive) R.color.white else R.color.black
        val unselectedColor = if (isDarkModeActive) R.color.white else R.color.black
        val drawableResId =
            if (isDarkModeActive) R.drawable.rounded_corner_dark_mode else R.drawable.rounded_corner

        binding.tabs.apply {
            setSelectedTabIndicatorColor(ContextCompat.getColor(this@DetailActivity, selectedColor))
            setTabTextColors(
                ContextCompat.getColor(this@DetailActivity, selectedColor),
                ContextCompat.getColor(this@DetailActivity, unselectedColor)
            )
            background = ContextCompat.getDrawable(this@DetailActivity, drawableResId)
        }
    }

    private fun setIconMode() {
        binding.layoutProfile.apply {
            iconCompLocation.setImageResource(if (isDarkModeActive) R.drawable.icon_location_dark_mode else R.drawable.icon_location)
            iconCompCompanies.setImageResource(if (isDarkModeActive) R.drawable.icon_company_dark_mode else R.drawable.icon_company)
            iconCompRepos.setImageResource(if (isDarkModeActive) R.drawable.icon_repo_dark_mode else R.drawable.icon_repo)
        }

    }

    private fun getToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val TAB_TITLES = arrayOf(
            "Following",
            "Followers",
        )
    }
}
