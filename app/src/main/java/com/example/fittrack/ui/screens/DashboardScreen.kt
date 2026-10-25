package com.example.fittrack.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fittrack.data.ExerciseRepository
import com.example.fittrack.data.WorkoutCategory
import com.example.fittrack.data.WorkoutLog
import com.example.fittrack.ui.WorkoutUiState
import com.example.fittrack.ui.theme.FitTrackTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val logTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("MMM d, h:mm a").withZone(ZoneId.systemDefault())

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    uiState: WorkoutUiState,
    categories: List<WorkoutCategory>,
    onCategoryClick: (String) -> Unit,
    onClearLogs: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "FitTrack", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(key = "summary") {
                SummaryStatsCard(
                    totalWorkouts = uiState.totalWorkouts,
                    totalMinutes = uiState.totalMinutes
                )
            }
            item(key = "categoriesHeader") {
                SectionTitle(text = "Workout Categories")
            }
            item(key = "categories") {
                CategoryPicker(categories = categories, onCategoryClick = onCategoryClick)
            }
            item(key = "historyHeader") {
                HistoryHeader(
                    clearEnabled = uiState.history.isNotEmpty(),
                    onClearLogs = onClearLogs
                )
            }
            if (uiState.history.isEmpty()) {
                item(key = "emptyHistory") {
                    EmptyHistoryMessage()
                }
            } else {
                items(items = uiState.history, key = { it.id }) { log ->
                    HistoryLogItem(log = log)
                }
            }
        }
    }
}

@Composable
private fun SummaryStatsCard(totalWorkouts: Int, totalMinutes: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = "Your Progress", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.size(12.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                StatItem(
                    value = totalWorkouts.toString(),
                    label = "Workouts Completed",
                    modifier = Modifier.weight(1f)
                )
                StatItem(
                    value = totalMinutes.toString(),
                    label = "Total Minutes",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StatItem(value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = value, style = MaterialTheme.typography.headlineMedium)
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.padding(top = 8.dp)
    )
}

@Composable
private fun CategoryPicker(
    categories: List<WorkoutCategory>,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        categories.chunked(2).forEach { rowCategories ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                rowCategories.forEach { category ->
                    CategoryCard(
                        category = category,
                        onClick = { onCategoryClick(category.name) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowCategories.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun CategoryCard(category: WorkoutCategory, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = category.emoji, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = category.name, style = MaterialTheme.typography.titleMedium)
            Text(text = category.tagline, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun HistoryHeader(clearEnabled: Boolean, onClearLogs: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Recent Activity",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = onClearLogs,
            enabled = clearEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Clear Logs")
        }
    }
}

@Composable
private fun EmptyHistoryMessage(modifier: Modifier = Modifier) {
    Text(
        text = "No workouts logged yet. Select a category above to get started!",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 16.dp)
    )
}

@Composable
private fun HistoryLogItem(log: WorkoutLog, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = log.exerciseName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${log.category} • ${logTimeFormatter.format(Instant.ofEpochMilli(log.loggedAtMillis))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "${log.durationMinutes} min",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DashboardScreenPreview() {
    FitTrackTheme {
        DashboardScreen(
            uiState = WorkoutUiState(
                totalWorkouts = 1,
                totalMinutes = 20,
                history = listOf(
                    WorkoutLog(
                        id = 1,
                        exerciseName = "Classic Push-ups",
                        category = "Strength",
                        durationMinutes = 20,
                        loggedAtMillis = 0L
                    )
                )
            ),
            categories = ExerciseRepository.categories,
            onCategoryClick = {},
            onClearLogs = {}
        )
    }
}
