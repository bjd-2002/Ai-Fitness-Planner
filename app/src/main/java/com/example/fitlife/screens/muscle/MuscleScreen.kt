package com.example.fitlife.screens.muscle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitlife.R
import com.example.fitlife.components.CommonTopAppBar
import com.example.fitlife.data.Muscle

@Composable
fun MuscleScreen(muscleList: List<Muscle>, navController: NavHostController) {
    Scaffold(
        topBar = {
            CommonTopAppBar(
                heading = "Muscles",
                navController = navController
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.muscle_screen_background_start),
                            colorResource(id = R.color.muscle_screen_background_end)
                        )
                    )
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(muscleList.chunked(2)) { muscleRow ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp), // Add vertical padding between rows
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        muscleRow.forEach { muscle ->
                            MuscleCard(
                                muscle = muscle,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 12.dp), // Horizontal padding for card spacing
                                navController
                            )
                        }
                    }
                }
            }
        }
    }
}



//@Preview(showBackground = true)
@Composable
fun PreviewMuscleScreen() {
    val navController = rememberNavController()
    val muscles = listOf(
        Muscle("Chest", R.drawable.chest_anatomy),
        Muscle("Back", R.drawable.lats_anatomy_nb),
        Muscle("Legs", R.drawable.quadriceps_muscles_anatomy_nb),
        Muscle("Shoulders", R.drawable.deltoids_anatomy_nb),
        Muscle("Arms", R.drawable.biceps_anatomy_nb),
        Muscle("Abs", R.drawable.abs_anatomy_nb)
    )
    MuscleScreen(muscleList = muscles, navController)
}




