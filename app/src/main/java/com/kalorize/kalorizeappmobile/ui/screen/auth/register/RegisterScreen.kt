import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.remote.body.RegisterBody
import com.kalorize.kalorizeappmobile.data.remote.response.RegisterResponse
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import com.kalorize.kalorizeappmobile.vm.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    val fullNameState = remember {
        mutableStateOf("")
    }
    val emailState = remember {
        mutableStateOf("")
    }
    val passwordState = remember {
        mutableStateOf("")
    }
    val passwordVisibility = remember {
        mutableStateOf(false)
    }
    val confirmPasswordState = remember {
        mutableStateOf("")
    }
    val confirmedPasswordVisibility = remember {
        mutableStateOf(false)
    }
    val isSamePassword = remember {
        mutableStateOf(false)
    }
    var response: RegisterResponse?

    val loading = remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current
    Box(){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.markorange),
                contentDescription = "Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier.padding(bottom = 30.dp, top = 30.dp)
            )
            Text(
                text = "Hi Friend👋",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .align(Alignment.Start)
            )
            Text(
                text = "Register here",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.Start)
            )
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
                text = "Email",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .align(Alignment.Start)
            )

            TextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
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
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Text(
                text = "Password",
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

            Text(
                text = "Confirm Password",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.Start)
            )

            TextField(
                value = confirmPasswordState.value,
                onValueChange = { input ->
                    confirmPasswordState.value = input
                    isSamePassword.value = passwordState.value == confirmPasswordState.value
                },
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                visualTransformation = if (confirmedPasswordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                trailingIcon = {
                    val image = if (confirmedPasswordVisibility.value) {
                        painterResource(id = R.drawable.showpassword)
                    } else {
                        painterResource(id = R.drawable.hidepassword)
                    }
                    IconButton(onClick = {
                        confirmedPasswordVisibility.value = !confirmedPasswordVisibility.value
                    }) {
                        Icon(painter = image, contentDescription = "password Toggle")
                    }
                }
            )
            Button(
                enabled = isSamePassword.value,
                onClick = {
                    loading.value = true
                    viewModel.registerViewModel.doRegister(
                        RegisterBody(
                            emailState.value,
                            fullNameState.value,
                            passwordState.value,
                            confirmPasswordState.value
                        )
                    )
                    viewModel.registerViewModel.register.observe(lifecycle) {
                        response = it
                        if (response != null) {
                            if (response!!.registerData.registerUser.id == -1) {
                                Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                                navHostController.navigate(Screen.Login.route)
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFF94917)
                ),
                content = {
                    Text(
                        text = "Register",
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            )
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Have an account?",
                    style = TextStyle(fontSize = 16.sp)
                )
                TextButton(
                    onClick = {
                        navHostController.popBackStack()
                    },
                    content = {
                        Text(
                            text = "Login",
                            color = Color(0xFFF94917),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    }
                )
            }
        }

        if (loading.value){
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

