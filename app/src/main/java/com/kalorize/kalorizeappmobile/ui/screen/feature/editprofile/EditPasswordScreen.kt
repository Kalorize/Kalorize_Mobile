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
import com.kalorize.kalorizeappmobile.data.remote.response.LoginData
import com.kalorize.kalorizeappmobile.data.remote.response.LoginUser
import com.kalorize.kalorizeappmobile.data.remote.response.RecommendationResponse
import com.kalorize.kalorizeappmobile.vm.MainViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPasswordScreen(
    navController: NavController,
    viewModel: MainViewModel
) {

    val passwordState = remember {
        mutableStateOf("")
    }
    val passwordVisibility = remember {
        mutableStateOf(false)
    }
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
                Text(text = "Edit Password")
            }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Change Password",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.Start)
            )
            TextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                    capitalization = KeyboardCapitalization.None
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    val image = if (passwordVisibility.value) {
                        painterResource(id = R.drawable.showpassword)
                    } else {
                        painterResource(id = R.drawable.hidepassword)
                    }
                    IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                        Icon(painter = image, contentDescription = "password Toggle")
                    }
                }
            )

            Button(
                enabled = passwordState.value != "",
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
                    Log.i("Password", passwordState.value)
                    val password = passwordState.value.toRequestBody("text/plain".toMediaType())
                    viewModel.homeViewModel.editPassword(
                        token = user.token,
                        password = password,
                    )
                    viewModel.homeViewModel.editPassword.observe(lifecycle) {
                        recommendationResponse.value = it
                        if (recommendationResponse.value.status == "success") {
                            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                            userPreferences.setUser(
                                LoginData(
                                    token = user.token,
                                    user = LoginUser(
                                        password = passwordState.value,
                                        id = user.user.id,
                                        email = user.user.email,
                                        name = user.user.name,
                                        gender = user.user.gender,
                                        picture = user.user.picture,
                                        weight = user.user.weight,
                                        age = user.user.age,
                                        height = user.user.height,
                                        activity = user.user.activity,
                                        target = user.user.target
                                    )
                                )
                            )
                            isLoading.value = false
                            navController.popBackStack()
                        } else {
                            isLoading.value = false
                            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
            ) {
                Text(text = "Edit Password")
            }
        }
    }
}