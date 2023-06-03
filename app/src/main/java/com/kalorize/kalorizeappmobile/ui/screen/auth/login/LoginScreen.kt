package com.kalorize.kalorizeappmobile.ui.screen.auth.login

import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
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
){
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    var response: LoginResponse? = null
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
            modifier = Modifier.padding(bottom = 50.dp , top = 50.dp)
        )
        Text(
            text = "Welcome Back",
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
                val image = if (passwordVisibility.value){
                    painterResource(id = R.drawable.showpassword)
                }else {
                    painterResource(id = R.drawable.hidepassword)
                }
                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                    Icon(painter = image, contentDescription = "password Toggle")
                }
            }


        )

        Text(
            text = "Forgot password",
            style = TextStyle(fontSize = 16.sp),
            color = Color.Blue,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.End)
        )

        Button(
            onClick = {

            viewModel.loginViewModel.doLogin(LoginBody(email.value,password.value))
            viewModel.loginViewModel.login.observe(lifecycle){
                response = it
                Log.d("Check response" , response.toString())
                if (response != null){
                    if (response!!.data.token.isEmpty()){
                        Toast.makeText(context, "Login Gagal pastikan Email dan Password benar", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                        userPreferences.setUser(response!!.data)
                        viewModel.loginViewModel.cleanLogin()
                        navHostController.popBackStack()
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
            onClick = {
                      navHostController.navigate(Screen.Register.route)
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(text = "Register here")
        }

    }
}
