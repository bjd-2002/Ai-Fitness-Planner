package com.example.fitlife.data

data class WorkoutPlanRequest(
    val daysPerWeek: Int,
    val targetMuscle: String,
    val experienceLevel: String,
    val other: String? = null
)
