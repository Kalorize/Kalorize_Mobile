package com.kalorize.kalorizeappmobile.ui.screen.questionnaire

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalorize.kalorizeappmobile.data.remote.body.RegisterBody
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import com.kalorize.kalorizeappmobile.ui.theme.WhiteFul

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Questionnaire1() {

    val ageState = remember {
        mutableStateOf("")
    }
    val heightState = remember {
        mutableStateOf("")
    }
    val weightState = remember {
        mutableStateOf("")
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
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Gender",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.Start),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        Switch(
            checked = false,
            onCheckedChange = { /* Handle gender switch */ }
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Age",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 10.dp),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = ageState.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                ageState.value = it
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Height",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.Start),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = heightState.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                heightState.value = it
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Weight",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .align(Alignment.Start),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = weightState.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                weightState.value = it
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Button(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White, // Ubah warna teks di sini
            ),
            content = {
                Text(
                    text = "Register",
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        Divider()
        Spacer(modifier = Modifier.height(28.dp))
        OutlinedButton(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            shape = RoundedCornerShape(50),
//            colors = ButtonDefaults.buttonColors(
//                contentColor = Color.White, // Ubah warna teks di sini
//            ),
            content = {
                Text(
                    text = "Gunakan Kamera",
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(text = "Kamu bisa foto wajahmu untuk mendapatkan\n" +
                "prediksi berat badan dan tinggi badan")
    }
}

@Preview(showBackground = true)
@Composable
fun Questionnaire1Preview() {
    Questionnaire1()
}
