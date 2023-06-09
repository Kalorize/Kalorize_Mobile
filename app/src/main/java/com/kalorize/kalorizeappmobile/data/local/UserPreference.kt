package com.kalorize.kalorizeappmobile.data.local

import android.content.Context
import com.kalorize.kalorizeappmobile.data.remote.response.LoginData
import com.kalorize.kalorizeappmobile.data.remote.response.LoginUser

internal class UserPreference(context: Context) {

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(value: LoginData) {
        val editor = preference.edit()
        editor.putString(TOKEN, value.token)
        editor.putInt(USER_ID, value.user.id)
        editor.putString(EMAIL, value.user.email)
        editor.putString(PASSWORD, value.user.password)
        editor.putString(NAME , value.user.name)
        editor.putString(USER_PICTURE , "https://storage.googleapis.com/${value.user.picture}")
        editor.apply()
    }

    fun bringEmail(email: String) {
        val sharedPreferences = preference.edit()
        sharedPreferences.putString("email-for-change-password", email)
        sharedPreferences.apply()
    }

    fun bringChangedPassword(password:String){
        val sharedPreferences = preference.edit()
        sharedPreferences.putString("new-password", password)
        sharedPreferences.apply()
    }

    fun takeEmail(): String? {
        return preference.getString("email-for-change-password", "")
    }

    fun takeChangedPassword():String?{
        return preference.getString("new-password","")
    }
    fun deleteEmailForForgotPassword(){
        val sharedPreferences = preference.edit()
        sharedPreferences.remove("email-for-change-password").commit()
    }

    fun deleteChangedPassword(){
        val sharedPreferences = preference.edit()
        sharedPreferences.remove("new-password").commit()
    }

    fun getUser(): LoginData {
        return LoginData(
            token = preference.getString(TOKEN, "")!!,
            user = LoginUser(
                id = preference.getInt(USER_ID, -1),
                email = preference.getString(EMAIL, "")!!,
                password = preference.getString(PASSWORD, "")!!,
                name = preference.getString(NAME, "")!!,
                picture = preference.getString(USER_PICTURE , "")
            )
        )
    }

    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val USER_ID = "userId"
        private const val TOKEN = "token"
        private const val NAME = "name"
        private const val USER_PICTURE = "user_picture"
    }
}