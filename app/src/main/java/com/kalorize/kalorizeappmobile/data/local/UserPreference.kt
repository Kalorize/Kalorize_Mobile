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
        editor.putString(NAME, value.user.name)
        editor.putString(USER_PICTURE, "https://storage.googleapis.com/${value.user.picture}")
        editor.putString(GENDER, value.user.gender)
        editor.putFloat(WEIGHT, value.user.weight ?: 0.toFloat())
        editor.putFloat(AGE, value.user.age ?: 0.toFloat())
        editor.putFloat(HEIGHT, value.user.height ?: 0.toFloat())
        editor.putString(ACTIVITY, value.user.activity ?: "")
        editor.putString(TARGET, value.user.target)
        editor.apply()
    }

    fun setGender(gender: String) {
        val sharedPreferences = preference.edit()
        sharedPreferences.putString("gender-profiling", gender)
        sharedPreferences.apply()
    }

    fun setAge(age: Float) {
        val sharedPreferences = preference.edit()
        sharedPreferences.putFloat("age-profiling", age)
        sharedPreferences.apply()
    }

    fun setWeight(weight: Float) {
        val sharedPreferences = preference.edit()
        sharedPreferences.putFloat("weight-profiling", weight)
        sharedPreferences.apply()
    }

    fun setHeight(height: Float) {
        val sharedPreferences = preference.edit()
        sharedPreferences.putFloat("height-profiling", height)
        sharedPreferences.apply()
    }

    fun setActivity(activity: String) {
        val sharedPreferences = preference.edit()
        sharedPreferences.putString("activity-profiling", activity)
        sharedPreferences.apply()
    }

    fun getAge(): Float? {
        return preference.getFloat("age-profiling", 0.toFloat())
    }

    fun getGender(): String? {
        return preference.getString("gender-profiling", "")
    }

    fun getWeight(): Float? {
        return preference.getFloat("weight-profiling", 0.toFloat())
    }

    fun getHeight(): Float? {
        return preference.getFloat("height-profiling", 0.toFloat())
    }

    fun getActivity(): String? {
        return preference.getString("activity-profiling", "")
    }

    fun deleteProfilingSharedPref() {
        val sharedPreferences = preference.edit()
        sharedPreferences.remove("gender-profiling")
        sharedPreferences.remove("weight-profiling")
        sharedPreferences.remove("height-profiling")
        sharedPreferences.remove("activity-profiling")
        sharedPreferences.apply()
    }

    fun bringEmail(email: String) {
        val sharedPreferences = preference.edit()
        sharedPreferences.putString("email-for-change-password", email)
        sharedPreferences.apply()
    }

    fun bringChangedPassword(password: String) {
        val sharedPreferences = preference.edit()
        sharedPreferences.putString("new-password", password)
        sharedPreferences.apply()
    }

    fun takeEmail(): String? {
        return preference.getString("email-for-change-password", "")
    }

    fun takeChangedPassword(): String? {
        return preference.getString("new-password", "")
    }

    fun deleteEmailForForgotPassword() {
        val sharedPreferences = preference.edit()
        sharedPreferences.remove("email-for-change-password").commit()
    }

    fun deleteChangedPassword() {
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
                picture = preference.getString(USER_PICTURE, ""),
                gender = preference.getString(GENDER, ""),
                weight = preference.getFloat(WEIGHT, 0.toFloat()),
                height = preference.getFloat(HEIGHT, 0.toFloat()),
                age = preference.getFloat(AGE, 0.toFloat()),
                activity = preference.getString(ACTIVITY, ""),
                target = preference.getString(TARGET, ""),
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
        private const val GENDER = "gender"
        private const val AGE = "age"
        private const val HEIGHT = "height"
        private const val WEIGHT = "weight"
        private const val ACTIVITY = "activity"
        private const val TARGET = "target"
    }
}