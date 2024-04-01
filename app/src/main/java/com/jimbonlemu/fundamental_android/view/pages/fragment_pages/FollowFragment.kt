package com.jimbonlemu.fundamental_android.view.pages.fragment_pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jimbonlemu.fundamental_android.databinding.FragmentFollowBinding
import com.jimbonlemu.fundamental_android.view.adapter.ListGithubUserAdapter
import com.jimbonlemu.fundamental_android.view.view_model.FollowersViewModel
import com.jimbonlemu.fundamental_android.view.view_model.FollowingViewModel

class FollowFragment : Fragment() {
    private var _position: Int? = null
    private var _username: String? = null
    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private val followingViewModel by viewModels<FollowingViewModel>()
    private val followersViewModel by viewModels<FollowersViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            _position = it.getInt(ARGS_TAB_POSITION)
            _username = it.getString(ARGS_GITHUB_USERNAME)
        }

        val listUserAdapter = ListGithubUserAdapter()

        initRecyclerView()

        if (_position == 1) {
            setFollowingData(listUserAdapter)
        } else {
            setFollowersData(listUserAdapter)
        }
    }

    private fun initRecyclerView() {
        with(binding?.rvFollow!!) {
            val layoutManager = LinearLayoutManager(requireActivity())
            this.layoutManager = layoutManager
            val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
            addItemDecoration(itemDecoration)
        }
    }

    private fun setFollowingData(adapter: ListGithubUserAdapter) {
        with(followingViewModel) {
            with(binding!!) {
                if (followingData.value == null) {
                    getFollowingGithubUser(_username!!)
                }

                isError.observe(viewLifecycleOwner) {
                    tvFollowError.text = it
                }

                followingData.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    rvFollow.adapter = adapter
                }

                isLoading.observe(viewLifecycleOwner) {
                    initLoader(it)
                }
            }
        }
    }

    private fun setFollowersData(adapter: ListGithubUserAdapter) {
        with(followersViewModel) {
            with(binding!!) {
                if (followersData.value == null) {
                    getFollowersGithubUser(_username!!)
                }

                isError.observe(viewLifecycleOwner) {
                    tvFollowError.text = it
                }

                followersData.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    rvFollow.adapter = adapter
                }

                isLoading.observe(viewLifecycleOwner) {
                    initLoader(it)
                }
            }
        }
    }

    private fun initLoader(isLoading: Boolean) {
        with(binding!!) {
            if (isLoading) {
                loaderRvFollow.root.startShimmer()
                loaderRvFollow.root.visibility = View.VISIBLE
                rvFollow.visibility = View.INVISIBLE
            } else {
                loaderRvFollow.root.stopShimmer()
                loaderRvFollow.root.visibility = View.GONE
                rvFollow.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARGS_TAB_POSITION = "args_tab_position"
        const val ARGS_GITHUB_USERNAME = "args_github_username"
    }
}
