package com.kalorize.kalorizeappmobile.ui.screen.questionnaire

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kalorize.kalorizeappmobile.ui.theme.WhiteFul

@Composable
fun Questionnaire1() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Gender"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Switch(
            checked = false,
            onCheckedChange = { /* Handle gender switch */ }
        )
        Spacer(modifier = Modifier.height(28.dp))
        OutlinedTextField(
            value = "",
            onValueChange = { /* Handle age input change */ },
            label = { Text(text = "Age") }
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = "",
            onValueChange = { /* Handle weight input change */ },
            label = { Text(text = "Weight") }
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = "",
            onValueChange = { /* Handle height input change */ },
            label = { Text(text = "Height") }
        )
        Spacer(modifier = Modifier.height(28.dp))
        Button(
            onClick = { /* Handle Next button click */ },
            colors = ButtonDefaults.buttonColors(contentColor = Color(0xFFF94917))
        ) {
            Text(text = "Next", color = WhiteFul)
        }
        Spacer(modifier = Modifier.height(28.dp))
        Divider()
        Spacer(modifier = Modifier.height(28.dp))
        OutlinedButton(
            onClick = { /* Handle Camera button click */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Gunakan Camera")
        }
        Spacer(modifier = Modifier.height(28.dp))
        Text(text = "Ini adalah deskripsi")
    }
}

@Preview(showBackground = true)
@Composable
fun Questionnaire1Preview() {
    Questionnaire1()
}
