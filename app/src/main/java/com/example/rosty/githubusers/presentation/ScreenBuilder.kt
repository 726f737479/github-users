package com.example.rosty.githubusers.presentation

import android.os.Bundle
import com.example.rosty.githubusers.presentation.details.DetailsFragment
import com.example.rosty.githubusers.presentation.search.SearchFragment

private val KEY_RECIPE_ID = "recipe_id"

fun buildSearchScreen() = SearchFragment()

fun buildDetailsScreen(recipeId: String): DetailsFragment {

    val fragment  = DetailsFragment()
    val arguments = Bundle()

    arguments.putString(KEY_RECIPE_ID, recipeId)
    fragment.arguments = arguments

    return fragment
}

fun DetailsFragment.getUserLogin() = arguments.getString(KEY_RECIPE_ID)
