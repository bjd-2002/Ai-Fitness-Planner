package com.example.fitlife.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitlife.R
import com.example.fitlife.data.Muscle
import com.example.fitlife.screens.HomePage
import com.example.fitlife.screens.exercises.ExerciseScreen
import com.example.fitlife.screens.muscle.MuscleScreen
import com.example.fitlife.screens.workout_plan.WorkoutPlanScreen
import com.example.fitlife.screens.workout_planner.WorkoutPlannerScreen
import com.example.fitlife.screens.workout_planner.parseWorkoutPlanResponse


@Composable
fun FitnessNavGraph(innerPaddingValues: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomePage(innerPaddingValues,navController)
        }
        composable("muscles") {
            MuscleScreen(
                muscleList = listOf(
                    Muscle("Chest", R.drawable.chest_anatomy),
                    Muscle("Back", R.drawable.lats_anatomy_nb),
                    Muscle("Quadriceps", R.drawable.quadriceps_muscles_anatomy_nb),
                    Muscle("Shoulders", R.drawable.deltoids_anatomy_nb),
                    Muscle("Biceps", R.drawable.biceps_anatomy_nb),
                    Muscle("Abs", R.drawable.abs_anatomy_nb),
                    Muscle("Calf", R.drawable.calf_muscles_anatomy_nb),
                    Muscle("Triceps", R.drawable.triceps_anatomy_nb),
                    Muscle("Traps", R.drawable.traps_anatomy_nb),
                    Muscle("Hamstrings", R.drawable.hamstrings_anatomy_nb)

                    // Add more muscle groups as needed
                ),
                navController
            )
        }
        composable("exercises/{muscle}") { backStackEntry ->
            val muscle = backStackEntry.arguments?.getString("muscle")
            ExerciseScreen(
                muscle = muscle ?: "Chest", // Provide a default value
                navController = navController
            )
        }
        composable("workout_planner") {
            WorkoutPlannerScreen(navController)
        }

        composable("workout_plan/{workoutPlanResponse}") { backStackEntry ->
            val workoutPlanResponse = backStackEntry.arguments?.getString("workoutPlanResponse")

            // Ensure workoutPlanResponse is not null
            workoutPlanResponse?.let {
                WorkoutPlanScreen(
                    workoutPlanResponse = it,
                    navController = navController
                )
            }
        }

    }
}
