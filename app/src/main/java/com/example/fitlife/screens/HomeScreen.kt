package com.example.fitlife.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fitlife.R
import com.example.fitlife.components.CommonTopAppBar
import com.example.fitlife.components.FeatureCard
import com.example.fitlife.data.FeatureCardData
import com.example.fitlife.screens.workout_planner.WorkoutPlannerDialog

@Composable
fun HomePage(innerpaddingValues: PaddingValues, navController: NavHostController) {
    Scaffold(
        topBar = {
            CommonTopAppBar(
                modifier = Modifier.height(55.dp),
                heading = "Welcome",
                navController = navController
            )

        }
    ) { paddingValues -> // Provide padding from the Scaffold
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 10.dp)
        ) {
            items(items = listOf(
                FeatureCardData("Exercises", "Start your fitness journey today with us, Get a whole range of muscle for each muscle group.", R.drawable.exercise_1),
                FeatureCardData("Workout Planner", "Create your personalized workout plan", R.drawable.workout_planner_image),
                FeatureCardData("Meal Planner", "Get your fully customized meal plans according to your needs and protein requirements", R.drawable.meal_planner_image),
                FeatureCardData("Your Workout Plan", "Your Fully Customized Workout Plan is Here", R.drawable.workout_plan_image)
            )) { cardData ->
                FeatureCard(
                    title = cardData.title,
                    description = cardData.description,
                    imageRes = cardData.imageRes,
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        if (cardData.title == "Exercises") {
                            navController.navigate("muscles")
                        }
                        if (cardData.title == "Workout Planner") {
                            navController.navigate("workout_planner")
                        }
                    },

                )
            }
        }
    }
}

