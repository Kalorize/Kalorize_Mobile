package com.kalorize.kalorizeappmobile.ui.screen.feature.questionnaire

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.ui.navigation.Screen

@Composable
fun Questionnaire2(
    navController: NavController
) {
    val options = listOf(
        "Extreme (36 hours gym)",
        "Hard (24 hours gym)",
        "Medium (12 hours gym)",
        "Easy (6 hours gym)"
    )
    var selectedOption = remember {
        mutableStateOf("")
    }
    val onSelectionChange = { text: String ->
        selectedOption.value = text
    }
    val context = LocalContext.current
    val userPreferences = UserPreference(context)
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 40.dp, end = 16.dp),
    ) {
        Row(
            modifier = Modifier.clickable {
                navController.popBackStack()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_orange),
                contentDescription = null,
                tint = Color(red = 243, green = 73, blue = 23)
            )
            Text(
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                ),
                text = "Back",
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Please answer this question\n" +
                    "to know more about you",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
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
        Spacer(modifier = Modifier.height(12.dp))

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
                Log.i("activity", selectedOption.value.replace("\\s*\\([^()]*\\)\\s*".toRegex(), "").uppercase())
                userPreferences.setActivity(selectedOption.value.replace("\\s*\\([^()]*\\)\\s*".toRegex(), "").uppercase())
                navController.navigate(Screen.Questionnare3.route)
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
fun Questionnaire2Preview() {
    Questionnaire2(navController = rememberNavController())
}

