package com.kalorize.kalorizeappmobile.ui.screen.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kalorize.kalorizeappmobile.data.remote.body.LoginBody
import com.kalorize.kalorizeappmobile.data.remote.response.LoginResponse
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import com.kalorize.kalorizeappmobile.vm.MainViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navHostController: NavHostController
){
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    var response: LoginResponse? = null
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(id = com.kalorize.kalorizeappmobile.R.drawable.markorange),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = "Welcome Back",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Please login to continue",
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(bottom = 32.dp)
        )
        TextField(value = email,
            onValueChange = { input ->  email = input },
            label = { Text(text = "Email")},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )
        TextField(value = password,
            onValueChange = { input ->  password = input },
            label = { Text(text = "Password")},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )
        Text(
            text = "Forgot password",
            style = TextStyle(fontSize = 16.sp),
            color = Color.Blue,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Button(
            onClick = {
            viewModel.loginViewModel.doLogin(LoginBody(email,password))
            viewModel.loginViewModel.login.observe(lifecycle){
                response = it
                if (response != null){
                    if (response!!.data.token.isEmpty()){
                        Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                        navHostController.navigate(Screen.Home.route)
                    }

                }

            } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)) {
            Text(text = "Login")
        }
        TextButton(
            onClick = { /* Navigate to register screen */ },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Register here")
        }

    }
}
