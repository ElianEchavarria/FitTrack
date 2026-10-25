package com.example.fittrack.data

/**
 * Static, in-memory catalog of workout categories and exercises.
 */
object ExerciseRepository {

    val categories = listOf(
        WorkoutCategory(name = "Strength", emoji = "💪", tagline = "Build muscle"),
        WorkoutCategory(name = "Cardio", emoji = "🏃", tagline = "Boost endurance"),
        WorkoutCategory(name = "Flexibility", emoji = "🧘", tagline = "Improve mobility"),
        WorkoutCategory(name = "HIIT", emoji = "⚡", tagline = "Burn fast")
    )

    private val exercises = listOf(
        // Strength
        Exercise(
            id = 1,
            name = "Classic Push-ups",
            category = "Strength",
            difficulty = Difficulty.BEGINNER,
            summary = "A bodyweight press that strengthens the chest, shoulders and triceps.",
            steps = listOf(
                "Start in a high plank with hands slightly wider than shoulder-width.",
                "Brace your core so your body forms a straight line from head to heels.",
                "Lower your chest until it is just above the floor.",
                "Press back up to full arm extension and repeat."
            ),
            recommendedMinutes = 10
        ),
        Exercise(
            id = 2,
            name = "Goblet Squats",
            category = "Strength",
            difficulty = Difficulty.INTERMEDIATE,
            summary = "Hold a weight at your chest and squat deep to build strong legs and glutes.",
            steps = listOf(
                "Hold a dumbbell or kettlebell vertically against your chest.",
                "Stand with feet shoulder-width apart, toes slightly turned out.",
                "Sit your hips back and down until your thighs are parallel to the floor.",
                "Drive through your heels to stand back up."
            ),
            recommendedMinutes = 15
        ),
        Exercise(
            id = 3,
            name = "Barbell Deadlifts",
            category = "Strength",
            difficulty = Difficulty.ADVANCED,
            summary = "A full-body pull from the floor that targets the entire posterior chain.",
            steps = listOf(
                "Stand with mid-foot under the bar, feet hip-width apart.",
                "Hinge at the hips and grip the bar just outside your knees.",
                "Flatten your back, brace your core and pull the slack out of the bar.",
                "Push the floor away and stand tall, then lower the bar under control."
            ),
            recommendedMinutes = 20
        ),
        // Cardio
        Exercise(
            id = 4,
            name = "Brisk Walking",
            category = "Cardio",
            difficulty = Difficulty.BEGINNER,
            summary = "A low-impact walk at a pace that raises your heart rate steadily.",
            steps = listOf(
                "Warm up with 3 minutes of easy-paced walking.",
                "Increase to a pace where talking is possible but slightly effortful.",
                "Swing your arms naturally and keep your posture upright.",
                "Cool down with 3 minutes of slow walking."
            ),
            recommendedMinutes = 30
        ),
        Exercise(
            id = 5,
            name = "Jump Rope",
            category = "Cardio",
            difficulty = Difficulty.INTERMEDIATE,
            summary = "Continuous rope skipping that improves coordination and conditioning.",
            steps = listOf(
                "Hold the handles at hip height with elbows close to your sides.",
                "Turn the rope using your wrists, not your shoulders.",
                "Jump just high enough to clear the rope, landing softly on the balls of your feet.",
                "Alternate 1 minute of skipping with 30 seconds of rest."
            ),
            recommendedMinutes = 15
        ),
        Exercise(
            id = 6,
            name = "Hill Sprints",
            category = "Cardio",
            difficulty = Difficulty.ADVANCED,
            summary = "Short, all-out uphill runs that build explosive speed and lung capacity.",
            steps = listOf(
                "Warm up with 10 minutes of light jogging.",
                "Sprint up a moderate hill for 10 to 15 seconds at maximum effort.",
                "Walk back down slowly to recover.",
                "Repeat for 6 to 10 rounds."
            ),
            recommendedMinutes = 25
        ),
        // Flexibility
        Exercise(
            id = 7,
            name = "Standing Hamstring Stretch",
            category = "Flexibility",
            difficulty = Difficulty.BEGINNER,
            summary = "A gentle forward fold that lengthens the back of the legs.",
            steps = listOf(
                "Stand tall with feet hip-width apart.",
                "Hinge forward at the hips, keeping a slight bend in the knees.",
                "Let your arms hang toward the floor and relax your neck.",
                "Hold for 30 seconds, breathing deeply, then slowly roll up."
            ),
            recommendedMinutes = 5
        ),
        Exercise(
            id = 8,
            name = "Sun Salutation Flow",
            category = "Flexibility",
            difficulty = Difficulty.INTERMEDIATE,
            summary = "A flowing yoga sequence that mobilizes the spine, hips and shoulders.",
            steps = listOf(
                "Begin standing with palms together at your chest.",
                "Reach up, then fold forward and step back into a plank.",
                "Lower down, lift into cobra, then press back into downward dog.",
                "Step forward, rise up and repeat for several rounds."
            ),
            recommendedMinutes = 15
        ),
        Exercise(
            id = 9,
            name = "Pigeon Pose Hold",
            category = "Flexibility",
            difficulty = Difficulty.ADVANCED,
            summary = "A deep hip opener that releases tension in the glutes and hip flexors.",
            steps = listOf(
                "From downward dog, bring your right knee forward behind your right wrist.",
                "Extend your left leg straight back and square your hips.",
                "Fold forward over the front leg, resting on your forearms.",
                "Hold for 1 to 2 minutes, then switch sides."
            ),
            recommendedMinutes = 10
        ),
        // HIIT
        Exercise(
            id = 10,
            name = "Jumping Jacks Intervals",
            category = "HIIT",
            difficulty = Difficulty.BEGINNER,
            summary = "Simple timed intervals of jumping jacks to get your heart pumping quickly.",
            steps = listOf(
                "Perform jumping jacks at a fast pace for 30 seconds.",
                "Rest for 30 seconds while marching in place.",
                "Repeat for 8 rounds.",
                "Finish with a 2-minute walk to cool down."
            ),
            recommendedMinutes = 10
        ),
        Exercise(
            id = 11,
            name = "Mountain Climbers",
            category = "HIIT",
            difficulty = Difficulty.INTERMEDIATE,
            summary = "Fast alternating knee drives from a plank that torch the core.",
            steps = listOf(
                "Start in a high plank with shoulders over wrists.",
                "Drive your right knee toward your chest, then quickly switch legs.",
                "Keep your hips low and your pace fast for 40 seconds.",
                "Rest 20 seconds and repeat for 6 rounds."
            ),
            recommendedMinutes = 12
        ),
        Exercise(
            id = 12,
            name = "Burpee Tabata",
            category = "HIIT",
            difficulty = Difficulty.ADVANCED,
            summary = "Eight rounds of all-out burpees using the classic 20/10 Tabata timing.",
            steps = listOf(
                "Squat down and place your hands on the floor.",
                "Jump your feet back into a plank and perform a push-up.",
                "Jump your feet forward and explode upward with arms overhead.",
                "Work for 20 seconds, rest for 10 seconds, and repeat 8 times."
            ),
            recommendedMinutes = 4
        )
    )

    fun getExercisesForCategory(category: String): List<Exercise> =
        exercises.filter { it.category == category }

    fun getExerciseById(id: Int): Exercise? =
        exercises.find { it.id == id }
}
