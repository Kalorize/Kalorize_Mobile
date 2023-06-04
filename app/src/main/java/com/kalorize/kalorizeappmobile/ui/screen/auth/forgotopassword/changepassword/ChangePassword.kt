package com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.changepassword

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.data.remote.body.UpdatePassBody
import com.kalorize.kalorizeappmobile.data.remote.response.SimpleResponse
import com.kalorize.kalorizeappmobile.vm.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val password = remember {
        mutableStateOf("")
    }
    val confirmPassword = remember {
        mutableStateOf("")
    }
    val passwordVisibility = remember {
        mutableStateOf(false)
    }
    val confirmPasswordVisibility = remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val userPreferences = UserPreference(context)
    val email = userPreferences.takeEmail()
    val lifecycle = LocalLifecycleOwner.current
    var response: SimpleResponse? = null
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 40.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier.clickable {
                navController.popBackStack(
                    route = Screen.ChangePassword.route,
                    inclusive = true
                )
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
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                text = "Change Your Password"
            )
            Image(
                painter = painterResource(id = R.drawable.changeurpassword),
                contentDescription = "Change Password",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                text = "New Password"
            )
            TextField(
                onValueChange = {
                    password.value = it
                },
                singleLine = true,
                value = password.value,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()

                    }
                ),
                shape = RoundedCornerShape(20.dp),
                placeholder = { Text("Enter New Password") },
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
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
                    IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                        Icon(painter = image, contentDescription = "password Toggle")
                    }
                }
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 10.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                text = "Re-type Password"
            )
            TextField(
                onValueChange = {
                    confirmPassword.value = it
                },
                singleLine = true,
                value = confirmPassword.value,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                shape = RoundedCornerShape(20.dp),
                placeholder = { Text("Confirm New Password") },
                modifier = Modifier
                    .fillMaxWidth(),
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                visualTransformation = if (confirmPasswordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (confirmPasswordVisibility.value) {
                        painterResource(id = R.drawable.showpassword)
                    } else {
                        painterResource(id = R.drawable.hidepassword)
                    }
                    IconButton(onClick = {
                        confirmPasswordVisibility.value = !confirmPasswordVisibility.value
                    }) {
                        Icon(painter = image, contentDescription = "password Toggle")
                    }
                }
            )
            Button(
                onClick = {
                    if (password.value == "" || confirmPassword.value == "") {
                        Toast.makeText(context, "Input is empty", Toast.LENGTH_SHORT).show()
                    } else if (password.value != confirmPassword.value) {
                        Toast.makeText(context, "Input is not the same", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        viewModel.changePasswordViewModel.doUpdatePassword(
                            UpdatePassBody(
                                email = email!!,
                                newpassword = password.value,
                                renewpassword = password.value
                            )
                        )
                        viewModel.changePasswordViewModel.updatePasswordResponse.observe(lifecycle) {
                            response = it
                            if (response!!.status == "success") {
                                Toast.makeText(
                                    context,
                                    "Password successfully changed",
                                    Toast.LENGTH_SHORT
                                ).show()
                                userPreferences.bringChangedPassword(password.value)
                                navController.navigate(route = Screen.GetStartedChangePassword.route) {
                                    // Pop up to login screen
                                    popUpTo(route = Screen.Login.route) {
                                        inclusive = false
                                    }
                                }
                            } else {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(height = 40.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF94917)
                ),
            ) {
                Text(
                    text = "Confirm",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    ),
                )
            }
        }
    }
}