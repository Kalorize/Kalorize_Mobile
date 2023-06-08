package com.kalorize.kalorizeappmobile.ui.screen.questionnaire

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalorize.kalorizeappmobile.R

@Composable
fun QuestionnaireSuccess() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = "Great!!\n" +
                    "You’re all set",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Image(
            painter = painterResource(R.drawable.sukses), // Replace with your actual image resource
            contentDescription = "Success Image",
            modifier = Modifier.fillMaxWidth()
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
                    text = "Let's Start",
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionnaireSuccessPreview() {
    QuestionnaireSuccess()
}
