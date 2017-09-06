package com.example.rosty.githubusers.util

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.rosty.githubusers.R


fun Toolbar.onBackAction(action: () -> Unit) {

    navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back)
    setNavigationOnClickListener({ action.invoke() })
}

fun EditText.onSearchAction(action: (String) -> Unit) {

    setOnEditorActionListener { v, actionId, event ->

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            action.invoke(text.toString())
            hideKeyboard()
        }

        false
    }
}

fun EditText.hideKeyboard() {

    clearFocus()

    context.getInputMethodManager()
            .hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}