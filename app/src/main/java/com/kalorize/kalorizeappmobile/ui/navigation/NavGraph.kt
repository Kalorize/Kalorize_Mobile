package com.kalorize.kalorizeappmobile.ui.navigation

import RegisterScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kalorize.kalorizeappmobile.ui.screen.AnimatedSplashScreen
import com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.changepassword.ChangePasswordScreen
import com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.changepassword.GetStartedChangePasswordScreen
import com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.otp.OtpScreen
import com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.reinputemail.ReInputEmailScreen
import com.kalorize.kalorizeappmobile.ui.screen.onboarding.OnBoardingScreen
import com.kalorize.kalorizeappmobile.ui.screen.auth.login.LoginScreen
import com.kalorize.kalorizeappmobile.ui.screen.feature.HomeScreen
import com.kalorize.kalorizeappmobile.ui.screen.feature.camera.PermissionCameraScreen
import com.kalorize.kalorizeappmobile.ui.screen.questionnaire.Questionnaire1
import com.kalorize.kalorizeappmobile.vm.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            AnimatedSplashScreen(navHostController = navController)
        }
        composable(route = Screen.OnBoarding.route) {
            OnBoardingScreen(navHostController = navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(viewModel = viewModel, navHostController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController , viewModel)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navHostController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.ReInputEmail.route
        ) {
            ReInputEmailScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.Otp.route
        ) {
            OtpScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.ChangePassword.route
        ) {
            ChangePasswordScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.GetStartedChangePassword.route
        ) {
            GetStartedChangePasswordScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.Questionnare1.route
        ) {
            Questionnaire1(navController)
        }
        composable(
            route = Screen.Questionnare2.route
        ) {
            Questionnaire1(navController)
        }
        composable(
            route = Screen.Questionnare3.route
        ) {
            Questionnaire1(navController)
        }
        composable(
            route = Screen.QuestionnareSuccess.route
        ) {
            Questionnaire1(navController)
        }
        composable(
            route = Screen.Camera.route
        ) {
            PermissionCameraScreen(navController, viewModel)
        }
    }
}