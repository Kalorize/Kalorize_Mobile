package com.kalorize.kalorizeappmobile.ui.screen.auth.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.data.remote.body.LoginBody
import com.kalorize.kalorizeappmobile.data.remote.response.LoginResponse
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import com.kalorize.kalorizeappmobile.vm.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navHostController: NavHostController
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    val userPreferences = UserPreference(context)
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val passwordVisibility = remember {
        mutableStateOf(false)
    }
    val loginResponse = remember {
        mutableStateOf(LoginResponse(status = "", data = null))
    }
    val loading = remember {
        mutableStateOf(false)
    }
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.markorange),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier.padding(bottom = 50.dp, top = 50.dp)
            )
            Text(
                text = "Welcome Back 👋",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start),
                textAlign = TextAlign.Start
            )
            Text(
                text = "Please login to continue",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .align(Alignment.Start),
                textAlign = TextAlign.Start
            )
            Text(
                text = "Email",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.Start),
                textAlign = TextAlign.Start
            )

            TextField(
                value = email.value,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Email,
                    capitalization = KeyboardCapitalization.None
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                onValueChange = {
                    email.value = it
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
            Text(
                text = "Password",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.Start),
                textAlign = TextAlign.Start
            )

            TextField(
                value = password.value,
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
                onValueChange = {
                    password.value = it
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisibility.value) {
                        painterResource(id = R.drawable.showpassword)
                    } else {
                        painterResource(id = R.drawable.hidepassword)
                    }
                    IconButton(
                        onClick = {
                            passwordVisibility.value = !passwordVisibility.value
                        }
                    ) {
                        Icon(painter = image, contentDescription = "password Toggle")
                    }
                }
            )
            Text(
                text = "Forgot password",
                style = TextStyle(fontSize = 16.sp),
                color = Color(249, 73, 23),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.End)
                    .clickable {
                        navHostController.navigate(route = Screen.ReInputEmail.route)
                    }
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
                    loading.value = true
                    viewModel.loginViewModel.doLogin(LoginBody(email.value, password.value))
                    viewModel.loginViewModel.login.observe(lifecycle) {
                        loginResponse.value = it
                    }
                },
            ) {
                Text(text = "Login")
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Don’t have an account?",
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier
                        .clickable {
                            navHostController.navigate(Screen.Register.route)
                        },
                    text = " Register here",
                    color = Color(249, 73, 23)
                )

            }

            LaunchedEffect(key1 = loginResponse.value) {
                Log.d("Check response", loginResponse.toString())
                if (loginResponse.value.data != null) {
                    loading.value = false
                    if (loginResponse.value.data!!.token.isNotEmpty()) {
                        Toast.makeText(
                            context,
                            "Sukses",
                            Toast.LENGTH_SHORT
                        ).show()
                        userPreferences.setUser(loginResponse.value.data!!)
                        viewModel.loginViewModel.cleanLogin()
                        if (loginResponse.value.data!!.user.activity == null){
                            navHostController.navigate(Screen.Questionnare1.route) {
                                popUpTo(Screen.Login.route) {
                                    inclusive = true
                                }
                            }
                        }else{
                            navHostController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) {
                                    inclusive = true
                                }
                            }
                        }

                    } else {
                        Toast.makeText(
                            context,
                            "Login gagal",
                            Toast.LENGTH_SHORT
                        ).show()
                        loading.value = false
                    }
                }
            }
        }
        if (loading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(80.dp),
                color = Color(0xFFF94917),
                strokeWidth = 6.dp
            )
        }
    }


}