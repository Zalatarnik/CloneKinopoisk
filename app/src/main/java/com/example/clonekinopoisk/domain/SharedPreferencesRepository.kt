package com.example.clonekinopoisk.domain

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

private const val SHARED_PREF = "sharedPref"
private const val USER_PREF = "userPref"
private  const val IS_FIRST_LAUNCH = "is_first_launch"
private const val USER_EMAIL = "user_email"

@Qualifier
annotation class ApplicationSharedPreferences

@Qualifier
annotation class UserSharedPreferences

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    @ApplicationSharedPreferences
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @UserSharedPreferences
    fun provideUserPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
    }
}

class SharedPreferencesRepository @Inject constructor(
    @ApplicationSharedPreferences private val sharedPreferences: SharedPreferences,
    @UserSharedPreferences private val userPreferences: SharedPreferences
){

    fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true)
    }

    fun setIsFirstLaunch() {
        sharedPreferences.edit {
            putBoolean(IS_FIRST_LAUNCH, false)
        }
    }

    fun setUserEmail(email: String){
        userPreferences.edit {
            putString(USER_EMAIL,email)
        }
    }

    fun getUserEmail():String?{
        return  userPreferences.getString(USER_EMAIL,null)
    }

    fun logout(){
        userPreferences.edit {
            clear()
        }
    }
    fun getInfo(){

    }

}


