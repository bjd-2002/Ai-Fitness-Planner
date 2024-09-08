package com.example.fitlife.screens.exercises

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fitlife.R
import com.example.fitlife.components.CommonTopAppBar
import com.example.fitlife.data.Exercise

@Composable
fun ExerciseScreen(muscle: String, navController: NavHostController) {
    val exercises = getExercisesForMuscle(muscle)

    Scaffold(
        topBar = {
            CommonTopAppBar(
                heading = "$muscle Exercises",
                navController = navController
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(exercises) { exercise ->
                ExerciseCard(
                    exercise = exercise,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
        }
    }
}

fun getExercisesForMuscle(muscle: String): List<Exercise> {
    // Return exercises based on the muscle group
    return when (muscle) {
        "Chest" -> listOf(
            Exercise("Bench Press", 3, 10, R.drawable.bench_press_image),
            Exercise("Push-ups", 4, 15, R.drawable.pushups_image),
            Exercise("Dips", 4, 15, R.drawable.dips_image),
            Exercise("Cable Fly", 4, 15, R.drawable.cable_fly_image),
            // Add more chest exercises
        )
        "Back" -> listOf(
            Exercise("Pull-ups", 3, 8, R.drawable.pullups_image),
            Exercise("Rows", 4, 12, R.drawable.rows_image)
            // Add more back exercises
        )
        // Add other muscle groups
        else -> emptyList()
    }
}