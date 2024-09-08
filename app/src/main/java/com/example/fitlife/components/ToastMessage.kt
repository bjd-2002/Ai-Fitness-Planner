package com.example.fitlife.components

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape

fun showToast(context: Context, message: String) {
    val toast = Toast(context)
    toast.duration = Toast.LENGTH_SHORT

    // Create a simple TextView for the toast message
    val textView = TextView(context).apply {
        text = message
        setPadding(32, 20, 32, 20) // Increase padding for a larger background area
        textSize = 18f // Maintain a slightly larger text size
        setTextColor(Color.parseColor("#333333")) // Dark text color
        gravity = Gravity.CENTER
        background = ShapeDrawable().apply {
            paint.color = Color.parseColor("#F5F5F5") // Slightly darker background color
            shape = RoundRectShape(
                floatArrayOf(30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f), // More rounded corners
                null, null
            )
        }
        elevation = 8f // Add some elevation (shadow)
    }

    // Set the custom view to the toast
    toast.view = textView
    toast.show()
}


