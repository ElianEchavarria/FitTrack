package com.example.fittrack.navigation

import android.net.Uri

/**
 * Route declarations for every FitTrack destination.
 */
object FitTrackRoutes {
    const val DASHBOARD = "dashboard"

    const val CATEGORY_ARG = "category"
    const val EXERCISE_LIST = "exercises/{$CATEGORY_ARG}"

    const val EXERCISE_ID_ARG = "exerciseId"
    const val EXERCISE_DETAIL = "exercise/{$EXERCISE_ID_ARG}"

    fun exerciseList(category: String): String = "exercises/${Uri.encode(category)}"

    fun exerciseDetail(exerciseId: Int): String = "exercise/$exerciseId"
}
