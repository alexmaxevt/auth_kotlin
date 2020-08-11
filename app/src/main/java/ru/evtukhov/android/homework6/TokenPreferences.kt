package ru.evtukhov.android.homework6

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.edit
import ru.evtukhov.android.homework6.api.Token

private const val TOKEN_KEY = "TOKEN_KEY"
private const val SHARED_PREF_KEY = "SHARED_PREF"

fun saveToken(token: Token?, context: Context) {
    val sharedPref = context.getSharedPreferences(
        SHARED_PREF_KEY,
        Context.MODE_PRIVATE
    )
    sharedPref.edit {
        putString(
            TOKEN_KEY,
            token?.token
        )
    }


}

fun getToken(context: Context): String? {
    val sharedPref = context.getSharedPreferences(
        SHARED_PREF_KEY,
        Context.MODE_PRIVATE
    )
    return sharedPref.getString(
        TOKEN_KEY,
        ""
    )
}

fun isAuthorized(context: Context) {
    val sharedPref = context.getSharedPreferences(
        SHARED_PREF_KEY,
        Context.MODE_PRIVATE
    )
    val token =sharedPref.getString(
        TOKEN_KEY,
        ""
    )
    if (token != "") {
        val intent = Intent(context, FeedActivity::class.java)
        context.startActivity(intent)
        val activity = context as Activity
        activity.finish()
    }
}