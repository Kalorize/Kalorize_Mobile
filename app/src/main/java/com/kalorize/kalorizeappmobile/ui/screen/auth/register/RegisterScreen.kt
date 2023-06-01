import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kalorize_project.R

@Composable
fun RegisterScreen() {
    val fullNameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val confirmPasswordState = remember { mutableStateOf("") }
    val passwordVisibilityState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.markorange),
            contentDescription = "Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(top = 70.dp, bottom = 60.dp)
                .size(70.dp)
        )
        Text(
            text = "Hi Friend",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = "Register here",
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(bottom = 20.dp)
        )
        TextField(
            value = fullNameState.value,
            onValueChange = { fullNameState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.large),
            label = { Text("Full Name") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
        )
        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.large),
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )
        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.large),
            label = { Text("Password") },
            visualTransformation = if (passwordVisibilityState.value) {
                PasswordVisualTransformation()
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconToggleButton(
                    checked = passwordVisibilityState.value,
                    onCheckedChange = { passwordVisibilityState.value = it }
                ) {
                    val icon = if (passwordVisibilityState.value) {
                        painterResource(id = R.drawable.hidepassword)
                    } else {
                        painterResource(id = R.drawable.showpassword)
                    }
                    Icon(
                        painter = icon,
                        contentDescription = "Toggle password visibility",
                        tint = LocalContentColor.current
                    )
                }
            }
        )
        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White, shape = MaterialTheme.shapes.large),
            label = { Text("Confirm Password") },
            visualTransformation = if (passwordVisibilityState.value) {
                PasswordVisualTransformation()
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconToggleButton(
                    checked = passwordVisibilityState.value,
                    onCheckedChange = { passwordVisibilityState.value = it }
                ) {
                    val icon = if (passwordVisibilityState.value) {
                        painterResource(id = R.drawable.hidepassword)
                    } else {
                        painterResource(id = R.drawable.showpassword)
                    }
                    Icon(
                        painter = icon,
                        contentDescription = "Toggle password visibility",
                        tint = LocalContentColor.current
                    )
                }
            }
        )
        Button(
            onClick = { /* Perform login action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFF94917), // Ubah warna latar belakang button di sini
                contentColor = Color.White, // Ubah warna teks di sini
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
                onClick = { /* Handle register here click */ },
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
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    Surface(color = MaterialTheme.colors.background) {
        RegisterScreen()
    }
}
