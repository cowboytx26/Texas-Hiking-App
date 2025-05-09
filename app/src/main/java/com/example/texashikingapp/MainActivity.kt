package com.example.texashikingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.texashikingapp.ui.ParkApp
import com.example.texashikingapp.ui.theme.TexasHikingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TexasHikingAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize()
                ) {
                    ParkApp()
                }
            }
        }
    }
}

