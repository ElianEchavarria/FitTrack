package com.example.fittrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.fittrack.navigation.FitTrackNavHost
import com.example.fittrack.ui.WorkoutViewModel
import com.example.fittrack.ui.theme.FitTrackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitTrackTheme {
                // Created once at the activity level and shared by every destination
                val workoutViewModel: WorkoutViewModel = viewModel()
                val navController = rememberNavController()
                FitTrackNavHost(navController = navController, viewModel = workoutViewModel)
            }
        }
    }
}
