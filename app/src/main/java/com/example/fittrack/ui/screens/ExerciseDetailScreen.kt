package com.example.fittrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fittrack.data.Exercise
import com.example.fittrack.data.ExerciseRepository
import com.example.fittrack.data.WorkoutLog
import com.example.fittrack.ui.WorkoutUiState
import com.example.fittrack.ui.components.DifficultyBadge
import com.example.fittrack.ui.theme.FitTrackTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(
    exercise: Exercise?,
    uiState: WorkoutUiState,
    onDurationChange: (String) -> Unit,
    onLogWorkout: (Exercise) -> Unit,
    onDismissConfirmation: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = exercise?.name ?: "Exercise Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to exercise list"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        if (exercise == null) {
            Text(
                text = "Exercise not found.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(24.dp)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ExerciseInfoCard(exercise = exercise)
                WorkoutLoggerCard(
                    durationInput = uiState.durationInput,
                    durationError = uiState.durationError,
                    isDurationValid = uiState.isDurationValid,
                    onDurationChange = onDurationChange,
                    onLogWorkout = { onLogWorkout(exercise) }
                )
            }
        }

        uiState.lastLoggedWorkout?.let { loggedWorkout ->
            LogConfirmationDialog(
                loggedWorkout = loggedWorkout,
                onDismiss = onDismissConfirmation
            )
        }
    }
}

@Composable
private fun ExerciseInfoCard(exercise: Exercise, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = exercise.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                DifficultyBadge(difficulty = exercise.difficulty)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = exercise.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Text(
                    text = "⏱ Recommended duration: ${exercise.recommendedMinutes} minutes",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Instructions", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            exercise.steps.forEachIndexed { index, step ->
                InstructionStep(number = index + 1, text = step)
            }
        }
    }
}

@Composable
private fun InstructionStep(number: Int, text: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(vertical = 4.dp)) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(24.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = number.toString(), style = MaterialTheme.typography.labelSmall)
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun WorkoutLoggerCard(
    durationInput: String,
    durationError: String?,
    isDurationValid: Boolean,
    onDurationChange: (String) -> Unit,
    onLogWorkout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Log This Workout", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = durationInput,
                onValueChange = onDurationChange,
                label = { Text(text = "Duration (Minutes)") },
                singleLine = true,
                isError = durationError != null,
                supportingText = {
                    if (durationError != null) {
                        Text(text = durationError, color = MaterialTheme.colorScheme.error)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onLogWorkout,
                enabled = isDurationValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Log Completed Workout")
            }
        }
    }
}

@Composable
private fun LogConfirmationDialog(loggedWorkout: WorkoutLog, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = { Text(text = "Workout Logged!") },
        text = {
            Text(
                text = "Great job! ${loggedWorkout.durationMinutes} minutes of " +
                    "${loggedWorkout.exerciseName} have been added to your history."
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "OK")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ExerciseDetailScreenPreview() {
    FitTrackTheme {
        ExerciseDetailScreen(
            exercise = ExerciseRepository.getExerciseById(1),
            uiState = WorkoutUiState(durationInput = "15"),
            onDurationChange = {},
            onLogWorkout = {},
            onDismissConfirmation = {},
            onBackClick = {}
        )
    }
}
