package com.kalorize.kalorizeappmobile.ui.screen.feature.questionnaire

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Questionnaire1(
    navController: NavController
) {
    val options = listOf(
        "Male",
        "Female"
    )
    val selectedOption = remember {
        mutableStateOf("")
    }
    val onSelectionChange = { text: String ->
        selectedOption.value = text
    }
    val ageState = remember {
        mutableStateOf("")
    }
    val heightState = remember {
        mutableStateOf("")
    }
    val weightState = remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val userPreferences = UserPreference(context)
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 40.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = "Please answer this question\n" +
                    "to know more about you",
            style = TextStyle(
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            ),
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

        Row {
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
                    modifier = when (text) {
                        "Male" ->
                            Modifier
                                .offset(0.dp, 0.dp)
                                .height(50.dp)
                                .width(180.dp)
                        else ->
                            Modifier
                                .offset(
                                    (-1 * 1).dp, 0.dp
                                )
                                .height(50.dp)
                                .width(180.dp)
                    },
                    shape = when (text) {
                        "Male" -> RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 0.dp,
                            bottomStart = 30.dp,
                            bottomEnd = 0.dp
                        )
                        else -> RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 30.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 30.dp
                        )
                    },
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
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
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
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
                capitalization = KeyboardCapitalization.None
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
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
            text = "Height (cm)",
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
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
                capitalization = KeyboardCapitalization.None
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
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
            text = "Weight (Kg)",
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
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
                capitalization = KeyboardCapitalization.None
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            onValueChange = {
                weightState.value = it
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Button(
            enabled = selectedOption.value != ""
                    && ageState.value != ""
                    && heightState.value != ""
                    && weightState.value != "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(height = 40.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                Color(0xFFF94917)
            ),
            onClick = {
                Log.i("Gender", selectedOption.value.uppercase())
                Log.i("Age", ageState.value)
                Log.i("Height", heightState.value)
                Log.i("Weight", weightState.value)
                userPreferences.setGender(selectedOption.value.uppercase())
                userPreferences.setAge(ageState.value.toFloat())
                userPreferences.setHeight(heightState.value.toFloat())
                userPreferences.setWeight(weightState.value.toFloat())
                navController.navigate(Screen.Questionnare2.route)
            },
        ) {
            Text(
                text = "Next",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Divider(
                modifier = Modifier
                    .width(165.dp)
                    .align(Alignment.CenterVertically)
                    .padding(end = 10.dp),
            )
            Text(
                text = "Atau"
            )
            Divider(
                modifier = Modifier
                    .width(165.dp)
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp),
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = {
                if (selectedOption.value == "") {
                    Toast.makeText(
                        context,
                        "Choose ur gender",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (ageState.value == "") {
                    Toast.makeText(
                        context,
                        "Enter your age ",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    userPreferences.setAge(ageState.value.toFloat())
                    userPreferences.setGender(selectedOption.value.uppercase())
                    navController.navigate(Screen.Camera.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
            colors = if (selectedOption.value != "" && ageState.value != "") {
                ButtonDefaults.buttonColors(
                    Color.White
                )
            } else {
                ButtonDefaults.buttonColors(
                    Color.Black
                )
            },
            content = {
                Icon(
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                        .padding(end = 10.dp),
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "Camera Icon",
                    tint = if (selectedOption.value != "" && ageState.value != "") {
                        Color.Black
                    } else {
                        Color.White
                    }
                )
                Text(
                    text = "Gunakan Kamera",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = if (selectedOption.value != "" && ageState.value != "") {
                        Color.Black
                    } else {
                        Color.White
                    }
                )
            },
            border = BorderStroke(
                2.dp,
                Color(44, 42, 63)
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Kamu bisa foto wajahmu untuk mendapatkan\n" +
                    "prediksi berat badan dan tinggi badan",
            style = TextStyle(
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Questionnaire1Preview() {
    Questionnaire1(navController = rememberNavController())
}
