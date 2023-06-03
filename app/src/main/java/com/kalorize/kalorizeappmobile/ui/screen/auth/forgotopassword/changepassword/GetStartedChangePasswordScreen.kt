package com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.changepassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.ui.navigation.Screen

@Composable
fun GetStartedChangePasswordScreen(
    navController: NavController
) {
    Surface(
        modifier = Modifier
            .padding(top = 40.dp, start = 20.dp, end = 20.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                text = "Password has been changed"
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Image(
                painter = painterResource(id = R.drawable.passwordchange),
                contentDescription = "Otp",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(height = 320.dp)
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Button(
                onClick = {
                    navController.navigate(route = Screen.Login.route) {
                        // Pop up to login screen
                        popUpTo(route = Screen.Login.route) {
                            inclusive = true
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
                    text = "Get Started",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    ),
                )
            }
        }
    }
}