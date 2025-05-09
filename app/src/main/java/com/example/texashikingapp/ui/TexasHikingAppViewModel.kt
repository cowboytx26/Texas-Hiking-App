package com.example.texashikingapp.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.getString
import com.example.texashikingapp.R
import com.example.texashikingapp.data.UserPreferencesRepository
import com.example.texashikingapp.TexasHikingApplication
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.texashikingapp.model.Trail
import com.example.texashikingapp.model.trails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/*
 * View model of Texas Hiking app components
 */
class TexasHikingAppViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState =
        userPreferencesRepository.easyHikingPref.map { easyHikingPref ->
            TexasHikingAppUiState(easyHikingPref)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TexasHikingAppUiState()
            )

    // UI states access for various [TexasHikingAppUiState]
    val uiState: StateFlow<TexasHikingAppUiState> = _uiState

    //List used to display trails for the selected state park
    var currTrails: MutableList<Trail> = mutableListOf()

    /*
     * [selectLayout] change the layout and icons accordingly and
     * save the selection in DataStore through [userPreferencesRepository]
     */
    fun selectHikingPreference(easyHikingPref: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveHikingPreference(easyHikingPref)
        }
    }

    //Function used to identify trails in the selected state park
    fun selectHikingTrails(context: Context ,@StringRes parkName: Int, prefEasyHike: Boolean) {

        currTrails.clear()
        val firstString: String = getString(context, parkName)
        var easyString: String = getString(context, R.string.easy)
        var secondString: String = ""
        var difficultyRating: String = ""
        for (trail in trails) {
            secondString = getString(context,trail.parkName)
            difficultyRating = getString(context, trail.difficultyRating)
            if (prefEasyHike) {
                if ((firstString.compareTo(secondString) == 0) && (difficultyRating.compareTo(easyString) == 0)) {
                    currTrails.add(trail)
                }
            } else {
                if (firstString.compareTo(secondString) == 0) {
                    currTrails.add(trail)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TexasHikingApplication)
                TexasHikingAppViewModel(application.userPreferencesRepository)
            }
        }
    }
}

/*
 * Data class containing various UI States for Texas Hiking App screens
 */
data class TexasHikingAppUiState(
    val prefEasyHike: Boolean = true,
    val toggleContentDescription: Int =
        if (prefEasyHike) R.string.easy_hike_on else R.string.easy_hike_off,
)