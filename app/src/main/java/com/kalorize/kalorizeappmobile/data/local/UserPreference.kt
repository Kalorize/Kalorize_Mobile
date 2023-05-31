package com.kalorize.kalorizeappmobile.data.local

import android.content.Context
import com.kalorize.kalorizeappmobile.data.remote.response.LoginData
import com.kalorize.kalorizeappmobile.data.remote.response.LoginUser

internal class UserPreference(context: Context) {

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser( value: LoginData) {
        val editor = preference.edit()
        editor.putString(TOKEN , value.token)
        editor.putInt(USER_ID , value.user.id)
        editor.putString(EMAIL , value.user.email)
        editor.putString(PASSWORD , value.user.password)
        editor.apply()
    }

    fun getUser(): LoginData {
        return LoginData(
            token = preference.getString(TOKEN , "")!!,
            user = LoginUser(
                id = preference.getInt(USER_ID , -1),
                email = preference.getString(EMAIL, "")!!,
                password = preference.getString(PASSWORD , "")!!
            )
        )
    }

    companion object{
        private const val PREFS_NAME = "user_pref"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val USER_ID = "userId"
        private const val TOKEN = "token"
    }
}