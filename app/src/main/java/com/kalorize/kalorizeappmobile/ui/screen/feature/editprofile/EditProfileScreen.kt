package com.kalorize.kalorizeappmobile.ui.screen.feature

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.data.remote.body.LoginBody
import com.kalorize.kalorizeappmobile.data.remote.response.LoginData
import com.kalorize.kalorizeappmobile.data.remote.response.LoginUser
import com.kalorize.kalorizeappmobile.data.remote.response.RecommendationResponse
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import com.kalorize.kalorizeappmobile.vm.MainViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val userPreferences = UserPreference(context)
    val lifecycle = LocalLifecycleOwner.current
    val user = userPreferences.getUser()
    val getMeResponse = remember {
        mutableStateOf(RecommendationResponse(null, ""))
    }
    val isDataLoaded = remember {
        mutableStateOf(false)
    }

    viewModel.homeViewModel.getRecommendation(user.token)
    viewModel.homeViewModel.recommendation.observe(lifecycle) {
        getMeResponse.value = it
    }

    LaunchedEffect(key1 = getMeResponse.value, block = {
        if (getMeResponse.value.data != null) {
            isDataLoaded.value = true
        }
    })

    if (isDataLoaded.value) {
        editProfileDetail(navController, getMeResponse.value, viewModel, user.token)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(80.dp),
                color = Color(0xFFF94917),
                strokeWidth = 6.dp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editProfileDetail(
    navController: NavController,
    userDetail: RecommendationResponse,
    viewModel: MainViewModel,
    token: String
) {
    val fullNameState = remember {
        mutableStateOf(userDetail.data!!.user.name)
    }
    val ageState = remember {
        mutableStateOf("")
    }
    val weightState = remember {
        mutableStateOf("")
    }
    val heightState = remember {
        mutableStateOf("")
    }
    val selectedOption = remember {
        mutableStateOf(userDetail.data!!.user.gender)
    }
    val onSelectionChange = { text: String ->
        selectedOption.value = text
    }
    val options = listOf(
        "Male",
        "Female"
    )
    val focusManager = LocalFocusManager.current
    val lifecycle = LocalLifecycleOwner.current
    val isLoading = remember {
        mutableStateOf(false)
    }
    val recommendationResponse = remember {
        mutableStateOf(
            RecommendationResponse(null, "")
        )
    }

    val context = LocalContext.current
    val userPreferences = UserPreference(context)
    val user = userPreferences.getUser()

    if (isLoading.value) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(80.dp),
                color = Color(0xFFF94917),
                strokeWidth = 6.dp
            )
        }
    }
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        TopAppBar(
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_orange),
                    contentDescription = "Back",
                    tint = Color(red = 243, green = 73, blue = 23),
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        }
                )
            },
            title = {
                Text(text = "Edit Profile")
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Full Name",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.Start)
            )

            TextField(
                value = fullNameState.value,
                onValueChange = { fullNameState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.None
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Text(
                text = "Age",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.Start)
            )

            TextField(
                value = ageState.value,
                onValueChange = { ageState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
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
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Text(
                text = "Gender",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.Start)
            )

            Row(
                modifier = Modifier
                    .padding(bottom = 20.dp),
            ) {
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

            Text(
                text = "Weight",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.Start)
            )

            TextField(
                value = weightState.value,
                onValueChange = { weightState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
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
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Text(
                text = "Height",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.Start)
            )

            TextField(
                value = heightState.value,
                onValueChange = { heightState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
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
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(height = 40.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFF94917)
                ),
                onClick = {
                    isLoading.value = true
                    Log.i("Full name", fullNameState.value)
                    Log.i("Age", ageState.value.toString())
                    Log.i("Gender", selectedOption.value)
                    Log.i("Weight", weightState.value.toString())
                    Log.i("Height", heightState.value.toString())
                    val name = fullNameState.value.toRequestBody("text/plain".toMediaType())
                    val gender = selectedOption.value.uppercase().toRequestBody("text/plain".toMediaType())
                    viewModel.homeViewModel.editProfile(
                        token = token,
                        name = name,
                        gender = gender,
                        age = ((if (ageState.value != "") {
                            ageState.value.toFloat()
                        } else {
                            user.user.age
                        })!!),
                        weight = ((if (weightState.value != "") {
                            weightState.value.toFloat()
                        } else {
                            user.user.weight
                        })!!),
                        height = ((if (heightState.value != "") {
                            heightState.value.toFloat()
                        } else {
                            user.user.height
                        })!!)
                    )
                    viewModel.homeViewModel.editProfile.observe(lifecycle) {
                        recommendationResponse.value = it
                        if (recommendationResponse.value.status == "success") {
                            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                            userPreferences.setUser(
                                LoginData(
                                    token = token,
                                    user = LoginUser(
                                        password = user.user.password,
                                        id = user.user.id,
                                        email = user.user.email,
                                        name = fullNameState.value,
                                        gender = selectedOption.value.uppercase(),
                                        picture = null,
                                        weight = ((if (weightState.value != "") {
                                            weightState.value.toFloat()
                                        } else {
                                            user.user.weight
                                        })!!),
                                        age =  ((if (ageState.value != "") {
                                            ageState.value.toFloat()
                                        } else {
                                            user.user.age
                                        })!!),
                                        height = ((if (heightState.value != "") {
                                            heightState.value.toFloat()
                                        } else {
                                            user.user.height
                                        })!!),
                                        activity = user.user.activity,
                                        target = user.user.target
                                    )
                                )
                            )
                            navController.navigate(Screen.UserDetail.route){
                                popUpTo(navController.graph.id){
                                    inclusive = true
                                }
                            }
                        } else {
                            isLoading.value = false
                            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
            ) {
                Text(text = "Edit")
            }
        }
    }
}