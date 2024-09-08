package com.example.fitlife.data

data class WorkoutPlan(
    val days: List<WorkoutDay>,
    val targetMuscle: String,
    val experienceLevel: String
)
