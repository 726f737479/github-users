package com.example.rosty.githubusers.data.remote.response

import com.example.rosty.githubusers.model.User
import com.google.gson.annotations.SerializedName


data class SearchResponse(@SerializedName("total_count") val count: Int,
                          @SerializedName("items")       val users: List<User>)
