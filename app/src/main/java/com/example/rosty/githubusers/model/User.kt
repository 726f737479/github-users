package com.example.rosty.githubusers.model

import com.google.gson.annotations.SerializedName


data class User (

    @SerializedName("id")           val id:             String? = null,
    @SerializedName("login")        val login:          String? = null,
    @SerializedName("avatar_url")   val avatarUrl:      String? = null,
    @SerializedName("html_url")     val url:            String? = null,

    @SerializedName("bio")          val bio:            String? = null,
    @SerializedName("public_repos") val reposCount:     String? = null,
    @SerializedName("public_gists") val gistsCount:     String? = null,
    @SerializedName("followers")    val followersCount: String? = null,
    @SerializedName("following")    val followingCount: String? = null,
    @SerializedName("created_at")   val creationDate:   String? = null
)
