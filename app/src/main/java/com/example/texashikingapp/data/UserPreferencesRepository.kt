package com.example.texashikingapp.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
){
    private companion object {
        val EASY_HIKING_PREFERENCE = booleanPreferencesKey("easy_hiking_preference")
    }

    suspend fun saveHikingPreference(easyHikingPref: Boolean) {
        dataStore.edit { preferences ->
            preferences[EASY_HIKING_PREFERENCE] = easyHikingPref
        }
    }

    val easyHikingPref: Flow<Boolean> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[EASY_HIKING_PREFERENCE] ?: true
        }

}