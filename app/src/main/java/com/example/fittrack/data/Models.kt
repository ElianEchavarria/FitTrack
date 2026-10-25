package com.example.fittrack.data

enum class Difficulty(val label: String) {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced")
}

data class WorkoutCategory(
    val name: String,
    val emoji: String,
    val tagline: String
)

data class Exercise(
    val id: Int,
    val name: String,
    val category: String,
    val difficulty: Difficulty,
    val summary: String,
    val steps: List<String>,
    val recommendedMinutes: Int
)

data class WorkoutLog(
    val id: Int,
    val exerciseName: String,
    val category: String,
    val durationMinutes: Int,
    val loggedAtMillis: Long
)
