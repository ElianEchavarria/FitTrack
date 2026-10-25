package com.example.fittrack.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.fittrack.data.Exercise
import com.example.fittrack.data.ExerciseRepository
import com.example.fittrack.data.WorkoutCategory
import com.example.fittrack.data.WorkoutLog

/**
 * The single ViewModel shared by every FitTrack destination.
 * Screens read [uiState] and send events through the public functions (UDF).
 */
class WorkoutViewModel : ViewModel() {

    var uiState by mutableStateOf(WorkoutUiState())
        private set

    private var nextLogId = 1

    val categories: List<WorkoutCategory> = ExerciseRepository.categories

    fun getExercisesForCategory(category: String): List<Exercise> =
        ExerciseRepository.getExercisesForCategory(category)

    fun getExercise(exerciseId: Int): Exercise? =
        ExerciseRepository.getExerciseById(exerciseId)

    fun onDurationInputChange(input: String) {
        uiState = uiState.copy(durationInput = input)
    }

    fun resetDurationInput() {
        uiState = uiState.copy(durationInput = "")
    }

    fun logWorkout(exercise: Exercise) {
        val minutes = uiState.durationInput.trim().toIntOrNull()
        if (minutes == null || minutes <= 0) return

        val entry = WorkoutLog(
            id = nextLogId++,
            exerciseName = exercise.name,
            category = exercise.category,
            durationMinutes = minutes,
            loggedAtMillis = System.currentTimeMillis()
        )
        uiState = uiState.copy(
            totalWorkouts = uiState.totalWorkouts + 1,
            totalMinutes = uiState.totalMinutes + minutes,
            history = listOf(entry) + uiState.history,
            durationInput = "",
            lastLoggedWorkout = entry
        )
    }

    fun dismissLogConfirmation() {
        uiState = uiState.copy(lastLoggedWorkout = null)
    }

    fun clearLogs() {
        uiState = WorkoutUiState()
    }
}
