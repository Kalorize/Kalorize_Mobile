package com.kalorize.kalorizeappmobile.ui.screen.auth.login

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.kalorize.kalorizeappmobile.data.remote.body.LoginBody
import com.kalorize.kalorizeappmobile.data.remote.response.LoginResponse
import com.kalorize.kalorizeappmobile.vm.MainViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
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
        modifier = modifier
    ) {

        Text(text = "Login Screen")
        TextField(value = email,
            onValueChange = { input ->  email = input },
            label = { Text(text = "email")}
        )
        TextField(value = password,
            onValueChange = { input ->  password = input },
            label = { Text(text = "email")}
        )
        Button(onClick = {
            viewModel.loginViewModel.doLogin(LoginBody(email,password))
            viewModel.loginViewModel.login.observe(lifecycle){
                response = it
                if (response != null){
                    Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                }

            }
        }) {
            Text(text = "Login")
        }

    }
}
