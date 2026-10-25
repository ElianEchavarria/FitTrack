package com.example.fittrack.ui

import com.example.fittrack.data.WorkoutLog

/**
 * Single, immutable snapshot of everything the FitTrack screens render.
 */
data class WorkoutUiState(
    val totalWorkouts: Int = 0,
    val totalMinutes: Int = 0,
    val history: List<WorkoutLog> = emptyList(),
    val durationInput: String = "",
    val lastLoggedWorkout: WorkoutLog? = null
) {
    /** Validation message for the duration field, or null when the input is valid. */
    val durationError: String?
        get() {
            val trimmed = durationInput.trim()
            val minutes = trimmed.toIntOrNull()
            return when {
                trimmed.isEmpty() -> "Duration is required."
                minutes == null -> "Please enter a whole number of minutes."
                minutes <= 0 -> "Duration must be greater than 0 minutes."
                else -> null
            }
        }

    val isDurationValid: Boolean
        get() = durationError == null

    val showLogConfirmation: Boolean
        get() = lastLoggedWorkout != null
}
