package com.example.fittrack.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fittrack.ui.WorkoutViewModel
import com.example.fittrack.ui.screens.DashboardScreen
import com.example.fittrack.ui.screens.ExerciseDetailScreen
import com.example.fittrack.ui.screens.ExerciseListScreen

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
            ExerciseListScreen(
                categoryName = category,
                exercises = viewModel.getExercisesForCategory(category),
                onExerciseClick = { exerciseId ->
                    viewModel.resetDurationInput()
                    navController.navigate(FitTrackRoutes.exerciseDetail(exerciseId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = FitTrackRoutes.EXERCISE_DETAIL,
            arguments = listOf(navArgument(FitTrackRoutes.EXERCISE_ID_ARG) { type = NavType.IntType })
        ) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getInt(FitTrackRoutes.EXERCISE_ID_ARG) ?: -1
            ExerciseDetailScreen(
                exercise = viewModel.getExercise(exerciseId),
                uiState = viewModel.uiState,
                onDurationChange = viewModel::onDurationInputChange,
                onLogWorkout = viewModel::logWorkout,
                onDismissConfirmation = viewModel::dismissLogConfirmation,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
