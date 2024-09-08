package com.example.fitlife.screens.muscle

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fitlife.R
import com.example.fitlife.data.Muscle
@Composable
fun MuscleCard(muscle: Muscle, modifier: Modifier, navController: NavHostController) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                // Navigate to ExerciseScreen with the muscle name as an argument
                navController.navigate("exercises/${muscle.name}")
            },
        elevation = CardDefaults.cardElevation(16.dp), // Increased elevation for better shadow
        shape = RoundedCornerShape(20.dp) // More rounded corners for a modern look
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient( // Gradient background for cards
                        colors = listOf(
                            colorResource(id = R.color.muscle_card_bg),
                            colorResource(id = R.color.muscle_card_bg_darker)
                        )
                    )
                )
                .padding(20.dp), // More padding for a spacious look
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Apply a rounded shape to the image
            Image(
                painter = painterResource(id = muscle.imageRes),
                contentDescription = muscle.name,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape) // Rounded image for a cleaner look
                    .border(
                        BorderStroke(2.dp, colorResource(id = R.color.muscle_card_border_color)),
                        shape = CircleShape
                    )
                    .padding(bottom = 8.dp) // Add padding between the image and the text
            )
            // Use bolder, larger text for the muscle name
            Text(
                text = muscle.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp // Slightly larger font size
                ),
                color = colorResource(id = R.color.muscle_text_color), // Custom text color for contrast
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


//@Preview(showBackground = true)
@Composable
fun MuscleCardPreview() {
    val navController = rememberNavController()
    MuscleCard(Muscle("Chest", R.drawable.chest_anatomy), Modifier, navController)
}