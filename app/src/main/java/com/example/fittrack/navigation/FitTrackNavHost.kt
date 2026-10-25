package com.example.fittrack.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fittrack.ui.WorkoutViewModel
import com.example.fittrack.ui.screens.DashboardScreen

/**
 * App navigation graph. The single [WorkoutViewModel] is passed in from the activity
 * so every destination shares the same state.
 */
@Composable
fun FitTrackNavHost(
    navController: NavHostController,
    viewModel: WorkoutViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = FitTrackRoutes.DASHBOARD,
        modifier = modifier
    ) {
        composable(route = FitTrackRoutes.DASHBOARD) {
            DashboardScreen(
                uiState = viewModel.uiState,
                categories = viewModel.categories,
                onCategoryClick = { category ->
                    navController.navigate(FitTrackRoutes.exerciseList(category))
                },
                onClearLogs = viewModel::clearLogs
            )
        }

        composable(
            route = FitTrackRoutes.EXERCISE_LIST,
            arguments = listOf(navArgument(FitTrackRoutes.CATEGORY_ARG) { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString(FitTrackRoutes.CATEGORY_ARG).orEmpty()
            // Temporary destination until the exercise list screen is built
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = category, style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}
