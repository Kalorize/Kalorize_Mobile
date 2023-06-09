package com.kalorize.kalorizeappmobile.ui.screen.questionnaire

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kalorize.kalorizeappmobile.ui.navigation.Screen

@Composable
fun Questionnaire3(
    navController: NavController
) {
    val options = listOf(
        "Reduce Weight",
        "Increase Muscle",
        "Be Healthy"
    )
    var selectedOption = remember {
        mutableStateOf("")
    }
    val onSelectionChange = { text: String ->
        selectedOption.value = text
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = "Please answer this question\n" +
                    "to know more about you",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Choose your main target",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.Start),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(10.dp))
        options.forEach { text ->
            Button(
                onClick = {
                    onSelectionChange(text)
                },
                colors = if (text == selectedOption.value) {
                    ButtonDefaults.buttonColors(
                        Color.Black
                    )
                } else {
                    ButtonDefaults.buttonColors(
                        Color.White
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(50),
                border = BorderStroke(2.dp, Color(44, 42, 63))
            ) {
                Text(
                    text = text,
                    color = if (text == selectedOption.value) {
                        Color.White
                    } else {
                        Color(44, 42, 63)
                    },
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
        Button(
            enabled = selectedOption.value != "",
            onClick = {
                Log.i("target", selectedOption.value)
                navController.navigate(Screen.QuestionnareSuccess.route)
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFFF94917)
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
fun Questionnaire3Preview() {
    Questionnaire3(navController = rememberNavController())
}

