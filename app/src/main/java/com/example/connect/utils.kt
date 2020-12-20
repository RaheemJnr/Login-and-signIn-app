package com.example.connect

import android.content.Context
import com.example.connect.network.User


class SharedPrefManager {

    private val SHARED_PREF_NAME = "userSharedPreference"
    private val KEY_USER_NAME = "keyusername"
    private val KEY_USER_EMAIL = "keyuseremail"
    private val KEY_USER_PASSWORD = "keyuserpassword"


    companion object {
        @Volatile
        private var INSTANCE: SharedPrefManager? = null
        private var mCtx: Context? = null

        private fun sharedPrefManager(context: Context): SharedPrefManager? {
            mCtx = context
            return null
        }

        // instance
        fun getInstance(context: Context): SharedPrefManager? {
            var instance = INSTANCE
            synchronized(this) {
                // create new instance of sharedPref if its null
                if (instance == null) {
                    instance = sharedPrefManager(context)
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

    //
    fun userLogin(user: User): Boolean {
        val sharedPreferences = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(KEY_USER_NAME, user.Name)
            putString(KEY_USER_EMAIL, user.Email)
            apply()

        }
        return true
    }

    //
    fun isLoggedIn(): Boolean {
        val sharedPreferences = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_USER_EMAIL, null).toBoolean()
    }

    //
    fun getUser(): User {
        val sharedPreferences = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return User(
            sharedPreferences.getString(KEY_USER_NAME, null)!!,
            sharedPreferences.getString(KEY_USER_EMAIL, null)!!,
            sharedPreferences.getString(KEY_USER_PASSWORD, null)!!
        )
    }

    fun logout(): Boolean {
        val sharedPreferences = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        return true
    }


}