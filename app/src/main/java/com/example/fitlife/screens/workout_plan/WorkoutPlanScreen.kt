package com.example.fitlife.screens.workout_plan

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fitlife.components.CommonTopAppBar
import com.example.fitlife.screens.workout_planner.parseWorkoutPlanResponse


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WorkoutPlanScreen(workoutPlanResponse: String, navController: NavHostController) {
    val parsedWorkoutPlan = parseWorkoutPlanResponse(workoutPlanResponse)

    Scaffold (
        topBar = {
            CommonTopAppBar(heading = "My Workout Plan", navController = navController)
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(it)
        ) {
            items(parsedWorkoutPlan) { (day, exercises) ->
                // Card for each day
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Heading for the day (e.g., Day 1: Chest, Triceps, Shoulders)
                        Text(
                            text = day,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // List of exercises for the day
                        exercises.forEach { exercise ->
                            Text(
                                text = exercise,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }


}




