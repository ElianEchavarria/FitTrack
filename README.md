# FitTrack

A 3-screen workout tracking app for Android, built with 100% Kotlin, Jetpack Compose, Material Design 3 and the MVVM architecture pattern.

CMP431 – Project 1

## Features

| Screen | What it does |
| --- | --- |
| **Dashboard** | Summary card with total workouts and total minutes, a 2×2 category picker (Strength, Cardio, Flexibility, HIIT), a recent activity log, and a Clear Logs button. |
| **Exercise List** | Shows the exercises for the selected category, each with its name, a colored difficulty badge and a one-sentence preview. |
| **Exercise Detail** | Full step-by-step instructions, the recommended duration, and a validated duration input that logs the workout and confirms with a dialog. |

## Architecture

```
app/src/main/java/com/example/fittrack/
├── MainActivity.kt              # Creates the single shared ViewModel and NavController
├── data/
│   ├── Models.kt                # Exercise, WorkoutCategory, WorkoutLog, Difficulty
│   └── ExerciseRepository.kt    # Static in-memory exercise catalog
├── navigation/
│   ├── FitTrackRoutes.kt        # Route declarations and argument names
│   └── FitTrackNavHost.kt       # NavHost graph wiring screens to ViewModel events
└── ui/
    ├── WorkoutUiState.kt        # Unified immutable UI state + derived validation
    ├── WorkoutViewModel.kt      # Single ViewModel (mutableStateOf with private set)
    ├── components/
    │   └── DifficultyBadge.kt
    ├── screens/
    │   ├── DashboardScreen.kt
    │   ├── ExerciseListScreen.kt
    │   └── ExerciseDetailScreen.kt
    └── theme/                   # Material 3 colors, typography and theme
```

- **MVVM + Unidirectional Data Flow.** `WorkoutViewModel` exposes one `WorkoutUiState` through `mutableStateOf()` with a `private set`. Screens are stateless: they receive state as parameters and report user actions through event lambdas.
- **Single shared ViewModel.** It is created once in `MainActivity` and passed into the `NavHost`, so state survives navigation between destinations.
- **Navigation.** `NavHost` with three routes: `dashboard`, `exercises/{category}` (String argument) and `exercise/{exerciseId}` (Int argument).
- **Lazy lists.** The exercise feed and the history log use `LazyColumn` with stable `key = { it.id }`.
- **Validation.** The duration field shows a red error and disables the log button when the input is empty, not a number, or ≤ 0.
- **Synchronous state only.** No coroutines, Flows or side-effect APIs.

## Tech Stack

- Kotlin 2.0, Jetpack Compose (BOM 2024.12.01), Material 3
- Navigation Compose 2.8
- AndroidX Lifecycle ViewModel 2.8
- minSdk 26, targetSdk 35

## Build & Run

1. Open the project folder in Android Studio.
2. Let Gradle sync finish.
3. Run the `app` configuration on an emulator or device (Android 8.0+).

From the command line:

```bash
./gradlew assembleDebug
```
