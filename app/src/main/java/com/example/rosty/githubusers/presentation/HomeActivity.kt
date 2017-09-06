package com.example.rosty.githubusers.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.example.rosty.githubusers.R
import com.example.rosty.githubusers.presentation.search.SearchFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, buildSearchScreen())
                    .commit()
        }
    }
}
