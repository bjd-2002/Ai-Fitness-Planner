package com.example.fitlife.screens.exercises

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitlife.R
import com.example.fitlife.data.Exercise

@Composable
fun ExerciseCard(exercise: Exercise, modifier: Modifier = Modifier) {

    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // First Column
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "${exercise.sets} sets x ${exercise.reps} reps",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Second Column
            Image(
                painter = painterResource(id = exercise.imageRes),
                contentDescription = exercise.name,
                modifier = Modifier
                    .size(120.dp)
//                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseCardPreview() {
    ExerciseCard(
        Exercise("Cable Fly", 4, 15, R.drawable.cable_fly_image),
    )
}