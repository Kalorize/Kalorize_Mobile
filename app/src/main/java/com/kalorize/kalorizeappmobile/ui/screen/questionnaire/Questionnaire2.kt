package com.kalorize.kalorizeappmobile.ui.screen.questionnaire

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Questionnaire2() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = "Please answer this question\n" +
                    "to know more about you",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Choose your preference (in a week)",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.Start),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(28.dp))
        OutlinedButton(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
            content = {
                Text(
                    text = "Extreme (36 hours gym)",
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
            content = {
                Text(
                    text = "Hard (24 hours gym)",
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
            content = {
                Text(
                    text = "Medium (12 hours gym)",
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
            content = {
                Text(
                    text = "Easy (6 hours gym)",
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        Button(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White, // Ubah warna teks di sini
            ),
            content = {
                Text(
                    text = "Next",
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Questionnaire2Preview() {
    Questionnaire2()
}
