package com.kalorize.kalorizeappmobile.ui.screen.feature

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.data.remote.response.LoginData
import com.kalorize.kalorizeappmobile.data.remote.response.LoginResponse
import com.kalorize.kalorizeappmobile.data.remote.response.LoginUser
import com.kalorize.kalorizeappmobile.ui.navigation.Screen

@Composable
fun HomeScreen(
    navController: NavHostController
){
    val context = LocalContext.current
    val userPreferences = UserPreference(context)
    Button(onClick = {
        userPreferences.setUser(LoginData(
            token = "",
            user = LoginUser(
                id = -1,
                email = "",
                password = ""
            )
        ))
        navController.popBackStack()
        navController.navigate(Screen.Login.route)
    }) {
        Text(text = "Log Out")
    }
}