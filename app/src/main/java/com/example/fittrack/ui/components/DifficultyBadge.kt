package com.example.fittrack.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fittrack.data.Difficulty
import com.example.fittrack.ui.theme.AdvancedRed
import com.example.fittrack.ui.theme.BeginnerGreen
import com.example.fittrack.ui.theme.IntermediateAmber

/**
 * Small colored pill showing an exercise's difficulty level.
 */
@Composable
fun DifficultyBadge(difficulty: Difficulty, modifier: Modifier = Modifier) {
    val badgeColor = when (difficulty) {
        Difficulty.BEGINNER -> BeginnerGreen
        Difficulty.INTERMEDIATE -> IntermediateAmber
        Difficulty.ADVANCED -> AdvancedRed
    }
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = badgeColor,
        contentColor = Color.White
    ) {
        Text(
            text = difficulty.label,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}
