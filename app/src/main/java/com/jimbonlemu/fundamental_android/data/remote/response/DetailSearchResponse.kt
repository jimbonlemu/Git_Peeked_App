package com.jimbonlemu.fundamental_android.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailSearchResponse(

	@field:SerializedName("bio")
	val bio: Any? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("public_repos")
	val publicRepos: Int? = null,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

)
