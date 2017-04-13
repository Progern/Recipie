package com.olegmisko.recipie.Services

import android.content.SharedPreferences
import com.olegmisko.recipie.Config.LOGIN_STATE

internal object LoginStateService {

    fun changeUserStateToLoggedIn(sharedPreferences: SharedPreferences) {
        sharedPreferences.edit().putBoolean(LOGIN_STATE, true).apply()
    }

    fun changeUserStateToLoggedOut(sharedPreferences: SharedPreferences) {
        sharedPreferences.edit().putBoolean(LOGIN_STATE, false).apply()
    }

}


