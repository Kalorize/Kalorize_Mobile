package com.kalorize.kalorizeappmobile.ui.screen

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navHostController: NavHostController) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    var alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )
    val context = LocalContext.current
    val userPreferences = UserPreference(context)
    val user = userPreferences.getUser()
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navHostController.popBackStack()
        if (user.token.isEmpty()) {
            navHostController.navigate(Screen.OnBoarding.route)
        } else if (user.user.activity == "") {
            navHostController.navigate(Screen.Questionnare1.route)
        } else {
            navHostController.navigate(Screen.Home.route)
        }


    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .background(Color.White) // Mengubah warna latar belakang menjadi putih
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha = alpha),
            painter = painterResource(com.kalorize.kalorizeappmobile.R.drawable.marktypeorange),
            contentDescription = "Logo Icon",
        )
    }
}
