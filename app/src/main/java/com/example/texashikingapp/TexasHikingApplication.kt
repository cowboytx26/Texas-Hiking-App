package com.example.texashikingapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.texashikingapp.data.UserPreferencesRepository

private const val EASY_HIKING_PREFERANCE_NAME = "easy_hiking_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = EASY_HIKING_PREFERANCE_NAME
)

class TexasHikingApplication: Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}