package com.example.rosty.githubusers.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.annotation.TransitionRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.transition.TransitionInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.rosty.githubusers.R


fun Fragment.applyTransactions(@TransitionRes res: Int)  {

    val transition = TransitionInflater.from(context)
            .inflateTransition(res)

    sharedElementEnterTransition = transition
    sharedElementReturnTransition = transition
}

fun Context.openWeb(link: String, view: View) {

    openWeb(link, { Snackbar.make(view, R.string.text_no_webview, Snackbar.LENGTH_SHORT).show() })
}

fun Context.openWeb(link: String, noWebAction: () -> Unit) {

    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))

    if (intent.resolveActivity(packageManager) == null) {

        noWebAction.invoke()

    } else startActivity(intent)
}

fun Context.getInputMethodManager() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
