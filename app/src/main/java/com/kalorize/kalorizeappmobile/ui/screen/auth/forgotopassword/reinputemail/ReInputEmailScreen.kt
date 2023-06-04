package com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.reinputemail

import com.kalorize.kalorizeappmobile.R
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.data.remote.body.RequestOtpBody
import com.kalorize.kalorizeappmobile.data.remote.response.SimpleResponse
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import com.kalorize.kalorizeappmobile.vm.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReInputEmailScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val email = remember {
        mutableStateOf("")
    }
    var response: SimpleResponse? = null
    val focusManager = LocalFocusManager.current
    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current
    val userPreferences = UserPreference(context)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
        Text(
            modifier = Modifier
                .padding(top = 30.dp)
                .align(alignment = Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            text = "Don't be sad 😥"
        )
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 16.sp,
            ),
            text = "Change your password here"
        )
        Image(
            painter = painterResource(id = R.drawable.privacy),
            contentDescription = "Privacy",
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
            text = "Input your email here"
        )
        TextField(
            onValueChange = {
                email.value = it
            },
            singleLine = true,
            value = email.value,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()

                }
            ),
            shape = RoundedCornerShape(20.dp),
            placeholder = { Text("Enter Email") },
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),

            )
        Button(
            onClick = {
                if (email.value.isEmpty()) {
                    Toast.makeText(context, "Email is Empty", Toast.LENGTH_SHORT).show()
                } else if (!isEmailValid(email.value)) {
                    Toast.makeText(context, "Wrong Input", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.reInputEmailViewModel.doRequestOtp(RequestOtpBody(email.value))
                    viewModel.reInputEmailViewModel.otpResponse.observe(lifecycle){
                        response = it
                        if (response!!.status == "success"){
                            Toast.makeText(context, "The OTP code has been sent, please check your email", Toast.LENGTH_SHORT).show()
                            userPreferences.bringEmail(email.value)
                            navController.navigate(Screen.Otp.route)
                        }else{
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
                Color(0xFFF94917)
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

fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}