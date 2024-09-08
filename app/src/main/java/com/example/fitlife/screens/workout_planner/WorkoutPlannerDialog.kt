package com.example.fitlife.screens.workout_planner




import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitlife.data.WorkoutPlanRequest


@ExperimentalMaterial3Api
@Composable
fun WorkoutPlannerDialog(onConfirm: (WorkoutPlanRequest) -> Unit) {
    var daysPerWeek by remember { mutableStateOf("") }
    var targetMuscle by remember { mutableStateOf("") }
    var experienceLevel by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { /* Handle dismiss */ },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .padding(16.dp)
            ,
        content = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Custom title bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "Workout Plan Preferences",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Input fields
                TextField(
                    value = daysPerWeek,
                    onValueChange = { daysPerWeek = it },
                    label = { Text(text = "Days per week") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = targetMuscle,
                    onValueChange = { targetMuscle = it },
                    label = { Text(text = "Major target muscle") },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = { /* Handle dismiss */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DropdownMenuItem(onClick = { experienceLevel = "Beginner" }, text = {Text(text = "Beginner")})

                    DropdownMenuItem(
                        onClick = { experienceLevel = "Intermediate" },
                        text = {
                            Text(text = "Intermediate")
                        }
                    )
                    DropdownMenuItem(
                        onClick = { experienceLevel = "Advanced" },
                        text = {
                            Text(text = "Advanced")
                        }
                    )
                }

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Button(
                        onClick = { /* Handle cancel */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            // Handle confirmation and send request to Gemini
                            val request = WorkoutPlanRequest(
                                daysPerWeek = daysPerWeek.toInt(),
                                targetMuscle = targetMuscle,
                                experienceLevel = experienceLevel
                            )
                            onConfirm(request)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun WorkoutPlannerDialogPreview() {
    WorkoutPlannerDialog({})
}

//fun sendWorkoutPlanRequestToGemini(request: WorkoutPlanRequest) {
//    val gson = Gson()
//    val requestJson = gson.toJson(request)
//
//    // Replace with your Gemini API endpoint and authentication
//    val response = GeminiAPI.sendPrompt(requestJson)
//
//    // Handle the response and parse the workout plan
//    // ...
//}