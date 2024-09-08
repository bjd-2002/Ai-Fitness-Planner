package com.example.fitlife.screens.workout_planner

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fitlife.Constants
import com.example.fitlife.components.CommonTopAppBar
import com.example.fitlife.components.showToast
import com.example.fitlife.data.WorkoutPlanRequest
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun WorkoutPlannerScreen(navController: NavHostController) {
    var days by remember { mutableStateOf("") }
    var selectedMuscle by remember { mutableStateOf("") }
    var experienceLevel by remember { mutableStateOf("") }
    var other by remember { mutableStateOf("") }
    var showMuscleDialog by remember { mutableStateOf(false) } // Ensure this state is toggled
    var isLoading by remember { mutableStateOf(false) }
    val muscles = listOf("Chest", "Back", "Legs", "Upper Body", "Arms", "Abs")

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CommonTopAppBar(heading = "Workout Planner", navController = navController)
        },
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                if (isLoading) {
                    CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(16.dp)
                ) {
                    // First Question: Days per week input
                    Text("1)How many days can you workout in a week?")
                    TextField(
                        value = days,
                        onValueChange = { days = it },
                        placeholder = { Text("Enter 1-7 number only") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Second Question: Target Muscle Input
                    Text("2)What is your target muscle?")
                    TextField(
                        value = selectedMuscle,
                        onValueChange = {}, // No changes here, controlled by dialog
                        placeholder = { Text("Select target muscle") },
                        readOnly = true, // Make it read-only to prevent text input
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    Button(
                        onClick = { showMuscleDialog = true},
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 10.dp)
                    ) {
                        Text(text = "Choose your target muscle")
                    }

                    // Muscle selection dialog
                    if (showMuscleDialog) {
                        Log.d("AlertDialog", "ALertDialog clicked")
                        AlertDialog(
                            onDismissRequest = { showMuscleDialog = false }, // Close when dismissed
                            text = {
                                Column {
                                    Text("Select Muscle", modifier = Modifier.padding(bottom = 8.dp))
                                    LazyColumn {
                                        items(muscles) { muscle ->
                                            Text(
                                                text = muscle,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        selectedMuscle =
                                                            muscle // Set the selected muscle
                                                        showMuscleDialog = false // Close dialog
                                                    }
                                                    .padding(16.dp)
                                            )
                                        }
                                    }
                                }
                            },
                            confirmButton = {} // Optional confirm button (can be omitted)
                        )
                    }

                    // Third Question: Experience Level
                    Text("3)What is your experience level?")
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = experienceLevel == "Beginner(<1 year)",
                                onClick = {
                                    Log.d("AlertDialog", "Beginner clicked")
                                    experienceLevel = "Beginner(<1 year)"
                                }
                            )
                            Text("Beginner(<1 year)", modifier = Modifier.padding(start = 8.dp))
                        }

                        Spacer(modifier = Modifier.width(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = experienceLevel == "Intermediate(1-3 years)",
                                onClick = { experienceLevel = "Intermediate(1-3 years)" }
                            )
                            Text("Intermediate(1-3 years)", modifier = Modifier.padding(start = 8.dp))
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            RadioButton(
                                selected = experienceLevel == "Advanced(3+ years)",
                                onClick = { experienceLevel = "Advanced(3+ years)" }
                            )
                            Text("Advanced(3+ years)", modifier = Modifier.padding(start = 8.dp))
                        }
                    }

                    Text("4)Do you want to add anything more")
                    TextField(
                        value = other,
                        onValueChange = { other = it },
                        placeholder = { Text("For eg. I will like a separate arm day") },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    // Button to submit input
                    Button(
                        onClick = {

                            val isValid = validateInputs(days, selectedMuscle, experienceLevel, context)
                            if (isValid) {
                                val request = WorkoutPlanRequest(
                                    daysPerWeek = days.toInt(),
                                    targetMuscle = selectedMuscle,
                                    experienceLevel = experienceLevel,
                                    other = other
                                )
                                isLoading = true
                                showToast(context, "Your workout plan is being created")

                                sendWorkoutPlanRequestToGemini(request, navController, scope, context, isLoading)
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 16.dp)
                    ) {
                        Text("Submit")
                    }
//                Box(
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    if (isLoading) {
//                        CircularProgressIndicator(
//                            modifier = Modifier.align(Alignment.Center)
//                        )
//                    }
//                }

                }
            }
            }

    )
}

// Validation logic
fun validateInputs(days: String, muscle: String, experience: String, context: Context): Boolean {
    val daysInt = days.toIntOrNull()
    return if (daysInt == null || daysInt !in 1..7 || muscle.isEmpty() || experience.isEmpty()) {
        showToast(context, "Please fill all input fields with valid inputs")
        false
    } else {
        true
    }
}

fun sendWorkoutPlanRequestToGemini(
    request: WorkoutPlanRequest,
    navController: NavHostController,
    scope: CoroutineScope,
    context: Context,
    isLoading: Boolean
) {
    val generativeModel = GenerativeModel(
        modelName = "gemini-pro", // Replace with your desired model name
        apiKey = Constants.apikey // Replace with your API key
    )

    val prompt = "Create a workout plan for ${request.daysPerWeek} days per week for full body but little bit more focus on ${request.targetMuscle} for a ${request.experienceLevel} level.${request.other} .And just give the response in this format " +
            "**Day 1: Muscles worked**\n" +
            "- Exercise 1 name: sets and reps\n" +
            "- Exercise 2 name: sets and reps\n" +
            "- Exercise 3 name: sets and reps\n" +
            "- Exercise 4 name: sets and reps\n" +
            "- Exercise 5 name: sets and reps\n" +
            "- Exercise 6 name: sets and reps\n" +
            "**Day 2: Muscles worked**\n" +
            "- Exercise 1 name: sets and reps\n" +
            "- Exercise 2 name: sets and reps\n" +
            "- Exercise 3 name: sets and reps\n" +
            "- Exercise 4 name: sets and reps\n" +
            "- Exercise 5 name: sets and reps\n" +
            "- Exercise 6 name: sets and reps\n" +
            "if the day is for rest then also same exact syntax like this" +
            "**Day number: Rest and Recovery or active rest day" +
            "- Activities to do like walk or anything you suggest"+
            "and so on upto seven days"


    scope.launch {
        try {
            val response = generativeModel.generateContent(
                prompt = prompt,
                // ... other parameters as needed
            )
//            isLoading = false
            Log.d("Gemini_response", response.text.toString())
            navController.navigate("home")

            val workoutPlan = parseWorkoutPlanResponse(response.text.toString())
            Log.d("Gemini_response", workoutPlan.toString())
            navController.navigate("workout_plan/${response.text.toString()}")
//            })
        } catch (e: Exception) {
            // Handle errors
            Log.d("Gemini_response_error", e.message.toString())
            showToast(context, "Error: ${e.message}")
        }
    }
}

fun parseWorkoutPlanResponse(response: String): List<Pair<String, List<String>>>{
    val lines = response.lines()
    val parsedPlan = mutableListOf<Pair<String, List<String>>>()

    var currentDayTitle = ""
    var currentExercises = mutableListOf<String>()

    lines.forEach { line ->
        when {
            line.startsWith("**") -> {
                if (currentDayTitle.isNotBlank()) {
                    parsedPlan.add(currentDayTitle to currentExercises)
                    currentExercises = mutableListOf<String>()
                }
                currentDayTitle = line.removeSurrounding("**")
            }
            line.startsWith("-") -> {
                currentExercises.add(line.removePrefix("-").trim())
            }
        }
    }

    if (currentDayTitle.isNotBlank() && currentExercises.isNotEmpty()) {
        parsedPlan.add(currentDayTitle to currentExercises)
    }

    return parsedPlan
}


// Data class to store each day and its exercises
data class DayWorkout(
    val dayTitle: String,
    val exercises: List<String>
)




